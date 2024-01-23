package de.teamlapen.werewolves.entities.player.werewolf;

import com.google.common.collect.ImmutableMap;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class WerewolfInventory {

    private final WerewolfPlayer werewolf;
    private final Map<WerewolfForm, List<ItemStack>> inventories;
    private WerewolfForm form = WerewolfForm.NONE;

    public WerewolfInventory(WerewolfPlayer werewolf) {
        this.werewolf = werewolf;
        Map<WerewolfForm, List<ItemStack>> inventories = new HashMap<>();
        WerewolfForm.getAllForms().forEach(form -> inventories.put(form, NonNullList.withSize(4, ItemStack.EMPTY)));
        this.inventories = inventories;
    }

    public void swapArmorItems(WerewolfForm to) {
        var armor = werewolf.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR.get()) && to != WerewolfForm.NONE && to.isHumanLike();
        List<ItemStack> previousArmor = werewolf.getRepresentingPlayer().getInventory().armor;
        this.inventories.put(this.form, List.copyOf(previousArmor));
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
        for (List<ItemStack> list : this.inventories.values()) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = list.get(i);
                if (!stack.isEmpty()) {
                    this.werewolf.getRepresentingPlayer().drop(stack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public void dropFormEquipment(WerewolfForm form) {
        List<ItemStack> list = this.inventories.get(form);
        for (int i = 0; i < list.size(); i++) {
            ItemStack stack = list.get(i);
            if (!stack.isEmpty()) {
                this.werewolf.getRepresentingPlayer().drop(stack, true, false);
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("lastForm", this.form.getName());
        CompoundTag tag = new CompoundTag();
        nbt.put("inventory", tag);
        this.inventories.forEach((form, armor) -> {
            ListTag list = new ListTag();
            for (int i = 0; i < armor.size(); i++) {
                ItemStack stack = armor.get(i);
                if (!stack.isEmpty()) {
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putByte("Slot", (byte) i);
                    stack.save(itemTag);
                    list.add(itemTag);
                }
            }
            tag.put(form.getName(), list);
        });
        return nbt;
    }

    public void load(CompoundTag tag) {
        this.form = Objects.requireNonNullElse(WerewolfForm.getForm(tag.getString("lastForm")), WerewolfForm.NONE);
        CompoundTag inventory = tag.getCompound("inventory");
        inventory.getAllKeys().stream().map(WerewolfForm::getForm).forEach(form -> {
            ListTag list = inventory.getList(form.getName(), 10);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag itemTag = list.getCompound(i);
                int slot = itemTag.getByte("Slot") & 255;
                ItemStack stack = ItemStack.of(itemTag);
                this.inventories.get(form).set(slot, stack);
            }
        });
    }
}
