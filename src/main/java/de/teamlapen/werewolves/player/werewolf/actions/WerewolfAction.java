package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    private static final UUID SPEED = UUID.fromString("57ac98ff-35a1-4115-96ee-2479dc7e1460");
    private static final UUID ARMOR = UUID.fromString("a16dfca7-98b1-44a1-8057-a9cb38fbfb19");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("c70fbf55-9f19-4679-8daa-919b29ed7104");

    public WerewolfAction() {
        super(null);
    }

    @Override
    public int getCooldown() {
        return Balance.wpa.WEREWOLF_COOLDOWN;
    }

    @Override
    public int getMinU() {
        return 0;
    }

    @Override
    public int getMinV() {
        return 0;
    }

    @Override
    public String getUnlocalizedName() {
        return "werewolf";
    }

    @Override
    public boolean isEnabled() {
        return Balance.wpa.WEREWOLF_ENABLED;
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();
        for (ItemStack e : player.getArmorInventoryList()) {
            if (!e.isEmpty()) player.dropItem(e, false);
        }
        ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf = true;
        applyModifier(werewolf);
        player.refreshDisplayName();
        return true;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer werewolf) {
        return true;
    }

    @Override
    public int getDuration(int level) {
        return Balance.wpa.WEREWOLF_DURATION;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolf) {
        ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf = true;
        applyModifier(werewolf);
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();
        ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf = false;
        removeModifier(werewolf);
        player.refreshDisplayName();

    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf = true;
        applyModifier(werewolf);
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolf) {
        return false;
    }

    private void applyModifier(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();
        //TODO modify
        if (player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(SPEED) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SPEED, "werewolf_speed", (float) Balance.wpa.WEREWOLF_SPEED_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 2));
        }

        if (player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).getModifier(ARMOR) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(ARMOR, "werewolf_armor", (float) Balance.wpa.WEREWOLF_ARMOR_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 0));
        }
        if (player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).getModifier(ARMOR_TOUGHNESS) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(ARMOR_TOUGHNESS, "werewolf_armor_toughness", (float) Balance.wpa.WEREWOLF_ARMOR_THOUGNESS_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 0));
        }
    }

    private void removeModifier(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();

        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS);
    }

    public void onLevelChanged(int newLevel, int oldLevel, IWerewolfPlayer werewolf) {
        removeModifier(werewolf);
        applyModifier(werewolf);
    }
}
