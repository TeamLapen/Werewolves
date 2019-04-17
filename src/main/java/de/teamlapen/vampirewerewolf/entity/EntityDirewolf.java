package de.teamlapen.vampirewerewolf.entity;

import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDirewolf extends EntityMob {

    public EntityDirewolf(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.85F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(9, new EntityAIWander(this, 0.5));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));

        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(VReference.WEREWOLF_FACTION, true, false, true, true, null)));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityCreature.class, 5, true, false, VampirismAPI.factionRegistry().getPredicate(VReference.WEREWOLF_FACTION, false, true, false, false, null)));
    }
}
