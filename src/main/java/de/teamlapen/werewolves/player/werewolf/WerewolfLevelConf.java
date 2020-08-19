package de.teamlapen.werewolves.player.werewolf;

import java.util.HashMap;
import java.util.Map;

public class WerewolfLevelConf {

    private static final WerewolfLevelConf INSTANCE = new WerewolfLevelConf();
    private final Map<Integer, LevelRequirement> levelRequirementMap = new HashMap<>();

    public WerewolfLevelConf() {
        levelRequirementMap.put(1, new LevelRequirement(10));
        levelRequirementMap.put(2, new LevelRequirement(10));
        levelRequirementMap.put(3, new LevelRequirement(10));
        levelRequirementMap.put(4, new LevelRequirement(10));
        levelRequirementMap.put(5, new LevelRequirement(10));
        levelRequirementMap.put(6, new LevelRequirement(10));
        levelRequirementMap.put(7, new LevelRequirement(10));
        levelRequirementMap.put(8, new LevelRequirement(10));
        levelRequirementMap.put(9, new LevelRequirement(10));
        levelRequirementMap.put(10, new LevelRequirement(10));
        levelRequirementMap.put(11, new LevelRequirement(10));
        levelRequirementMap.put(12, new LevelRequirement(10));
        levelRequirementMap.put(13, new LevelRequirement(10));
        levelRequirementMap.put(14, new LevelRequirement(10));
    }

    public static WerewolfLevelConf getInstance() {
        return INSTANCE;
    }

    public LevelRequirement getRequirement(int level) {
        return levelRequirementMap.get(level);
    }

    public static class LevelRequirement {

        public final int xpAmount;

        public LevelRequirement(int xpAmount) {
            this.xpAmount = xpAmount;
        }
    }
}
