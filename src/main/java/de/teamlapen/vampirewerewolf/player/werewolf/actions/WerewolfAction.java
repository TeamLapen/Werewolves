package de.teamlapen.vampirewerewolf.player.werewolf.actions;

import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.actions.DefaultWerewolfAction;
import de.teamlapen.vampirewerewolf.config.Balance;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public static final UUID SPEED = UUID.fromString("57ac98ff-35a1-4115-96ee-2479dc7e1460");

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
        float oldMax = player.getMaxHealth();
        float oldHealth = player.getHealth();
        ((WerewolfPlayer) werewolf).getSpecialAttributes().werewolf = true;
        applyModifier(werewolf);
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
        if (!player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(new AttributeModifier(SPEED, "werewolf_speed", 2, 2))) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SPEED, "werewolf_speed", 2, 2));
        }
    }

    private void removeModifier(IWerewolfPlayer werewolf) {
        EntityPlayer player = werewolf.getRepresentingPlayer();

        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED);
    }
}
