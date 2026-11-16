package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;

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
    private WerewolfForm form = WerewolfForm.NONE;

    private static final Codec<Map<WerewolfForm, List<ItemStack>>> INVENTORY_CODEC = Codec.simpleMap(WerewolfForm.CODEC, Codec.list(ItemStack.OPTIONAL_CODEC), WerewolfForm.FORM_KEYS).codec();

    public WerewolfInventory(WerewolfPlayer werewolf) {
        super(werewolf);
        this.werewolf = werewolf;
        Map<WerewolfForm, List<ItemStack>> inventories = new HashMap<>();
        WerewolfForm.getAllForms().forEach(form -> inventories.put(form, NonNullList.withSize(4, ItemStack.EMPTY)));
        this.inventories = inventories;
    }

    public void swapArmorItems(WerewolfForm to) {
        var armor = werewolf.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR) && to != WerewolfForm.NONE && to.isHumanLike();
        List<ItemStack> previousArmor = werewolf.asEntity().getInventory().armor;
        this.inventories.put(this.form, new ArrayList<>(previousArmor));
        previousArmor.clear();
        List<ItemStack> itemStacks = this.inventories.get(to);
        if (armor && itemStacks.stream().allMatch(ItemStack::isEmpty)) {
            itemStacks = this.inventories.get(to = WerewolfForm.NONE);
        }
        for (int i = 0; i < 4; i++) {
            previousArmor.set(i, itemStacks.get(i));
        }
        this.inventories.put(to, previousArmor);
        this.form = to;
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
        registerProperty(WIdentifier.mod("form")).simple(WerewolfForm.CODEC).provider(() -> form).commonLoader(x -> {
            var old = this.form;
            this.form = x;
            return Objects.equals(old, this.form);
        }).register();
        registerProperty(WIdentifier.mod("inventory")).simple(INVENTORY_CODEC).provider(() -> inventories).commonLoader(this::updateInventories).register();
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

}
