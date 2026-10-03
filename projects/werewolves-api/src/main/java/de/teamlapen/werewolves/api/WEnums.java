package de.teamlapen.werewolves.api;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.common.damagesource.IDeathMessageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class WEnums {
    public static final EnumProxy<MobCategory> WEREWOLF_CATEGORY = new EnumProxy<>(MobCategory.class, WReference.MODID + ":werewolf", 8, false, false, 128);
    public static final EnumProxy<Boat.Type> JACARANDA_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, ModRegistryItems.JACARANDA_PLANKS, WReference.MODID + ":jacaranda", ModRegistryItems.JACARANDA_BOAT, ModRegistryItems.JACARANDA_CHEST_BOAT, (Supplier<Item>)() -> Items.STICK, false);
    public static final EnumProxy<Boat.Type> MAGIC_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, ModRegistryItems.MAGIC_PLANKS, WReference.MODID + ":magic", ModRegistryItems.MAGIC_BOAT, ModRegistryItems.MAGIC_CHEST_BOAT, (Supplier<Item>)() -> Items.STICK, false);
    public static final EnumProxy<DeathMessageType> WEREWOLF_BITE = new EnumProxy<>(DeathMessageType.class, WReference.MODID + ":bite", WEnums.biteProvider());




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
