package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class HowlingAction extends DefaultWerewolfAction {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        PlayerEntity player = werewolfPlayer.getRepresentingPlayer();
        if (player.getActivePotionEffect(ModEffects.howling) != null) {
            return false;
        }
        AxisAlignedBB bb = new AxisAlignedBB(player.getPosition());
        bb.grow(10);
        List<LivingEntity> entities = player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, bb);
        entities.forEach(entity -> entity.addPotionEffect(new EffectInstance(ModEffects.howling, (WerewolvesConfig.BALANCE.SKILLS.howling_duration.get() + WerewolvesConfig.BALANCE.SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true)));

        if (werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.wolf_pack)) {
            World world = player.getEntityWorld();

            int wolfAmount = 3;
            for (int i = 0; i < wolfAmount; ++i) {
                AggressiveWolfEntity wolf = ModEntities.wolf.create(world);
                wolf.setTamedBy(player);
                wolf.restrictLiveSpan(WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_duration.get() * 20);
                UtilLib.spawnEntityInWorld((ServerWorld) world, new AxisAlignedBB(player.getPosition()).grow(1), wolf, 10, new ArrayList<>(), SpawnReason.EVENT);
            }
        }
        return true;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        return player.getRepresentingPlayer().getActivePotionEffect(ModEffects.howling) == null;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_cooldown.get() * 20;
    }
}
