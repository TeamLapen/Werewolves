package de.teamlapen.werewolves.entities.player.werewolf;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class WerewolfLevelConf {

    @Nonnull
    private static final WerewolfLevelConf INSTANCE = new WerewolfLevelConf();
    @Nonnull
    private final Map<Integer, LevelRequirement> levelRequirementMap = new HashMap<>();

    public WerewolfLevelConf() {
        levelRequirementMap.put(2, new StoneAltarRequirement(50, 3, 2));
        levelRequirementMap.put(3, new StoneAltarRequirement(75, 4, 3));
        levelRequirementMap.put(4, new StoneAltarRequirement(100, 5, 4));
        levelRequirementMap.put(5, new StoneAltarRequirement(125, 6, 5));
        levelRequirementMap.put(6, new StoneAltarRequirement(150, 7, 6));
        levelRequirementMap.put(7, new StoneAltarRequirement(175, 8, 7));
        levelRequirementMap.put(8, new StoneAltarRequirement(200, 9, 8));
        levelRequirementMap.put(9, new StoneAltarRequirement(225, 10, 9));
        levelRequirementMap.put(10, new StoneAltarRequirement(250, 11, 10));
        levelRequirementMap.put(11, new StoneAltarRequirement(275, 12, 11));
        levelRequirementMap.put(12, new StoneAltarRequirement(300, 13, 12));
        levelRequirementMap.put(13, new StoneAltarRequirement(325, 14, 13));
        levelRequirementMap.put(14, new StoneAltarRequirement(350, 15, 14));
    }

    public static WerewolfLevelConf getInstance() {
        return INSTANCE;
    }

    @Nullable
    public LevelRequirement getRequirement(int level) {
        return levelRequirementMap.get(level);
    }

    @Nullable
    public StoneAltarRequirement getStoneRequirement(int level){
        LevelRequirement req = levelRequirementMap.get(level);
        return req instanceof StoneAltarRequirement? (StoneAltarRequirement) req :null;
    }

    public static class LevelRequirement {

        public final int xpAmount;

        public LevelRequirement(int xpAmount) {
            this.xpAmount = xpAmount;
        }
    }

    public static class StoneAltarRequirement extends LevelRequirement {

        public static final int START_LVL = 2;
        public static final int LAST_LVL = 14;
        public final int liverAmount, bonesAmount;

        public StoneAltarRequirement(int xpAmount, int liverAmount, int bonesAmount) {
            super(xpAmount);
            this.liverAmount = liverAmount;
            this.bonesAmount = bonesAmount;
        }
    }
}
