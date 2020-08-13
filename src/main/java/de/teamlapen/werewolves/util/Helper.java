package de.teamlapen.werewolves.util;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.werewolves.entities.ExtendedWerewolf;
import de.teamlapen.werewolves.entities.IExtendedWerewolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class Helper extends de.teamlapen.vampirism.util.Helper {

    public static boolean isWerewolf(Entity entity) {
        boolean faction = WReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
        if (entity instanceof AbstractVillagerEntity) {
            return faction || ExtendedWerewolf.getSafe((AbstractVillagerEntity) entity).map(IExtendedWerewolf::isWerewolf).orElse(false);
        }
        return faction;
    }

    public static boolean isWerewolf(PlayerEntity entity) {
        return VampirismAPI.getFactionPlayerHandler((entity)).map(h -> WReference.WEREWOLF_FACTION.equals(h.getCurrentFaction())).orElse(false);
    }

    public static boolean hasFaction(Entity entity) {
        if (VampirismAPI.factionRegistry().getFaction(entity) != null) {
            return true;
        } else return isWerewolf(entity);
    }

    public static BlockPos multiplyBlockPos(BlockPos pos, double amount) {
        return new BlockPos(pos.getX() * amount, pos.getY() * amount, pos.getZ() * amount);
    }
}
