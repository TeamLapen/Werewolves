package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

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

    /**
     * @return how much percentage is left
     */
    public static float getDurationPercentage(IWerewolfPlayer player) {
        long durationMax = WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
        return 1 - (float) ((WerewolfPlayer) player).getSpecialAttributes().werewolfTime / durationMax;
    }

    protected static class Modifier {

        public final Attribute attribute;
        public final UUID dayUuid;
        public final UUID nightUuid;
        public final String name;
        public final Function<IWerewolfPlayer, Double> value;
        public final AttributeModifier.Operation operation;
        public final double dayModifier;

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, AttributeModifier.Operation operation) {
            this(attribute, dayUuid, nightUuid, dayModifier, name, player -> valueFunction.get(), operation);
        }

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, Supplier<Double> extendedValueFunction, ISkill extendedSkill, AttributeModifier.Operation operation) {
            this(attribute, dayUuid, nightUuid, dayModifier, name, player -> player.getSkillHandler().isSkillEnabled(extendedSkill) ? extendedValueFunction.get() : valueFunction.get(), operation);
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
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        if (FormHelper.isFormActionActive(werewolfPlayer)) {
            FormHelper.deactivateWerewolfActions(werewolfPlayer);
        }
        ((WerewolfPlayer) werewolfPlayer).setForm(this, this.form);
        if (!(werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.wear_armor) && this.form.isHumanLike())) {
            ((WerewolfPlayer) werewolfPlayer).removeArmorModifier();
        }
        this.applyModifier(werewolfPlayer);
        werewolfPlayer.getRepresentingPlayer().refreshDisplayName();
        return true;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).switchForm(this.form);
        werewolfPlayer.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).setForm(this, WerewolfForm.NONE);
        ((WerewolfPlayer) werewolfPlayer).addArmorModifier();
        this.removeModifier(werewolfPlayer);
        werewolfPlayer.getRepresentingPlayer().refreshDisplayName();
        if (werewolfPlayer.getActionHandler().isActionActive(ModActions.hide_name)) {
            werewolfPlayer.getActionHandler().toggleAction(ModActions.hide_name);
        }
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolfPlayer) {
        if (!(werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.wear_armor) && this.form.isHumanLike())) {
            ((WerewolfPlayer) werewolfPlayer).removeArmorModifier();
        }
        werewolfPlayer.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolfPlayer) {
        if (!werewolfPlayer.getRepresentingPlayer().getCommandSenderWorld().isClientSide) {
            checkDayNightModifier(werewolfPlayer);
        }
        if (!FormHelper.isWerewolfFormTicking(werewolfPlayer.getRepresentingPlayer().getCommandSenderWorld(), werewolfPlayer.getRepresentingPlayer().blockPosition())) {
            return false;
        }
        return increaseWerewolfTime(werewolfPlayer);
    }
    
    protected boolean increaseWerewolfTime(IWerewolfPlayer werewolfPlayer) {
        return ++((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().werewolfTime > WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
    }

    public void checkDayNightModifier(IWerewolfPlayer werewolfPlayer) {
        PlayerEntity player = werewolfPlayer.getRepresentingPlayer();
        boolean night = Helper.isNight(player.getCommandSenderWorld());
        for (Modifier attribute : this.attributes) {
            if (player.getAttribute(attribute.attribute).getModifier(!night ? attribute.nightUuid : attribute.dayUuid) != null) {
                removeModifier(werewolfPlayer);
                applyModifier(werewolfPlayer);
            }
        }
    }

    public void applyModifier(IWerewolfPlayer werewolf) {
        PlayerEntity player = werewolf.getRepresentingPlayer();
        boolean night = Helper.isNight(player.getCommandSenderWorld());
        for (Modifier attribute : this.attributes) {
            ModifiableAttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null && ins.getModifier(attribute.dayUuid) == null) {
                ins.addPermanentModifier(attribute.create(werewolf, night));
            }
        }
    }

    public void removeModifier(IWerewolfPlayer werewolf) {
        PlayerEntity player = werewolf.getRepresentingPlayer();
        for (Modifier attribute : this.attributes) {
            ModifiableAttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null) {
                ins.removeModifier(attribute.dayUuid);
                ins.removeModifier(attribute.nightUuid);
            }
        }
    }

    @Override
    public int getDuration(int level) {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        boolean active = player.getActionHandler().isActionActive(this);
        if (Helper.isFullMoon(player.getRepresentingPlayer().getCommandSenderWorld()) && active)
            return false;
        return player.getRepresentingPlayer().level.getBiome(player.getRepresentingEntity().blockPosition()) == ModBiomes.werewolf_heaven || (getDurationPercentage(player) > 0.3) || active;
    }
}
