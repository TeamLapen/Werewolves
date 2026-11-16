package de.teamlapen.werewolves.common.world.entities.player.werewolf.properties;

import de.teamlapen.sync.PropertyParentSync;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.util.Mth;

public class LevelHandler extends PropertyParentSync {
    private final WerewolfPlayer player;
    private int levelProgress;

    public LevelHandler(WerewolfPlayer player) {
        super(player);
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
    protected void registerProperties() {
        this.registerProperty(WIdentifier.mod("progress")).simple(0, () -> levelProgress, x -> this.levelProgress = x);
    }

}
