package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HowlingAction extends DefaultWerewolfAction implements IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_enabled.get();
    }

    @Override
    protected boolean activate(@NotNull IWerewolfPlayer werewolfPlayer, ActivationContext context) {
        Player player = werewolfPlayer.getRepresentingPlayer();
        if (player.getEffect(ModEffects.HOWLING.get()) != null) {
            return false;
        }
        AABB bb = new AABB(player.blockPosition());
        bb.inflate(10);
        List<LivingEntity> entities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, bb);
        entities.forEach(entity -> entity.addEffect(new MobEffectInstance(ModEffects.HOWLING.get(), (WerewolvesConfig.BALANCE.SKILLS.howling_duration.get() + WerewolvesConfig.BALANCE.SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true)));
        werewolfPlayer.getRepresentingPlayer().playSound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), 1.0F, 1.0F);
        werewolfPlayer.getRepresentingPlayer().playNotifySound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        if (werewolfPlayer.getSkillHandler().isSkillEnabled(ModSkills.WOLF_PACK.get())) {
            Level world = player.getCommandSenderWorld();

            int wolfAmount = WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_amount.get();
            if (werewolfPlayer.getSkillHandler().isRefinementEquipped(ModRefinements.MORE_WOLVES.get())) {
                wolfAmount += WerewolvesConfig.BALANCE.REFINEMENTS.more_wolves.get();
            }
            for (int i = 0; i < wolfAmount; ++i) {
                AggressiveWolfEntity wolf = ModEntities.WOLF.get().create(world);
                wolf.tame(player);
                wolf.restrictLiveSpan(WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_duration.get() * 20);
                UtilLib.spawnEntityInWorld((ServerLevel) world, new AABB(player.blockPosition()).inflate(1), wolf, 10, new ArrayList<>(), MobSpawnType.EVENT);
            }
        }
        return true;
    }

    @Override
    public boolean canBeUsedBy(@NotNull IWerewolfPlayer player) {
        return player.getRepresentingPlayer().getEffect(ModEffects.HOWLING.get()) == null;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.howling_cooldown.get() * 20;
    }
}
