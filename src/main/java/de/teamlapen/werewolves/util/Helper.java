package de.teamlapen.werewolves.util;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Helper extends de.teamlapen.vampirism.util.Helper {

    public static boolean isWerewolf(Entity entity) {
        return (entity instanceof Player player && isWerewolf(player)) || WReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
    }

    public static boolean isWerewolf(Player entity) {
        return  WReference.WEREWOLF_FACTION.equals(VampirismAPI.factionPlayerHandler(entity).getCurrentFaction());
    }

    public static boolean hasFaction(Entity entity) {
        if (VampirismAPI.factionRegistry().getFaction(entity) != null) {
            return true;
        } else return isWerewolf(entity);
    }

    public static BlockPos multiplyBlockPos(BlockPos pos, double amount) {
        return new BlockPos((int) (pos.getX() * amount), (int) (pos.getY() * amount), (int) (pos.getZ() * amount));
    }

    public static boolean canBecomeWerewolf(Player player) {
        return FactionPlayerHandler.get(player).canJoin(WReference.WEREWOLF_FACTION);
    }

    public static boolean isNight(Level world) {
        long time = world.getDayTime() % 24000;
        return !world.dimensionType().hasFixedTime() && time > 12786 && time < 23216;
    }

    public static boolean isFullMoon(Level world) {
        long time = world.getDayTime() % 192000;
        return !world.dimensionType().hasFixedTime() && time > 12786 && time < 23216;
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
        return canWerewolfEatItem(player, stack) || WerewolfPlayer.getOpt(player).map(w -> w.getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT.get())).orElse(false);
    }

    public static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
        return stack.getFoodProperties(entity) != null && (stack.is(ItemTags.MEAT) || stack.is(ModTags.Items.WEREWOLF_FOOD) || WerewolvesConfig.SERVER.isCustomMeatItems(stack.getItem()));
    }

    @Deprecated
    public static boolean isMeat(ItemStack stack) {
        return isMeat(null, stack);
    }

    public static boolean isRawMeat( @Nullable LivingEntity entity, ItemStack stack) {
        return isMeat(entity, stack) && (stack.is(ModTags.Items.RAW_FOOD) || WerewolvesConfig.SERVER.isCustomRawMeatItems(stack.getItem()));
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
        return stack.is(ModTags.Items.RAW_MEAT);
    }

    public static boolean canEat(LivingEntity entity, ItemStack stack) {
        return Helper.isMeat(entity, stack) || !(entity instanceof Player player) || !Helper.isWerewolf(player) || WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT.get());
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
        return Arrays.stream(ingredient.getItems()).anyMatch(stack -> ItemStack.isSameItem(stack, searchStack) && ItemStack.isSameItemSameComponents(stack, searchStack));
    }

    public static MutableComponent joinComponents(String delimiter, MutableComponent... components) {
        MutableComponent comp = components[0];
        for (int i = 1; i < components.length; i++) {
            comp.append(delimiter).append(components[i]);
        }
        return comp;
    }

    public static boolean isSilverItem(ItemStack stack) {
        return stack.getItem() instanceof ISilverItem || stack.is(ModTags.Items.SILVER_ITEM);
    }

    public static boolean isNoLiving(LivingEntity entity) {
        return entity.isInvertedHealAndHarm() || isVampire(entity);
    }

}
