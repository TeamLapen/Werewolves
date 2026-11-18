package de.teamlapen.werewolves.api;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.common.damagesource.IDeathMessageProvider;

public class WEnums {
    public static final EnumProxy<MobCategory> WEREWOLF_CATEGORY = new EnumProxy<>(MobCategory.class, "werewolves:werewolf", 8, false, false, 128);
    public static final EnumProxy<DeathMessageType> WEREWOLF_BITE = new EnumProxy<>(DeathMessageType.class, WReference.MODID + ":bite", WEnums.biteProvider());



//
    private static IDeathMessageProvider biteProvider() {
        return (entity, lastEntry, mostSignificantFall) -> {
            DamageSource source = lastEntry.source();
            String s = "death.attack." + source.type().msgId();
            if (source.getEntity() == null && source.getDirectEntity() == null) {
                LivingEntity livingentity1 = entity.getKillCredit();
                String s1 = s + ".player";
                return livingentity1 != null
                        ? Component.translatable(s1, entity.getDisplayName(), livingentity1.getDisplayName())
                        : Component.translatable(s, entity.getDisplayName());
            } else {
                Component component = source.getEntity() == null ? source.getDirectEntity().getDisplayName() : source.getEntity().getDisplayName();
                return Component.translatable(s, entity.getDisplayName(), component);
            }
        };
    }

}
