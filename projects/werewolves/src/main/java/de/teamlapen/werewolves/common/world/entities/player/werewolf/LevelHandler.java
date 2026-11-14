package de.teamlapen.werewolves.common.world.entities.player.werewolf;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

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
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("progress", levelProgress);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        compoundTag.getInt("progress").ifPresent(x -> levelProgress = x);
    }

    @Override
    public void deserializeUpdateNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        deserializeNBT(provider, compoundTag);
    }

    @Override
    public CompoundTag serializeUpdateNBT(HolderLookup.Provider provider) {
        return serializeNBT(provider);
    }

    @Override
    public String nbtKey() {
        return KEY_LEVEL;
    }
}
