package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModRefinements;
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
import net.minecraftforge.server.permission.PermissionAPI;

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

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, AttributeModifier.Operation operation) {
            this(attribute, dayUuid, nightUuid, dayModifier, name, player -> valueFunction.get(), operation);
        }

        public Modifier(Attribute attribute, UUID dayUuid, UUID nightUuid, double dayModifier, String name, Supplier<Double> valueFunction, Supplier<Double> extendedValueFunction, ISkill<IWerewolfPlayer> extendedSkill, AttributeModifier.Operation operation) {
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
    protected boolean activate(IWerewolfPlayer werewolf) {
        float healthPerc = werewolf.getRepresentingPlayer().getHealth() / werewolf.getRepresentingPlayer().getMaxHealth();
        if (isWerewolfFormActionActive(werewolf.getActionHandler())) {
            FormHelper.deactivateWerewolfActions(werewolf);
        }
        ((WerewolfPlayer) werewolf).setForm(this, this.form);
        this.removeArmorModifier(werewolf);
        this.applyModifier(werewolf);
        werewolf.getRepresentingPlayer().setHealth(werewolf.getRepresentingPlayer().getMaxHealth() * healthPerc);
        werewolf.getRepresentingPlayer().refreshDisplayName();
        return true;
    }

    protected void removeArmorModifier(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).removeArmorModifier();
    }

    protected void addArmorModifier(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).addArmorModifier();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).switchForm(this.form);
        werewolfPlayer.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        float healthPerc = werewolf.getRepresentingPlayer().getHealth() / werewolf.getRepresentingPlayer().getMaxHealth();
        ((WerewolfPlayer) werewolf).setForm(this, WerewolfForm.NONE);
        this.addArmorModifier(werewolf);
        this.removeModifier(werewolf);
        werewolf.getRepresentingPlayer().setHealth(werewolf.getRepresentingPlayer().getMaxHealth() * healthPerc);
        werewolf.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        this.removeArmorModifier(werewolf);
        werewolf.getRepresentingPlayer().refreshDisplayName();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolfPlayer) {
            if (werewolfPlayer.getRepresentingPlayer().level.getGameTime() % 20 == 0) {
                checkDayNightModifier(werewolfPlayer);
            }

            if (!usesTransformationTime(werewolfPlayer.getRepresentingPlayer())) {
                return false;
            }
        return increaseWerewolfTime(werewolfPlayer) || (werewolfPlayer.getRepresentingPlayer() instanceof ServerPlayer && !PermissionAPI.getPermission((ServerPlayer) werewolfPlayer.getRepresentingPlayer(), Permissions.FORM));
    }

    protected boolean usesTransformationTime(LivingEntity player) {
        return !Helper.isNight(player.level) && !FormHelper.isInWerewolfBiome(player.level, player.blockPosition());
    }

    protected boolean increaseWerewolfTime(IWerewolfPlayer werewolfPlayer) {
        if(!consumesWerewolfTime()) return false;
        return (((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().transformationTime = Mth.clamp(((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().transformationTime + ((double) 1 / (double) getTimeModifier(werewolfPlayer)),0,1)) == 1;
    }

    public void checkDayNightModifier(IWerewolfPlayer werewolfPlayer) {
        Player player = werewolfPlayer.getRepresentingPlayer();
        boolean night = Helper.isNight(player.getCommandSenderWorld());
        for (Modifier attribute : this.attributes) {
            if (player.getAttribute(attribute.attribute).getModifier(!night ? attribute.nightUuid : attribute.dayUuid) != null) {
                removeModifier(werewolfPlayer);
                applyModifier(werewolfPlayer);
            }
        }
    }

    public void applyModifier(IWerewolfPlayer werewolf) {
        Player player = werewolf.getRepresentingPlayer();
        boolean night = Helper.isNight(player.getCommandSenderWorld());
        for (Modifier attribute : this.attributes) {
            AttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null && ins.getModifier(attribute.dayUuid) == null) {
                ins.addPermanentModifier(attribute.create(werewolf, night));
            }
        }
    }

    public void removeModifier(IWerewolfPlayer werewolf) {
        Player player = werewolf.getRepresentingPlayer();
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
        return 10000;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        if (player.getRepresentingPlayer() instanceof ServerPlayer && (!PermissionAPI.getPermission((ServerPlayer) player.getRepresentingPlayer(), Permissions.TRANSFORMATION) || !PermissionAPI.getPermission((ServerPlayer) player.getRepresentingPlayer(), Permissions.FORM)))
            return false;
        boolean active = player.getActionHandler().isActionActive(this);
        if (Helper.isFullMoon(player.getRepresentingPlayer().getCommandSenderWorld()) && active) return false;
        return consumesWerewolfTime() || active || (((WerewolfPlayer) player).getSpecialAttributes().transformationTime < 0.7) || player.getRepresentingPlayer().level.getBiome(player.getRepresentingEntity().blockPosition()) == ModBiomes.werewolf_heaven;
    }

    public boolean consumesWerewolfTime() {
        return true;
    }

    /**
     * ticks this action can be used
     */
    public int getTimeModifier(IWerewolfPlayer werewolf) {
        int limit = WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_general_1);
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_general_2);
        if (duration1 || duration2) {
            if (duration2) {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_general_2.get() * 20;
            } else {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_general_1.get() * 20;
            }
        }
        return limit;
    }
}
