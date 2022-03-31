package de.teamlapen.werewolves.entities.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.api.entity.player.task.ITaskManager;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    public PlayerEntity getRepresentingPlayer() {
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

    @Nonnull
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

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return WerewolfForm.NONE;
    }

    @Override
    public int getSkinType(@Nonnull WerewolfForm form) {
        return 0;
    }

    @Override
    public int getEyeType(@Nonnull WerewolfForm form) {
        return 0;
    }

    @Override
    public boolean hasGlowingEyes(@Nonnull WerewolfForm form) {
        return false;
    }
}
