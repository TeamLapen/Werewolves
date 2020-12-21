package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

public class SummonWolfAction extends DefaultWerewolfAction {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.wolf_pack_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {

        PlayerEntity player = werewolf.getRepresentingPlayer();
        World world = player.getEntityWorld();

        int wolfAmount = 3;
        for (int i = 0; i < wolfAmount; ++i) {
            AggressiveWolfEntity wolf = ModEntities.wolf.create(world);
            wolf.setTamedBy(player);
            wolf.restrictLiveSpan(WerewolvesConfig.BALANCE.SKILLS.wolf_pack_wolf_duration.get() * 20);
            UtilLib.spawnEntityInWorld((ServerWorld) world, new AxisAlignedBB(player.getPosition()).grow(1), wolf, 10, new ArrayList<>(), SpawnReason.EVENT);
        }
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.wolf_pack_cooldown.get() * 20;
    }
}