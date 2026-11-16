package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.vampirism.api.world.items.QuarrelProperties;
import de.teamlapen.vampirism.common.world.items.crossbow.behavior.QuarrelBehavior;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;

public class SilverBehavior extends QuarrelBehavior {
    public SilverBehavior() {
        super(QuarrelProperties.of(0xffc0c0c0).baseDamage(3).effectDescription(Component.translatable("tooltip.werewolves.quarrel_silver")).build());
    }

    @Override
    public void onHitEntity(ItemStack arrow, LivingEntity entity, AbstractArrow arrowEntity, Entity shootingEntity) {
        if (Helper.isWerewolf(entity)) {
            entity.addEffect(SilverEffect.createSilverEffect(entity, ModConfig.balance().UTIL.silverBoltEffectDuration.get() * 20, 1));
        }
    }
}
