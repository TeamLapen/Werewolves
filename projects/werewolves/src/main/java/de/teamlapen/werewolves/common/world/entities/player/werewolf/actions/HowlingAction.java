package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;
import de.teamlapen.faction.common.util.SpawnUtil;
import de.teamlapen.werewolves.common.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

public class HowlingAction extends DefaultWerewolfAction implements IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.howling_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer werewolfPlayer, ActivationContext context) {
        if (werewolfPlayer.asEntity().getEffect(ModEffects.HOWLING) != null) {
            return IActionResult.RESTRICTED;
        }

        applyHowling(werewolfPlayer);
        spawnWolves(werewolfPlayer);

        if (werewolfPlayer.asEntity() instanceof ServerPlayer serverPlayer) {
            ModAdvancements.TRIGGER_VAMPIRE_ACTION.get().trigger(serverPlayer, WerewolfActionCriterionTrigger.Action.HOWLING);
        }

        return IActionResult.SUCCESS;
    }

    protected void applyHowling(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        var effect = new MobEffectInstance(ModEffects.HOWLING, (ModConfig.balance().SKILLS.howling_duration.get() + ModConfig.balance().SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true);
        AABB bb = new AABB(player.blockPosition());
        bb.inflate(10);
        player.level().getEntities(EntityTypeTest.forClass(LivingEntity.class), bb, WUtils.IS_WEREWOLF).forEach(s -> s.addEffect(effect));
        player.playSound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), 1.0F, 1.0F);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new ClientboundSoundPacket(ModSounds.ENTITY_WEREWOLF_HOWL, SoundSource.PLAYERS, player.getX(),player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextLong()));
        }
    }

    protected void spawnWolves(IWerewolfPlayer werewolf) {
        Player player = werewolf.asEntity();
        if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PACK)) {
            Level world = player.level();

            int wolfAmount = ModConfig.balance().SKILLS.wolf_pack_wolf_amount.get();
            if (werewolf.getRefinementHandler().isRefinementEquipped(ModRefinements.MORE_WOLVES)) {
                wolfAmount += ModConfig.balance().REFINEMENTS.more_wolves.get();
            }
            for (int i = 0; i < wolfAmount; ++i) {
                AggressiveWolfEntity wolf = ModEntities.WOLF.get().create(world, EntitySpawnReason.EVENT);
                if (wolf != null) {
                    wolf.tame(player);
                    wolf.restrictLiveSpan(ModConfig.balance().SKILLS.wolf_pack_wolf_duration.get() * 20);
                    SpawnUtil.spawnEntityInWorld((ServerLevel) world, new AABB(player.blockPosition()).inflate(1), wolf, 10, new ArrayList<>(), EntitySpawnReason.EVENT);
                }
            }
        }
    }

    @Override
    public IActionResult canBeUsedBy(IWerewolfPlayer player) {
        return (player.asEntity().getEffect(ModEffects.HOWLING) == null) ? IActionResult.SUCCESS : IActionResult.RESTRICTED;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.howling_cooldown.get() * 20;
    }


}
