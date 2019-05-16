package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayerSpecialAttributes;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;

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
        // TODO
        return 0;
    }

    @Override
    public int getMinV() {
        // TODO
        return 0;
    }

    @Override
    public String getUnlocalizedName() {
        // TODO
        return "werewolf";
    }

    @Override
    public boolean isEnabled() {
        return Balance.wpa.WEREWOLF_ENABLED;
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        //TODO armor handling
        WerewolfPlayerSpecialAttributes specialAttributes = ((WerewolfPlayer) werewolf).getSpecialAttributes();
        specialAttributes.werewolf = specialAttributes.werewolfLevel;
        this.applyModifier(werewolf);
        werewolf.getRepresentingPlayer().refreshDisplayName();
        WerewolvesMod.log.t("num: %s", ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolfLevel);
        return true;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer werewolf) {
        WerewolvesMod.log.t("Player: %s", ((WerewolfPlayer) werewolf).getSpecialAttributes().transformable);
        if (!((WerewolfPlayer) werewolf).getSpecialAttributes().transformable)
            return false;
        return true;
    }

    @Override
    public int getDuration(int level) {
        if (level == 14)
            return Balance.wpa.WEREWOLF_DURATION_MAX;
        return Balance.wpa.WEREWOLF_DURATION;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolf) {
        WerewolfPlayerSpecialAttributes specialAttributes = ((WerewolfPlayer) werewolf).getSpecialAttributes();
        specialAttributes.werewolf = specialAttributes.werewolfLevel;
        this.applyModifier(werewolf);
        werewolf.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        WerewolfPlayerSpecialAttributes specialAttributes = ((WerewolfPlayer) werewolf).getSpecialAttributes();
        specialAttributes.werewolf = 0;
        this.removeModifier(werewolf);
        werewolf.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        WerewolfPlayerSpecialAttributes specialAttributes = ((WerewolfPlayer) werewolf).getSpecialAttributes();
        specialAttributes.werewolf = specialAttributes.werewolfLevel;
        this.applyModifier(werewolf);
        werewolf.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolf) {
        return false;
    }

    public void onLevelChanged(IWerewolfPlayer werewolf) {
        this.removeModifier(werewolf);
        this.applyModifier(werewolf);
    }

    private void applyModifier(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();
        WerewolfPlayerSpecialAttributes specialAttributes = ((WerewolfPlayer) werewolf).getSpecialAttributes();
        if (specialAttributes.werewolf > 0 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(SPEED) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SPEED, "werewolf_speed", (float) Balance.wpa.WEREWOLF_SPEED_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 2));
        }
        if (specialAttributes.werewolf > 1 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).getModifier(ARMOR) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(ARMOR, "werewolf_armor", (float) Balance.wpa.WEREWOLF_ARMOR_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 0));
        }
        if (specialAttributes.werewolf > 1 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).getModifier(ARMOR_TOUGHNESS) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(ARMOR_TOUGHNESS, "werewolf_armor_toughness", (float) Balance.wpa.WEREWOLF_ARMOR_THOUGNESS_MAX / werewolf.getMaxLevel() * werewolf.getLevel(), 0));
        }
    }

    private void removeModifier(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();

        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS);
    }
}
