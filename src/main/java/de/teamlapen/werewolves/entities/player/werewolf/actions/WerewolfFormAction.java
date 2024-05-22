package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.Permissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.PermissionAPI;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class WerewolfFormAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    private static final Set<WerewolfFormAction> ALL_ACTION = new HashSet<>();

    public static boolean isWerewolfFormActionActive(IActionHandler<IWerewolfPlayer> handler) {
        return ALL_ACTION.stream().anyMatch(handler::isActionActive);
    }

    public static Set<WerewolfFormAction> getAllAction() {
        return Collections.unmodifiableSet(ALL_ACTION);
    }

    protected static class Modifier {

        public final Attribute attribute;
        public final UUID dayUuid;
        public final UUID nightUuid;
        public final String name;
        public final Function<IWerewolfPlayer, Double> value;
        public final AttributeModifier.Operation operation;
        public final double dayModifier;

        public Modifier(Attribute attribute, UUID uuid, String name, Supplier<Double> valueFunction, AttributeModifier.Operation operation) {
            this(attribute, uuid, uuid, 1, name, player -> valueFunction.get(), operation);
        }

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, AttributeModifier.Operation operation) {
            this(attribute, dayUuid, nightUuid, dayModifier, name, player -> valueFunction.get(), operation);
        }

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, Supplier<Double> extendedValueFunction, Supplier<ISkill<IWerewolfPlayer>> extendedSkill, AttributeModifier.Operation operation) {
            this(attribute, dayUuid, nightUuid, dayModifier, name, player -> player.getSkillHandler().isSkillEnabled(extendedSkill.get()) ? extendedValueFunction.get() : valueFunction.get(), operation);
        }

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Function<IWerewolfPlayer, Double> valueFunction, AttributeModifier.Operation operation) {
            this.attribute = attribute;
            this.dayUuid = dayUuid;
            this.nightUuid = nightUuid;
            this.name = name;
            this.value = valueFunction;
            this.operation = operation;
            this.dayModifier = dayModifier;
        }

        public AttributeModifier create(IWerewolfPlayer player, boolean night) {
            return new AttributeModifier(night ? nightUuid : dayUuid, name, night ? value.apply(player) : value.apply(player) * dayModifier, operation);
        }
    }

    protected final List<Modifier> attributes = new ArrayList<>();
    @Nonnull
    private final WerewolfForm form;

    public WerewolfFormAction(@Nonnull WerewolfForm form) {
        ALL_ACTION.add(this);
        this.form = form;
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf, ActivationContext context) {
        Player player = werewolf.asEntity();
        float healthPerc = player.getHealth() / player.getMaxHealth();
        if (isWerewolfFormActionActive(werewolf.getActionHandler())) {
            FormHelper.deactivateWerewolfActions(werewolf);
        }
        ((WerewolfPlayer) werewolf).setForm(this, this.form);
        this.checkDayNightModifier(werewolf);
        player.setHealth(player.getMaxHealth() * healthPerc);
        player.refreshDisplayName();
        return true;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).setForm(this, this.form);
        werewolfPlayer.asEntity().refreshDisplayName();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        float healthPerc = player.getHealth() / player.getMaxHealth();
        ((WerewolfPlayer) werewolf).setForm(this, WerewolfForm.NONE);
        this.removeModifier(werewolf);
        player.setHealth(player.getMaxHealth() * healthPerc);
        player.refreshDisplayName();
        if (werewolf.getActionHandler().isActionActive(ModActions.RAGE.get())) {
            werewolf.getActionHandler().deactivateAction(ModActions.RAGE.get());
        }
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        ((WerewolfPlayer) werewolf).setForm(this, this.form);
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
        boolean night = Helper.isNight(werewolfPlayer.asEntity().getCommandSenderWorld());
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
    public boolean canBeUsedBy(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        if (player instanceof ServerPlayer && (!PermissionAPI.getPermission((ServerPlayer) player, Permissions.TRANSFORMATION) || !PermissionAPI.getPermission((ServerPlayer) player, Permissions.FORM))) {
            return false;
        }
        if (player.isPassenger() && !this.form.isHumanLike()) return false;
        boolean active = werewolf.getActionHandler().isActionActive(this);
        if (active) {
            if (Helper.isFullMoon(player.getCommandSenderWorld())) {
                return werewolf.getSkillHandler().isSkillEnabled(ModSkills.FREE_WILL.get());
            } else {
                return true;
            }
        } else {
            if (werewolf.getForm().isTransformed()) {
                return true;
            } else {
                if (player.level().getBiome(player.blockPosition()).is(ModBiomes.WEREWOLF_FOREST)) {
                    return true;
                } else {
                    return ((WerewolfPlayer) werewolf).getSpecialAttributes().transformationTime < 0.7;
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
        int limit = WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1.get());
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2.get());
        if (duration1 || duration2) {
            if (duration2) {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_general_2.get() * 20;
            } else {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_general_1.get() * 20;
            }
        }
        return limit;
    }

    @Nonnull
    public WerewolfForm getForm() {
        return form;
    }

    public static class FormActionContext extends ActionHandler.ActivationContext {


    }
}
