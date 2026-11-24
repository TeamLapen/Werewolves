package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;

import net.minecraft.world.entity.EquipmentSlot;

import com.mojang.serialization.Codec;
import de.teamlapen.sync.PropertyParentSync;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class WerewolfInventory extends PropertyParentSync {

    private final WerewolfPlayer werewolf;
    private final Map<WerewolfForm, List<ItemStack>> inventories;

    private static final Codec<Map<WerewolfForm, List<ItemStack>>> INVENTORY_CODEC = Codec.simpleMap(WerewolfForm.CODEC, Codec.list(ItemStack.OPTIONAL_CODEC), WerewolfForm.FORM_KEYS).codec();

    public WerewolfInventory(WerewolfPlayer werewolf) {
        super(werewolf);
        this.werewolf = werewolf;
        Map<WerewolfForm, List<ItemStack>> inventories = new HashMap<>();
        WerewolfForm.getAllForms().forEach(form -> inventories.put(form, NonNullList.withSize(4, ItemStack.EMPTY)));
        this.inventories = inventories;
    }

    /**
     * Stores the currently worn armor for {@code from} and equips the stored armor of {@code to}.
     * <p>
     * With the {@link ModSkills#WEAR_ARMOR} skill, armor is carried between {@link WerewolfForm#NONE} and the human-like
     * transformed forms if the target form has no armor of its own:
     * <ul>
     *     <li>switching to a human-like transformed form takes the armor stored for {@link WerewolfForm#NONE}</li>
     *     <li>switching from a human-like transformed form to {@link WerewolfForm#NONE} keeps the worn armor</li>
     * </ul>
     * The carried armor is moved, so it is never stored for two forms at once.
     */
    public void swapArmorItems(WerewolfForm from, WerewolfForm to) {
        List<ItemStack> armor = new ArmorView(werewolf.asEntity());
        this.inventories.put(from, new ArrayList<>(armor));
        armor.clear();
        List<ItemStack> itemStacks = this.inventories.get(to);
        if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR) && itemStacks.stream().allMatch(ItemStack::isEmpty)) {
            WerewolfForm source = null;
            if (to != WerewolfForm.NONE && to.isHumanLike()) {
                source = WerewolfForm.NONE;
            } else if (to == WerewolfForm.NONE && from.isHumanLike()) {
                source = from;
            }
            if (source != null && werewolf.canWearArmor(to, this.inventories.get(source))) {
                itemStacks = this.inventories.get(source);
                this.inventories.put(source, NonNullList.withSize(4, ItemStack.EMPTY));
            }
        }
        for (int i = 0; i < 4; i++) {
            armor.set(i, itemStacks.get(i));
        }
        this.inventories.put(to, armor);
    }

    public void dropEquipment() {
        Player player = this.werewolf.asEntity();
        for (List<ItemStack> list : this.inventories.values()) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = list.get(i);
                if (!stack.isEmpty()) {
                    player.drop(stack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public void dropFormEquipment(WerewolfForm form) {
        Player player = this.werewolf.asEntity();
        List<ItemStack> list = this.inventories.get(form);
        for (int i = 0; i < list.size(); i++) {
            ItemStack stack = list.get(i);
            if (!stack.isEmpty()) {
                if(!player.addItem(stack)) {
                    player.drop(stack,false);
                }
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    protected void registerProperties() {
        registerProperty(WIdentifier.mod("inventory")).simple(INVENTORY_CODEC).defaultValue(new HashMap<>()).provider(() -> inventories).commonLoader(this::updateInventories).register();
    }

    /**
     * Updates {@link #inventories} from {@code newInventories}, comparing the actual stacks in each list
     * (via {@link ItemStack#matches}) instead of only the map keys.
     * <p>
     * Existing lists are updated in place where possible, because the list of the current form may be the
     * player's armor list itself (see {@link #swapArmorItems}). Incoming lists are never stored directly,
     * as codec-decoded lists are immutable.
     *
     * @return whether anything changed
     */
    private boolean updateInventories(Map<WerewolfForm, List<ItemStack>> newInventories) {
        boolean changed = this.inventories.keySet().retainAll(newInventories.keySet());
        for (Map.Entry<WerewolfForm, List<ItemStack>> entry : newInventories.entrySet()) {
            List<ItemStack> newList = entry.getValue();
            List<ItemStack> oldList = this.inventories.get(entry.getKey());
            if (oldList == null || oldList.size() != newList.size()) {
                NonNullList<ItemStack> copy = NonNullList.withSize(newList.size(), ItemStack.EMPTY);
                for (int i = 0; i < newList.size(); i++) {
                    copy.set(i, newList.get(i));
                }
                this.inventories.put(entry.getKey(), copy);
                changed = true;
                continue;
            }
            for (int i = 0; i < newList.size(); i++) {
                if (!ItemStack.matches(oldList.get(i), newList.get(i))) {
                    oldList.set(i, newList.get(i));
                    changed = true;
                }
            }
        }
        return changed;
    }


    /**
     * Live view on the armor equipment slots of the player (feet, legs, chest, head), replacing the removed {@code Inventory#armor} list
     */
    private static class ArmorView extends AbstractList<ItemStack> {
        private static final EquipmentSlot[] SLOTS = {EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
        private final Player player;

        private ArmorView(Player player) {
            this.player = player;
        }

        @Override
        public ItemStack get(int index) {
            return this.player.getItemBySlot(SLOTS[index]);
        }

        @Override
        public ItemStack set(int index, ItemStack element) {
            ItemStack old = get(index);
            this.player.setItemSlot(SLOTS[index], element);
            return old;
        }

        @Override
        public void clear() {
            for (int i = 0; i < SLOTS.length; i++) {
                set(i, ItemStack.EMPTY);
            }
        }

        @Override
        public int size() {
            return SLOTS.length;
        }
    }
}
