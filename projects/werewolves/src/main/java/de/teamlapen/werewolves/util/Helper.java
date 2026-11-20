package de.teamlapen.werewolves.util;

import de.teamlapen.faction.api.FactionsApi;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.tags.ModItemTags;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.faction.common.core.FactionDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Helper {

    public static boolean isWerewolf(Entity entity) {
        return (entity instanceof Player player && isWerewolf(player)) || IFaction.is(FactionsApi.services().factionHelper().getFaction(entity), ModFactions.WEREWOLF);
    }

    public static boolean isWerewolf(Player entity) {
        return FactionsApi.factionPlayerHandler(entity).isInFaction(ModFactions.WEREWOLF);
    }

    public static boolean hasFaction(Entity entity) {
        if (!IFaction.isNeutral(FactionsApi.services().factionHelper().getFaction(entity))) {
            return true;
        } else return isWerewolf(entity);
    }

    public static BlockPos multiplyBlockPos(BlockPos pos, double amount) {
        return new BlockPos((int) (pos.getX() * amount), (int) (pos.getY() * amount), (int) (pos.getZ() * amount));
    }

    public static boolean canBecomeWerewolf(Player player) {
        return FactionPlayerHandler.get(player).canJoin(ModFactions.WEREWOLF);
    }

    public static boolean isNight(Level world) {
        return world.isDarkOutside();
    }

    public static boolean isFullMoon(Level world, BlockPos pos) {
        return world.isDarkOutside() && world.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, pos) == MoonPhase.FULL_MOON;
    }

    public static Map<Item, Integer> getMissingItems(Container inventory, Item[] items, int[] amount) {
        Map<Item, Integer> missing = new HashMap<>();
        for (int i = 0; i < items.length; i++) {
            missing.put(items[i], amount[i]);
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            missing.computeIfPresent(stack.getItem(), (item, amount1) -> amount1 - stack.getCount());
        }
        missing.entrySet().removeIf(s -> s.getValue() <= 0);
        return missing;
    }

    @Deprecated
    @SuppressWarnings("ConstantConditions")
    public static boolean canWerewolfEatItem(LivingEntity entity, ItemStack stack) {
        return isMeat(entity, stack);
    }

    @Deprecated
    public static boolean canWerewolfPlayerEatItem(Player player, ItemStack stack) {
        return canWerewolfEatItem(player, stack) || (isWerewolf(player) && WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT));
    }

    public static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
        return (stack.has(DataComponents.FOOD) || stack.has(FactionDataComponents.FACTION_FOOD)) && (stack.is(ModItemTags.WEREWOLF_FOOD) || ModConfig.server().isCustomMeatItems(stack.getItem()));
    }

    @Deprecated
    public static boolean isMeat(ItemStack stack) {
        return isMeat(null, stack);
    }

    public static boolean isRawMeat( @Nullable LivingEntity entity, ItemStack stack) {
        return isMeat(entity, stack) && (stack.is(ModItemTags.RAW_FOOD) || ModConfig.server().isCustomRawMeatItems(stack.getItem()));
    }

    @Deprecated
    public static boolean isRawMeat(ItemStack stack) {
        return isRawMeat(null, stack);
    }

    /**
     * Should only be called when {@link #isMeat(net.minecraft.world.entity.LivingEntity, net.minecraft.world.item.ItemStack)} was {@code true} previously.<p>
     * Otherwise use {@link #isRawMeat(net.minecraft.world.entity.LivingEntity, net.minecraft.world.item.ItemStack)}
     */
    public static boolean isRawMeatSkipMeat(ItemStack stack) {
        return stack.is(ModItemTags.RAW_FOOD);
    }

    public static boolean canEat(LivingEntity entity, ItemStack stack) {
        return Helper.isMeat(entity, stack) || !(entity instanceof Player player) || !Helper.isWerewolf(player) || WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT);
    }

    public static Optional<IWerewolf> asIWerewolf(LivingEntity entity) {
        if (entity instanceof IWerewolf werewolf) {
            return Optional.of(werewolf);
        } else if (entity instanceof Player player) {
            return Optional.of(WerewolfPlayer.get(player));
        } else {
            return Optional.empty();
        }
    }

    public static boolean matchesItem(Ingredient ingredient, ItemStack searchStack) {
        return ingredient.test(searchStack);
    }

    public static MutableComponent joinComponents(String delimiter, MutableComponent... components) {
        MutableComponent comp = components[0];
        for (int i = 1; i < components.length; i++) {
            comp.append(delimiter).append(components[i]);
        }
        return comp;
    }

    public static boolean isSilverItem(ItemStack stack) {
        return stack.getItem() instanceof ISilverItem || stack.is(ModItemTags.SILVER_ITEM);
    }

    public static boolean isNoLiving(LivingEntity entity) {
        return entity.isInvertedHealAndHarm();
    }


    public static boolean isDay(Level level, BlockPos pos) {
        float angle = level.environmentAttributes().getValue(EnvironmentAttributes.SUN_ANGLE, pos) / 360;
        return angle > 0.78 || angle < 0.24;
    }
}
