package de.teamlapen.werewolves.entities.player.werewolf;

import de.teamlapen.lib.lib.storage.ISyncableSaveData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class LevelHandler implements ISyncableSaveData {
    private static final String KEY_LEVEL = "level";
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

    @Override
    public @NotNull CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("progress", levelProgress);
        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag compoundTag) {
        if (compoundTag.contains("progress")) {
            levelProgress = compoundTag.getInt("progress");
        }
    }

    @Override
    public void deserializeUpdateNBT(@NotNull CompoundTag compoundTag) {
        deserializeNBT(compoundTag);
    }

    @Override
    public @NotNull CompoundTag serializeUpdateNBT() {
        return serializeNBT();
    }

    @Override
    public String nbtKey() {
        return KEY_LEVEL;
    }
}
