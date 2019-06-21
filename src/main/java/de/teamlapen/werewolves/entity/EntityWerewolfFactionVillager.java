package de.teamlapen.werewolves.entity;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.entity.EntityFactionVillager;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import net.minecraft.world.World;

public class EntityWerewolfFactionVillager extends EntityFactionVillager implements IWerewolf {

    public EntityWerewolfFactionVillager(World worldIn) {
        super(worldIn);
    }

    @Override
    public IFaction getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
