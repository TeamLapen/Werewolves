package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@SuppressWarnings("DuplicatedCode")
@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    @Shadow public abstract boolean startRiding(Entity p_184205_1_, boolean p_184205_2_);

    protected MobEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @ModifyArg(method = "doHurtTarget(Lnet/minecraft/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;hurt(Lnet/minecraft/util/DamageSource;F)Z", ordinal = 0), index = 1)
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
