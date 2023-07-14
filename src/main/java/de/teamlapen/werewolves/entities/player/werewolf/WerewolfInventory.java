package de.teamlapen.werewolves.entities.player.werewolf;

import com.google.common.collect.ImmutableMap;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WerewolfInventory {

    private final WerewolfPlayer werewolf;
    private final Map<WerewolfForm, NonNullList<ItemStack>> inventories;

    public WerewolfInventory(WerewolfPlayer werewolf) {
        this.werewolf = werewolf;
        Map<WerewolfForm, NonNullList<ItemStack>> inventories = new HashMap<>();
        WerewolfForm.getAllForms().forEach(form -> inventories.put(form, NonNullList.withSize(4, ItemStack.EMPTY)));
        this.inventories = Collections.unmodifiableMap(inventories);
    }

    public NonNullList<ItemStack> getArmor(WerewolfForm form) {
        return inventories.get(form);
    }

    public Map<WerewolfForm, NonNullList<ItemStack>> getInventories() {
        return inventories;
    }

    public void dropEquipment() {
        for (NonNullList<ItemStack> list : this.inventories.values()) {
            for (int i = 0; i < list.size(); i++) {
                ItemStack stack = list.get(i);
                if (!stack.isEmpty()) {
                    this.werewolf.getRepresentingPlayer().drop(stack, true, false);
                    list.set(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
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
        return tag;
    }

    public void load(CompoundTag tag) {
        tag.getAllKeys().stream().map(WerewolfForm::getForm).forEach(form -> {
            ListTag list = tag.getList(form.getName(), 10);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag itemTag = list.getCompound(i);
                int slot = itemTag.getByte("Slot") & 255;
                ItemStack stack = ItemStack.of(itemTag);
                this.inventories.get(form).set(slot, stack);
            }
        });
    }
}
