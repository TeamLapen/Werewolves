package de.teamlapen.werewolves.mixin;

import com.mojang.datafixers.util.Either;
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SuppressWarnings("DuplicatedCode")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract Either<PlayerEntity.SleepResult, Unit> startSleepInBed(BlockPos p_213819_1_);

    @Shadow public abstract void startFallFlying();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @ModifyArg(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;hurt(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 0), index = 1)
    public float applyOil(float damage) {
        MutableFloat damagef = new MutableFloat(damage);
        ItemStack stack = this.getItemInHand(Hand.MAIN_HAND);
        WeaponOilHelper.executeAndReduce(stack, (oil, duration) -> {
            if(oil.canEffect(stack, this)){
                damagef.add(oil.getAdditionalDamage(stack, this, damage));
            }
        });
        return damagef.floatValue();
    }
}
