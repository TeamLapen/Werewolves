package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class HowlingAction extends DefaultWerewolfAction implements IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        PlayerEntity player = werewolfPlayer.getRepresentingPlayer();
        if (player.getEffect(ModEffects.HOWLING.get()) != null) {
            return false;
        }
        AxisAlignedBB bb = new AxisAlignedBB(player.blockPosition());
        bb.inflate(10);
        List<LivingEntity> entities = player.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, bb);
        entities.forEach(entity -> entity.addEffect(new EffectInstance(ModEffects.HOWLING.get(), (WerewolvesConfig.BALANCE.SKILLS.howling_duration.get() + WerewolvesConfig.BALANCE.SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true)));
        werewolfPlayer.getRepresentingPlayer().playSound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), 1.0F, 1.0F);
        werewolfPlayer.getRepresentingPlayer().playNotifySound(ModSounds.ENTITY_WEREWOLF_HOWL.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (werewolfPlayer.getSkillHandler().isSkillEnabled(WerewolfSkills.WOLF_PACK.get())) {
            World world = player.getCommandSenderWorld();

            int wolfAmount = WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_amount.get();
            if (werewolfPlayer.getSkillHandler().isRefinementEquipped(ModRefinements.MORE_WOLVES.get())) {
                wolfAmount += WerewolvesConfig.BALANCE.REFINEMENTS.more_wolves.get();
            }
            for (int i = 0; i < wolfAmount; ++i) {
                ModEntities.WOLF.map(a -> a.create(world)).ifPresent(wolf -> {
                    wolf.tame(player);
                    wolf.restrictLiveSpan(WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_duration.get() * 20);
                    UtilLib.spawnEntityInWorld((ServerWorld) world, new AxisAlignedBB(player.blockPosition()).inflate(1), wolf, 10, new ArrayList<>(), SpawnReason.EVENT);
                });
            }
        }
        return true;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        return player.getRepresentingPlayer().getEffect(ModEffects.HOWLING.get()) == null;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_cooldown.get() * 20;
    }
}
