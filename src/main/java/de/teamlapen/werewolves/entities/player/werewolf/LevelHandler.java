package de.teamlapen.werewolves.entities.player.werewolf;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

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
        return Mth.clamp((float) this.levelProgress / getNeededProgress(), 0, 1);
    }

    public void saveToNbt(@NotNull CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("progress", levelProgress);
        compound.put("level", nbt);
    }

    public void loadFromNbt(@NotNull CompoundTag compound) {
        if (compound.contains("level")) {
            this.levelProgress = compound.getCompound("level").getInt("progress");
        }
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
