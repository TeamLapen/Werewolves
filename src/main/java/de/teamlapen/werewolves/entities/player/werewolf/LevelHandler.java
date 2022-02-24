package de.teamlapen.werewolves.entities.player.werewolf;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class LevelHandler {

    private final WerewolfPlayer player;
    private int levelProgress;


    public LevelHandler(WerewolfPlayer player) {
        this.player = player;
    }

    public boolean canLevelUp() {
        return this.player.getLevel() != this.player.getMaxLevel() && this.levelProgress >= getNeededProgress();
    }

    public float getLevelPerc() {
        return MathHelper.clamp((float) this.levelProgress / getNeededProgress(), 0, 1);
    }

    public void saveToNbt(@Nonnull CompoundNBT compound) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("progress", levelProgress);
        compound.put("level", nbt);
    }

    public void loadFromNbt(@Nonnull CompoundNBT compound) {
        this.levelProgress = compound.getCompound("level").getInt("progress");
    }

    public int getLevelProgress() {
        return this.levelProgress;
    }

    public int getNeededProgress() {
        return WerewolfLevelConf.getInstance().getRequirementOpt(this.player.getLevel() + 1).map(x -> x.xpAmount).orElse(Integer.MAX_VALUE);
    }

    public void increaseProgress(int amount) {
        this.levelProgress += amount;
    }

    public void reset() {
        this.levelProgress = 0;
    }
}
