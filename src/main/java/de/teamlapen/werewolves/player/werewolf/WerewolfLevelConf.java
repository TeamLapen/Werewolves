package de.teamlapen.werewolves.player.werewolf;

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
        levelRequirementMap.put(2, new StoneAltarRequirement(10, 3, 2));
        levelRequirementMap.put(3, new StoneAltarRequirement(15, 4, 3));
        levelRequirementMap.put(4, new StoneAltarRequirement(20, 5, 4));
        levelRequirementMap.put(5, new StoneAltarRequirement(25, 6, 5));
        levelRequirementMap.put(6, new StoneAltarRequirement(30, 7, 6));
        levelRequirementMap.put(7, new StoneAltarRequirement(35, 8, 7));
        levelRequirementMap.put(8, new StoneAltarRequirement(40, 9, 8));
        levelRequirementMap.put(9, new StoneAltarRequirement(45, 10, 9));
        levelRequirementMap.put(10, new StoneAltarRequirement(50, 11, 10));
        levelRequirementMap.put(11, new StoneAltarRequirement(55, 12, 11));
        levelRequirementMap.put(12, new StoneAltarRequirement(60, 13, 12));
        levelRequirementMap.put(13, new StoneAltarRequirement(65, 14, 13));
        levelRequirementMap.put(14, new StoneAltarRequirement(70, 15, 14));
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
