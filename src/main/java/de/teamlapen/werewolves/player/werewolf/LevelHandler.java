package de.teamlapen.werewolves.player.werewolf;

import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class LevelHandler {

    private final WerewolfPlayer player;
    private int levelProgress;


    public LevelHandler(WerewolfPlayer player) {
        this.player = player;
    }

    public boolean canLevelUp() {
        return levelProgress >= WerewolfLevelConf.getInstance().getRequirement(player.getLevel() + 1).xpAmount;
    }

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
}
