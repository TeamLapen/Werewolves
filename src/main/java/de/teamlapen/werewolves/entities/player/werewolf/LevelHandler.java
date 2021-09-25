package de.teamlapen.werewolves.entities.player.werewolf;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class LevelHandler {

    private final WerewolfPlayer player;
    private int levelProgress;


    public LevelHandler(WerewolfPlayer player) {
        this.player = player;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean canLevelUp() {
        return player.getLevel() != player.getMaxLevel() && levelProgress >= WerewolfLevelConf.getInstance().getRequirement(player.getLevel() + 1).xpAmount;
    }

    @SuppressWarnings("ConstantConditions")
    public float getLevelPerc() {
        return player.getLevel() == player.getMaxLevel() ? 1f : (float) levelProgress / WerewolfLevelConf.getInstance().getRequirement(player.getLevel() + 1).xpAmount;
    }

    public void saveToNbt(@Nonnull CompoundNBT compound) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("progress", levelProgress);
        compound.put("level", nbt);
    }

    public void loadFromNbt(@Nonnull CompoundNBT compound) {
        this.levelProgress = compound.getCompound("level").getInt("progress");
    }

    @SuppressWarnings("ConstantConditions")
    public void clear() {
        if (player.getLevel() != player.getMaxLevel()) {
            return;
        }
        this.levelProgress = Math.max(0, this.levelProgress - WerewolfLevelConf.getInstance().getRequirement(player.getLevel() + 1).xpAmount);
    }
}
