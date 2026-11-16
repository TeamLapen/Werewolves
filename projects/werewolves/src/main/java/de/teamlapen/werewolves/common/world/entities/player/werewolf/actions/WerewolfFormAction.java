package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;

import de.teamlapen.faction.api.factions.actions.IActionHandler;
import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.faction.api.factions.skills.ISkill;
import de.teamlapen.faction.api.registries.actions.DeferredAction;
import de.teamlapen.werewolves.common.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.Permissions;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.PermissionAPI;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class WerewolfFormAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public static boolean isWerewolfFormActionActive(IActionHandler<IWerewolfPlayer> handler) {
        return getAllAction().stream().anyMatch(handler::isActionActive);
    }

    public static List<DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, WerewolfFormAction>> getAllAction() {
        return List.of(ModActions.HUMAN_FORM, ModActions.BEAST_FORM, ModActions.SURVIVAL_FORM);
    }

    protected static class Modifier {

        public final Holder<Attribute> attribute;
        public final Identifier dayUuid;
        public final Identifier nightUuid;
        public final Function<IWerewolfPlayer, Double> value;
        public final AttributeModifier.Operation operation;
        public final double dayModifier;

        public Modifier(Holder<Attribute> attribute, Identifier dayUuid, Identifier nightUuid, double dayModifier, Function<IWerewolfPlayer, Double> valueFunction, AttributeModifier.Operation operation) {
            this.attribute = attribute;
            this.dayUuid = dayUuid;
            this.nightUuid = nightUuid;
            this.value = valueFunction;
            this.operation = operation;
            this.dayModifier = dayModifier;
        }

        public AttributeModifier create(IWerewolfPlayer player, boolean night) {
            return new AttributeModifier(night ? nightUuid : dayUuid, night ? value.apply(player) : value.apply(player) * dayModifier, operation);
        }

        protected static class Builder {

            private final Holder<Attribute> attribute;
            private final Identifier name;
            private Function<IWerewolfPlayer, Double> valueSupplier;
            private AttributeModifier.Operation operation;
            private boolean hasDayModifier;
            private double dayModifier;

            public Builder(Holder<Attribute> attribute, Identifier name) {
                this.attribute = attribute;
                this.name = name;
            }

            public Builder withValues(Supplier<Double> valueSupplier, AttributeModifier.Operation operation) {
                this.valueSupplier = werewolfPlayer -> valueSupplier.get();
                this.operation = operation;
                return this;
            }

            public Builder withDayModifier(double dayModifier) {
                this.dayModifier = dayModifier;
                this.hasDayModifier = true;
                return this;
            }

            public Builder withSkillModifier(Holder<? extends ISkill<?>> skill, Supplier<Double> valueSupplier) {
                Objects.requireNonNull(this.valueSupplier);
                var old = this.valueSupplier;
                this.valueSupplier = werewolf -> werewolf.getSkillHandler().isSkillEnabled(skill) ? valueSupplier.get() : old.apply(werewolf);
                return this;
            }

            public Modifier build() {
                if (hasDayModifier) {
                    return new Modifier(attribute, name.withSuffix("_day"), name.withSuffix("_night"), dayModifier, valueSupplier, operation);
                }
                return new Modifier(attribute, name, name, 1, valueSupplier, operation);
            }
        }
    }

    protected final List<Modifier> attributes = new ArrayList<>();
    private final WerewolfForm form;

    public WerewolfFormAction(WerewolfForm form) {
        this.form = form;
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer werewolf, ActivationContext context) {
        Player player = werewolf.asEntity();
        float healthPerc = player.getHealth() / player.getMaxHealth();
        if (isWerewolfFormActionActive(werewolf.getActionHandler())) {
            FormHelper.deactivateWerewolfActions(werewolf);
        }
        ((WerewolfPlayer) werewolf).setForm(this.<WerewolfFormAction>holder(), this.form);
        this.checkDayNightModifier(werewolf);
        player.setHealth(player.getMaxHealth() * healthPerc);
        player.refreshDisplayName();

        if (player instanceof ServerPlayer serverPlayer && getForm() != WerewolfForm.NONE && getForm() != WerewolfForm.HUMAN && Helper.isFullMoon(serverPlayer.level())) {
            ModAdvancements.TRIGGER_VAMPIRE_ACTION.get().trigger(serverPlayer, WerewolfActionCriterionTrigger.Action.TRANSFORM_FULL_MOON);
        }
        return IActionResult.SUCCESS;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).setForm(this.<WerewolfFormAction>holder(), this.form);
        werewolfPlayer.asEntity().refreshDisplayName();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        float healthPerc = player.getHealth() / player.getMaxHealth();
        ((WerewolfPlayer) werewolf).setForm(this.<WerewolfFormAction>holder(), WerewolfForm.NONE);
        this.removeModifier(werewolf);
        player.setHealth(player.getMaxHealth() * healthPerc);
        player.refreshDisplayName();
        if (werewolf.getActionHandler().isActionActive(ModActions.RAGE)) {
            werewolf.getActionHandler().deactivateAction(ModActions.RAGE);
        }
    }

    @Override
    public void onReActivatedServer(IWerewolfPlayer werewolf) {
        ((WerewolfPlayer) werewolf).setForm(this.<WerewolfFormAction>holder(), this.form);
        werewolf.asEntity().refreshDisplayName();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolfPlayer) {
        if (werewolfPlayer.asEntity().level().getGameTime() % 20 == 0) {
            checkDayNightModifier(werewolfPlayer);
        }

        if (!usesTransformationTime(werewolfPlayer)) {
            return false;
        }
        return increaseWerewolfTime(werewolfPlayer) || (werewolfPlayer.asEntity() instanceof ServerPlayer && !PermissionAPI.getPermission((ServerPlayer) werewolfPlayer.asEntity(), Permissions.FORM));
    }

    public boolean usesTransformationTime(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        return !Helper.isNight(player.level()) && !FormHelper.isInWerewolfBiome(player.level(), player.blockPosition());
    }

    protected boolean increaseWerewolfTime(IWerewolfPlayer werewolfPlayer) {
        if (!consumesWerewolfTime(werewolfPlayer)) return false;
        return (((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().transformationTime = Mth.clamp(((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().transformationTime + ((double) 1 / (double) getTimeModifier(werewolfPlayer)), 0, 1)) == 1;
    }

    public void checkDayNightModifier(IWerewolfPlayer werewolfPlayer) {
        boolean night = Helper.isNight(werewolfPlayer.asEntity().level());
        checkDayNightModifier(werewolfPlayer, night);
    }

    protected void checkDayNightModifier(IWerewolfPlayer werewolfPlayer, boolean night) {
        Player player = werewolfPlayer.asEntity();
        float healthPerc = player.getHealth() / player.getMaxHealth();
        removeModifier(werewolfPlayer);
        applyModifier(werewolfPlayer, night);
        player.setHealth(player.getMaxHealth() * healthPerc);
    }

    public void applyModifier(IWerewolfPlayer werewolf, boolean night) {
        Player player = werewolf.asEntity();
        for (Modifier attribute : this.attributes) {
            AttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null && ins.getModifier(attribute.dayUuid) == null) {
                ins.addPermanentModifier(attribute.create(werewolf, night));
            }
        }
    }

    public void removeModifier(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        for (Modifier attribute : this.attributes) {
            AttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null) {
                ins.removeModifier(attribute.dayUuid);
                ins.removeModifier(attribute.nightUuid);
            }
        }
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return Integer.MAX_VALUE;
    }

    @Override
    public IActionResult canBeUsedBy(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        if (player instanceof ServerPlayer && (!PermissionAPI.getPermission((ServerPlayer) player, Permissions.TRANSFORMATION) || !PermissionAPI.getPermission((ServerPlayer) player, Permissions.FORM))) {
            return IActionResult.DISALLOWED_PERMISSION;
        }
        if (player.isPassenger() && !this.form.isHumanLike()) return IActionResult.RESTRICTED;
        boolean active = werewolf.getActionHandler().isActionActive(this.<ILastingAction<IWerewolfPlayer>>holder());
        if (active) {
            if (Helper.isFullMoon(player.level())) {
                return (werewolf.getSkillHandler().isSkillEnabled(ModSkills.FREE_WILL)) ? IActionResult.SUCCESS : IActionResult.RESTRICTED;
            } else {
                return IActionResult.SUCCESS;
            }
        } else {
            if (this.form.getSize(werewolf.asEntity().getPose()).map(dimensions -> dimensions.makeBoundingBox(werewolf.asEntity().position())).filter(s -> werewolf.asEntity().level().collidesWithSuffocatingBlock(werewolf.asEntity(), s)).isPresent()) {
                return IActionResult.RESTRICTED;
            }
            if (werewolf.getForm().isTransformed()) {
                return IActionResult.SUCCESS;
            } else {
                if (player.level().getBiome(player.blockPosition()).is(ModBiomes.WEREWOLF_FOREST)) {
                    return IActionResult.SUCCESS;
                } else {
                    return (((WerewolfPlayer) werewolf).getSpecialAttributes().transformationTime < 0.7) ? IActionResult.SUCCESS : IActionResult.RESTRICTED;
                }
            }
        }
    }

    public boolean consumesWerewolfTime(IWerewolfPlayer werewolf) {
        return true;
    }

    /**
     * ticks this action can be used
     */
    public int getTimeModifier(IWerewolfPlayer werewolf) {
        int limit = ModConfig.balance().SKILLS.werewolf_form_time_limit.get() * 20;
        boolean duration1 = werewolf.getRefinementHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1);
        boolean duration2 = werewolf.getRefinementHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2);
        if (duration1 || duration2) {
            if (duration2) {
                limit += ModConfig.balance().REFINEMENTS.werewolf_form_duration_general_2.get() * 20;
            } else {
                limit += ModConfig.balance().REFINEMENTS.werewolf_form_duration_general_1.get() * 20;
            }
        }
        return limit;
    }

    public WerewolfForm getForm() {
        return form;
    }

}
