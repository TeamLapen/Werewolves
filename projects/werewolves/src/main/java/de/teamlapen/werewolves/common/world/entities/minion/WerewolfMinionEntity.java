package de.teamlapen.werewolves.common.world.entities.minion;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import de.teamlapen.faction.api.FactionDataComponents;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.IFactionEntity;
import de.teamlapen.faction.api.factions.IFactionPredicate;
import de.teamlapen.faction.api.world.entities.minion.MinionAppearance;
import de.teamlapen.faction.common.components.FactionRestriction;
import de.teamlapen.faction.common.factions.minions.MinionData;
import de.teamlapen.faction.common.factions.minions.MinionEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.core.ModDataComponents;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.core.ModTags;
import de.teamlapen.werewolves.common.server.command.arguments.WerewolfFormArgument;
import de.teamlapen.werewolves.common.world.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.common.world.items.WerewolfMinionUpgradeItem;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class WerewolfMinionEntity extends MinionEntity implements IWerewolf {

    public static final MinionAppearance<Integer> EYE_TYPE = new MinionAppearance<>(ModDataComponents.MINION_EYES, 0, "eye", IntegerArgumentType.integer(0));
    public static final MinionAppearance<WerewolfForm> FORM_TYPE = new MinionAppearance<>(ModDataComponents.WEREWOLF_FORM, WerewolfForm.BEAST, "form", WerewolfFormArgument.nonHumanForms());
    public static final MinionAppearance<Boolean> GLOWING_EYES_TYPE = new MinionAppearance<>(ModDataComponents.GLOWING_EYES, false, "glowingEyes", BoolArgumentType.bool());

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return BasicWerewolfEntity.getAttributeBuilder();
    }

    public WerewolfMinionEntity(EntityType<? extends MinionEntity> type, Level world) {
        super(type, world, IFactionPredicate.defaultTargets(ModFactions.WEREWOLF).targetFaction(ModTags.Factions.WEREWOLF_MINION_TARGETS).build().or(e -> !(e instanceof IFactionEntity) && (e instanceof Enemy) && !(e instanceof Creeper)));
    }

    @Override
    public MinionData createData() {
        return new MinionData(ModFactions.WEREWOLF_MINION.get());
    }

    @Override
    public void openAppearanceScreen(Runnable back) {
        WerewolvesMod.proxy.displayWerewolfMinionAppearanceScreen(this, back);
    }

    @Override
    public void openStatsScreen(Runnable back) {
        WerewolvesMod.proxy.displayWerewolfStatsScreen(this, back);
    }

    @Override
    protected boolean canConsume(ItemStack stack, Consumable consumable) {
        if (!super.canConsume(stack, consumable)) return false;
        @Nullable
        FoodProperties foodProperties = stack.get(DataComponents.FOOD);
        if (foodProperties == null || !Helper.isMeat(this, stack)) return false;
        boolean fullHealth = this.getHealth() == this.getMaxHealth();
        return !fullHealth || foodProperties == null;
    }

    @Override
    public void eat(Level world, ItemStack stack, FoodProperties properties) {
        if (Helper.isRawMeat(this, stack)) {
            float healAmount = properties.nutrition() / 2f;
            this.heal(healAmount);
        }
        super.eat(world, stack, properties);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide() && isLord(player) && minionData != null) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.getItem() instanceof WerewolfMinionUpgradeItem && IFaction.is(((WerewolfMinionUpgradeItem) heldItem.getItem()).getFaction(), this.getFaction())) {
                if (this.minionData.getLevel() + 1 >= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMinLevel() && this.minionData.getLevel() + 1 <= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMaxLevel()) {
                    if (this.minionData.setLevel(this.minionData.getLevel() + 1) && !player.getAbilities().instabuild) heldItem.shrink(1);
                    if (!player.getAbilities().instabuild) heldItem.shrink(1);
                    player.sendOverlayMessage(Component.translatable("text.werewolves.werewolf_minion.equipment_upgrade"));
                    sync();
                } else {
                    player.sendOverlayMessage(Component.translatable("text.werewolves.werewolf_minion.equipment_wrong"));

                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
    }

    @Override
    public WerewolfForm getForm() {
        return FORM_TYPE.currentValue(this, minionData);
    }

    @Override
    public int getSkinType(@Nullable WerewolfForm form) {
        return MinionAppearance.SKIN_TYPE.currentValue(this, minionData);
    }

    @Override
    public int getEyeType(@Nullable WerewolfForm form) {
        return EYE_TYPE.currentValue(this, minionData);
    }

    @Override
    public void setSkinType(@Nullable WerewolfForm form, int skinType) {
        MinionAppearance.SKIN_TYPE.setValue(this, minionData, skinType);
    }

    @Override
    public void setEyeType(@Nullable WerewolfForm form, int eyeType) {
        EYE_TYPE.setValue(this, minionData, eyeType);
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return GLOWING_EYES_TYPE.currentValue(this, minionData);
    }
}
