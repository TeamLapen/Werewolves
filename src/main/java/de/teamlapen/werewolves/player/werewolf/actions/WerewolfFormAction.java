package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public abstract class WerewolfFormAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<WerewolfFormAction> ALL_ACTION = new HashSet<>();


    public static boolean isWerewolfFormActionActive(IActionHandler<IWerewolfPlayer> handler) {
        return ALL_ACTION.stream().anyMatch(handler::isActionActive);
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
        public final UUID uuid;
        public final String name;
        public final Supplier<Double> value;
        public final AttributeModifier.Operation operation;

        public Modifier(Attribute attribute, UUID uuid, String name, Supplier<Double> value, AttributeModifier.Operation operation) {
            this.attribute = attribute;
            this.uuid = uuid;
            this.name = name;
            this.value = value;
            this.operation = operation;
        }

        public AttributeModifier create(){
            return new AttributeModifier(uuid, name, value.get(), operation);
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
        ((WerewolfPlayer) werewolfPlayer).switchForm(this.form);
        ((WerewolfPlayer) werewolfPlayer).activateWerewolfForm();
        this.applyModifier(werewolfPlayer.getRepresentingPlayer());
        return true;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).switchForm(this.form);
        ((WerewolfPlayer) werewolfPlayer).activateWerewolfForm();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).switchForm(WerewolfForm.NONE);
        ((WerewolfPlayer) werewolfPlayer).deactivateWerewolfForm();
        this.removeModifier(werewolfPlayer.getRepresentingPlayer());
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolfPlayer) {
        ((WerewolfPlayer) werewolfPlayer).activateWerewolfForm();
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer werewolfPlayer) {
        if (Helper.isNight(werewolfPlayer.getRepresentingPlayer().getEntityWorld())) {
            return false;
        }
        return ++((WerewolfPlayer) werewolfPlayer).getSpecialAttributes().werewolfTime > WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
    }

    public void applyModifier(PlayerEntity player) {
        for (Modifier attribute : this.attributes) {
            ModifiableAttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null && ins.getModifier(attribute.uuid) == null) {
                ins.applyPersistentModifier(attribute.create());
            }
        }
    }

    public void removeModifier(PlayerEntity player){
        for (Modifier attribute : this.attributes) {
            ModifiableAttributeInstance ins = player.getAttribute(attribute.attribute);
            if (ins != null) {
                ins.removeModifier(attribute.uuid);
            }
        }
    }

    @Override
    public int getDuration(int level) {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        if (Helper.isFullMoon(player.getRepresentingPlayer().getEntityWorld()) | Helper.isFormActionActive(player) && !player.getActionHandler().isActionActive(this)) {
            return false;
        }
        return player.getRepresentingPlayer().world.getBiome(player.getRepresentingEntity().getPosition()) == ModBiomes.werewolf_heaven || (getDurationPercentage(player) > 0.3) || player.getActionHandler().isActionActive(this);
    }
}
