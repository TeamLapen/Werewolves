package de.teamlapen.werewolves.entities.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.api.entity.player.task.ITaskManager;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings("ConstantConditions")
@Deprecated
class WerewolfPlayerDefaultImpl implements IWerewolfPlayer {
    private static final Logger LOGGER = LogManager.getLogger();

    public WerewolfPlayerDefaultImpl() {
        LOGGER.error("Created Default Implementation. THIS SHOULD NOT BE DONE. The default impl does absolutely nothing");
    }

    @Override
    public boolean canLeaveFaction() {
        return false;
    }

    @Nullable
    @Override
    public IFaction<?> getDisguisedAs() {
        return null;
    }

    @NotNull
    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public Predicate<LivingEntity> getNonFriendlySelector(boolean b, boolean b1) {
        return null;
    }

    @Override
    public Player getRepresentingPlayer() {
        return null;
    }

    @Override
    public boolean isDisguised() {
        return false;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public void onLevelChanged(int i, int i1) {
    }

    @NotNull
    @Override
    public ITaskManager getTaskManager() {
        return null;
    }

    @Override
    public LivingEntity getRepresentingEntity() {
        return null;
    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return null;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return null;
    }

    @NotNull
    @Override
    public WerewolfForm getForm() {
        return null;
    }

    @Override
    public int getSkinType(WerewolfForm form) {
        return 0;
    }

    @Override
    public int getEyeType(WerewolfForm form) {
        return 0;
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return false;
    }
}
