package de.teamlapen.werewolves.entities;

import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class FireCrackerItemEntity extends ThrowableEntity implements IRendersAsItem {

    private static final ItemStack stack = new ItemStack(ModItems.fire_cracker);

    private int impactTime = 20;
    private boolean impact = false;

    public FireCrackerItemEntity(EntityType<? extends FireCrackerItemEntity> type, World worldIn) {
        super(type, worldIn);
        this.noClip = false;
    }

    public FireCrackerItemEntity(World worldIn, LivingEntity thrower, int impactTime) {
        super(ModEntities.fire_cracker, thrower, worldIn);
        this.noClip = false;
        this.impactTime = impactTime;
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerData() {

    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return stack;
    }

    @Override
    protected void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("impactTime", this.impactTime);
        compound.putBoolean("impact", this.impact);
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        this.impactTime = compound.getInt("impactTime");
        this.impact = compound.getBoolean("impact");
    }

    @Override
    protected float getGravityVelocity() {
        return 0.05F;
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
        super.func_230299_a_(p_230299_1_);
        this.impact = true;
    }

    @Override
    public void tick() {
        if (!impact) {
            super.tick();
        }
        if (this.ticksExisted >= this.impactTime) {
            this.explode();
        }
    }

    protected void explode(){
        this.remove();
    }
}
