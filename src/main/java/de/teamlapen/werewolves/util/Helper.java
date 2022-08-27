package de.teamlapen.werewolves.util;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Helper extends de.teamlapen.vampirism.util.Helper {

    public static boolean isWerewolf(Entity entity) {
        return WReference.WEREWOLF_FACTION.equals(VampirismAPI.factionRegistry().getFaction(entity));
    }

    public static boolean isWerewolf(Player entity) {
        return VampirismAPI.getFactionPlayerHandler((entity)).map(h -> WReference.WEREWOLF_FACTION.equals(h.getCurrentFaction())).orElse(false);
    }

    public static boolean hasFaction(Entity entity) {
        if (VampirismAPI.factionRegistry().getFaction(entity) != null) {
            return true;
        } else return isWerewolf(entity);
    }

    public static @NotNull BlockPos multiplyBlockPos(@NotNull BlockPos pos, double amount) {
        return new BlockPos(pos.getX() * amount, pos.getY() * amount, pos.getZ() * amount);
    }

    public static boolean canBecomeWerewolf(@NotNull Player player) {
        return FactionPlayerHandler.getOpt(player).map((v) -> v.canJoin(WReference.WEREWOLF_FACTION)).orElse(false);
    }

    public static boolean isNight(@NotNull Level world) {
        long time = world.getDayTime() % 24000;
        return !world.dimensionType().hasFixedTime() && time > 12786 && time < 23216;
    }

    public static boolean isFullMoon(@NotNull Level world) {
        long time = world.getDayTime() % 192000;
        return !world.dimensionType().hasFixedTime() && time > 12786 && time < 23216;
    }

    public static @NotNull Map<Item, Integer> getMissingItems(@NotNull Container inventory, Item @NotNull [] items, int[] amount) {
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

    public static @NotNull BiteDamageSource causeWerewolfDamage(String cause, Entity entity) {
        return new BiteDamageSource(cause, entity);
    }

    public static @NotNull BiteDamageSource causeWerewolfDamage(Player entity) {
        return causeWerewolfDamage("player", entity);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean canWerewolfEatItem(LivingEntity entity, @NotNull ItemStack stack) {
        return !stack.isEdible() || stack.is(ModTags.Items.COOKEDMEATS) || WerewolvesConfig.SERVER.isCustomMeatItems(stack.getItem()) || stack.is(ModTags.Items.RAWMEATS) || WerewolvesConfig.SERVER.isCustomRawMeatItems(stack.getItem()) || stack.getItem().getFoodProperties(stack, entity).isMeat();
    }

    public static boolean canWerewolfPlayerEatItem(@NotNull Player player, @NotNull ItemStack stack) {
        return canWerewolfEatItem(player, stack) || WerewolfPlayer.getOpt(player).map(w -> w.getSkillHandler().isSkillEnabled(ModSkills.NOT_MEAT.get())).orElse(false);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isRawMeat(@NotNull ItemStack stack) {
        return stack.isEdible() && stack.getItem().getFoodProperties().isMeat() && stack.is(ModTags.Items.RAWMEATS);
    }

    public static IWerewolf asIWerewolf(LivingEntity entity) {
        if (entity instanceof IWerewolf) {
            return ((IWerewolf) entity);
        }
        if (entity instanceof Player) {
            return WerewolfPlayer.get(((Player) entity));
        } else {
            return null;
        }
    }

    public static boolean matchesItem(@NotNull Ingredient ingredient, @NotNull ItemStack searchStack) {
        return Arrays.stream(ingredient.getItems()).anyMatch(stack -> stack.sameItem(searchStack) && stack.areShareTagsEqual(searchStack));
    }

    public static MutableComponent joinComponents(@NotNull String delimiter, MutableComponent @NotNull ... components) {
        MutableComponent comp = components[0];
        for (int i = 1; i < components.length; i++) {
            comp.append(delimiter).append(components[i]);
        }
        return comp;
    }

}
