package de.teamlapen.werewolves.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteriaReadOnly;
import net.minecraft.scoreboard.ScoreObjective;

public class ScoreboardUtil {
    public final static IScoreCriteria WEREWOLF_LEVEL_CRITERIA = new ScoreCriteriaReadOnly("vampirism:werewolf");

    private static boolean init = false;

    public static void init() {
        if (!init) {
            IScoreCriteria.INSTANCES.put(WEREWOLF_LEVEL_CRITERIA.getName(), WEREWOLF_LEVEL_CRITERIA);
            init = true;
        }

    }

    public static void updateScoreboard(EntityPlayer player, IScoreCriteria crit, int value) {
        if (!player.world.isRemote) {
            for (ScoreObjective scoreobjective : player.getWorldScoreboard().getObjectivesFromCriteria(crit)) {
                Score score = player.getWorldScoreboard().getOrCreateScore(player.getName(), scoreobjective);
                score.setScorePoints(value);
            }
        }
    }
}
