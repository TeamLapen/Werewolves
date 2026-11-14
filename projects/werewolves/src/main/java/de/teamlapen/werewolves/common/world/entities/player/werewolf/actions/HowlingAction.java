package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.common.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class HowlingAction extends DefaultWerewolfAction implements IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return ModConfig.BALANCE.SKILLS.howling_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer, ActivationContext context) {
        if (werewolfPlayer.asEntity().getEffect(ModEffects.HOWLING) != null) {
            return false;
        }

        applyHowling(werewolfPlayer);
        spawnWolves(werewolfPlayer);

        if (werewolfPlayer.asEntity() instanceof ServerPlayer serverPlayer) {
            ModAdvancements.TRIGGER_VAMPIRE_ACTION.get().trigger(serverPlayer, WerewolfActionCriterionTrigger.Action.HOWLING);
        }

        return true;
    }

    protected void applyHowling(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        var effect = new MobEffectInstance(ModEffects.HOWLING, (ModConfig.BALANCE.SKILLS.howling_duration.get() + ModConfig.BALANCE.SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true);
        AABB bb = new AABB(player.blockPosition());
        bb.inflate(10);
        player.getCommandSenderWorld().getEntities(EntityTypeTest.forClass(LivingEntity.class), bb, WUtils.IS_WEREWOLF).forEach(s -> s.addEffect(effect));
        player.playSound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), 1.0F, 1.0F);
        player.playNotifySound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    protected void spawnWolves(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PACK.get())) {
            Level world = player.level();

            int wolfAmount = ModConfig.BALANCE.SKILLS.wolf_pack_wolf_amount.get();
            if (werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.MORE_WOLVES.get())) {
                wolfAmount += ModConfig.BALANCE.REFINEMENTS.more_wolves.get();
            }
            for (int i = 0; i < wolfAmount; ++i) {
                AggressiveWolfEntity wolf = ModEntities.WOLF.get().create(world);
                if (wolf != null) {
                    wolf.tame(player);
                    wolf.restrictLiveSpan(ModConfig.BALANCE.SKILLS.wolf_pack_wolf_duration.get() * 20);
                    UtilLib.spawnEntityInWorld((ServerLevel) world, new AABB(player.blockPosition()).inflate(1), wolf, 10, new ArrayList<>(), MobSpawnType.EVENT);
                }
            }
        }
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        return player.asEntity().getEffect(ModEffects.HOWLING) == null;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return ModConfig.BALANCE.SKILLS.howling_cooldown.get() * 20;
    }

    @Override
    public boolean showHudCooldown(Player player) {
        return true;
    }
}
