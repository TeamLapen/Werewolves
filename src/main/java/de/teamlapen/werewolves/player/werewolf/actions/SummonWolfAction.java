package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SummonWolfAction extends DefaultWerewolfAction {
    @Override
    public boolean isEnabled() {
        return true; //TODO config
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {

        PlayerEntity player = werewolf.getRepresentingPlayer();
        World world = player.getEntityWorld();

        int wolfAmount = 3;
        for (int i = 0; i < wolfAmount; ++i) {
            AggressiveWolfEntity wolf = ModEntities.wolf.create(world);
            wolf.setTamedBy(player);
            wolf.restrictLiveSpan();
            UtilLib.spawnEntityInWorld(world, new AxisAlignedBB(player.getPosition()).grow(1), wolf, 10, new ArrayList<>(), SpawnReason.EVENT);
        }
        return true;
    }

    @Override
    public int getCooldown() {
        return 10; //TODO config
    }
}