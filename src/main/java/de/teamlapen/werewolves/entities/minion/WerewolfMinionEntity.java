package de.teamlapen.werewolves.entities.minion;

import com.google.common.collect.Lists;
import de.teamlapen.lib.HelperLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.config.BalanceMobProps;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.minion.MinionEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModMinionTasks;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.items.WerewolfMinionUpgradeItem;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class WerewolfMinionEntity extends MinionEntity<WerewolfMinionEntity.WerewolfMinionData> implements IWerewolf {

    public static AttributeSupplier.Builder getAttributeBuilder() {
        return BasicWerewolfEntity.getAttributeBuilder();
    }

    public WerewolfMinionEntity(EntityType<? extends VampirismEntity> type, Level world) {
        super(type, world, VampirismAPI.factionRegistry().getPredicate(WReference.WEREWOLF_FACTION, true, true, false, false, null).or(e -> !(e instanceof IFactionEntity) && (e instanceof Enemy) && !(e instanceof Creeper)));
    }

    @Override
    public List<IMinionTask<?, ?>> getAvailableTasks() {
        return Lists.newArrayList(ModMinionTasks.V.FOLLOW_LORD.get(), ModMinionTasks.V.DEFEND_AREA.get(), ModMinionTasks.V.STAY.get(), ModMinionTasks.V.PROTECT_LORD.get(), ModMinionTasks.COLLECT_WEREWOLF_ITEMS.get());
    }

    @Override
    public boolean shouldRenderLordSkin() {
        return false;
    }

    @Override
    public void openAppearanceScreen() {
        WerewolvesMod.proxy.displayWerewolfMinionAppearanceScreen(this);
    }

    @Override
    public void openStatsScreen() {
        WerewolvesMod.proxy.displayWerewolfStatsScreen(this);
    }

    @Override
    protected boolean canConsume(@NotNull ItemStack stack) {
        if (!super.canConsume(stack)) return false;
        if (stack.getFoodProperties(this) == null || !Helper.isMeat(this, stack)) return false;
        boolean fullHealth = this.getHealth() == this.getMaxHealth();
        return !fullHealth || stack.getFoodProperties(this) == null;
    }

    @Override
    public @NotNull ItemStack eat(@NotNull Level world, @NotNull ItemStack stack, FoodProperties properties) {
        if (Helper.isRawMeat(this, stack)) {
            @SuppressWarnings("DataFlowIssue")
            float healAmount = properties.nutrition() / 2f;
            this.heal(healAmount);
        }
        return super.eat(world, stack, properties);
    }

    @Override
    protected void onMinionDataReceived(HolderLookup.Provider provider, @NotNull WerewolfMinionData data) {
        super.onMinionDataReceived(provider, data);
        this.updateAttributes();
    }

    @Override
    public @NotNull Predicate<ItemStack> getEquipmentPredicate(EquipmentSlot slotType) {
        return itemStack -> ((itemStack.getItem() instanceof IFactionExclusiveItem) && ((IFactionExclusiveItem) itemStack.getItem()).getExclusiveFaction(itemStack).equals(WReference.WEREWOLF_FACTION)) || itemStack.getUseAnimation() == UseAnim.DRINK || itemStack.getUseAnimation() == UseAnim.EAT;
    }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!this.level().isClientSide() && isLord(player) && minionData != null) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.getItem() instanceof WerewolfMinionUpgradeItem && ((WerewolfMinionUpgradeItem) heldItem.getItem()).getFaction() == this.getFaction()) {
                if (this.minionData.level + 1 >= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMinLevel() && this.minionData.level + 1 <= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMaxLevel()) {
                    this.minionData.level++;
                    if (!player.getAbilities().instabuild) heldItem.shrink(1);
                    player.displayClientMessage(Component.translatable("text.werewolves.werewolf_minion.equipment_upgrade"), false);
                    HelperLib.sync(this);
                } else {
                    player.displayClientMessage(Component.translatable("text.werewolves.werewolf_minion.equipment_wrong"), false);

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

    @Nonnull
    @Override
    public WerewolfForm getForm() {
        return this.minionData != null ? this.minionData.form : WerewolfForm.NONE;
    }

    @Override
    public int getSkinType(WerewolfForm form) {
        return this.minionData != null ? this.minionData.skinType : 0;
    }

    @Override
    public int getEyeType(WerewolfForm form) {
        return this.minionData != null ? this.minionData.eyeType : 0;
    }

    @Override
    public boolean hasGlowingEyes(WerewolfForm form) {
        return this.minionData != null && this.minionData.glowingEyes;
    }

    private void updateAttributes() {
        float statsMultiplier = this.getMinionData().filter(d -> d.hasIncreasedStats).map(a -> 1.2f).orElse(1f);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((BalanceMobProps.mobProps.MINION_MAX_HEALTH + BalanceMobProps.mobProps.MINION_MAX_HEALTH_PL * getMinionData().map((WerewolfMinionData::getHealthLevel)).orElse(0)) * statsMultiplier);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE + BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE_PL * getMinionData().map(WerewolfMinionData::getStrengthLevel).orElse(0)) * statsMultiplier);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(BalanceMobProps.mobProps.VAMPIRE_HUNTER_SPEED * statsMultiplier); //TODO
    }

    public void setEyeType(int type) {
        this.getMinionData().ifPresent(d -> d.eyeType = type);
    }

    public void setSkinType(int type) {
        this.getMinionData().ifPresent(d -> d.skinType = type);
    }

    public void setGlowingEyes(boolean glowing) {
        this.getMinionData().ifPresent(d -> d.glowingEyes = glowing);
    }

    public static class WerewolfMinionData extends MinionData {
        public static final ResourceLocation ID = WResourceLocation.mod("werewolf");

        public static final int MAX_LEVEL = 6;
        public static final int MAX_LEVEL_INVENTORY = 2;
        public static final int MAX_LEVEL_HEALTH = 3;
        public static final int MAX_LEVEL_STRENGTH = 3;
        public static final int MAX_LEVEL_RESOURCES = 2;

        private int level;
        private int inventoryLevel;
        private int healthLevel;
        private int strengthLevel;
        private int resourceEfficiencyLevel;

        private int skinType;
        private int eyeType;
        private boolean glowingEyes;
        private WerewolfForm form = WerewolfForm.BEAST;
        private boolean hasIncreasedStats;

        public WerewolfMinionData(String name, int skinType, int eyeType, boolean glowingEyes, WerewolfForm form, boolean hasIncreasedStats) {
            super(name, 9);
            this.hasIncreasedStats = hasIncreasedStats;
            assert !form.isHumanLike();
            this.level = 0;
            this.skinType = skinType;
            this.eyeType = eyeType;
            this.glowingEyes = glowingEyes;
            this.form = form;
        }

        public WerewolfMinionData() {
            super();
        }

        @Override
        public MutableComponent getFormattedName() {
            return super.getFormattedName().withStyle(style -> style.withColor(WReference.WEREWOLF_FACTION.getChatColor()));
        }

        public int getHealthLevel() {
            return healthLevel;
        }

        public int getInventoryLevel() {
            return inventoryLevel;
        }

        public int getStrengthLevel() {
            return strengthLevel;
        }

        public int getLevel() {
            return level;
        }

        public int getSkinType() {
            return skinType;
        }

        public int getEyeType() {
            return eyeType;
        }

        public boolean hasGlowingEyes() {
            return glowingEyes;
        }

        public int getResourceEfficiencyLevel() {
            return resourceEfficiencyLevel;
        }

        public WerewolfForm getForm() {
            return form;
        }

        public int getRemainingStatPoints() {
            return Math.max(0, this.level - this.inventoryLevel - this.healthLevel - this.strengthLevel - this.resourceEfficiencyLevel);
        }

        @Override
        public boolean hasUsedSkillPoints() {
            return this.inventoryLevel + this.healthLevel + this.strengthLevel + this.resourceEfficiencyLevel > 0;
        }

        @Override
        public void resetStats(@NotNull MinionEntity<?> entity) {
            this.inventoryLevel = 0;
            this.strengthLevel = 0;
            this.healthLevel = 0;
            this.resourceEfficiencyLevel = 0;
            this.shrinkInventory(entity);
            super.resetStats(entity);
        }

        @Override
        public int getInventorySize() {
            int size = this.getDefaultInventorySize();
            return this.inventoryLevel == 1 ? size + 3 : (this.inventoryLevel == 2 ? size + 6 : size);
        }

        @Override
        public void handleMinionAppearanceConfig(String name, List<Integer> data) {
            super.handleMinionAppearanceConfig(name, data);
            this.setName(name);
            this.skinType = data.get(0);
            this.eyeType = data.get(1);
            this.glowingEyes = data.get(2) == 1;
        }

        public boolean setLevel(int level) {
            if (level < 0 || level > MAX_LEVEL) return false;
            boolean levelup = level > this.level;
            this.level = level;
            return levelup;
        }

        @Override
        public boolean upgradeStat(int statId, @NotNull MinionEntity<?> entity) {
            if (super.upgradeStat(statId, entity)) return true;
            if (getRemainingStatPoints() == 0) {
                LOGGER.warn("Cannot upgrade minion stat as no stat points are left");
                return false;
            }
            assert entity instanceof WerewolfMinionEntity;
            switch (statId) {
                case 0 -> {
                    if (inventoryLevel >= MAX_LEVEL_INVENTORY) return false;
                    inventoryLevel++;
                    this.getInventory().setAvailableSize(getInventorySize());
                    return true;
                }
                case 1 -> {
                    if (healthLevel >= MAX_LEVEL_HEALTH) return false;
                    healthLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    entity.setHealth(entity.getMaxHealth());
                    return true;
                }
                case 2 -> {
                    if (strengthLevel >= MAX_LEVEL_STRENGTH) return false;
                    strengthLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    return true;
                }
                case 3 -> {
                    if (resourceEfficiencyLevel >= MAX_LEVEL_RESOURCES) return false;
                    this.resourceEfficiencyLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    return true;
                }
                default -> {
                    LOGGER.warn("Cannot upgrade minion stat {} as it does not exist", statId);
                    return false;
                }
            }
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provide, @NotNull CompoundTag nbt) {
            super.deserializeNBT(provide, nbt);
            this.level = nbt.getInt("level");
            this.inventoryLevel = nbt.getInt("l_inv");
            this.healthLevel = nbt.getInt("l_he");
            this.strengthLevel = nbt.getInt("l_str");
            this.resourceEfficiencyLevel = nbt.getInt("l_res");
            this.skinType = nbt.getInt("s_type");
            this.eyeType = nbt.getInt("e_type");
            this.glowingEyes = nbt.getBoolean("e_glow");
            this.form = WerewolfForm.getForm(nbt.getString("form"));
            this.hasIncreasedStats = nbt.getBoolean("hasIncreasedStats");
        }

        @Override
        public void serializeNBT(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provide) {
            super.serializeNBT(tag, provide);
            tag.putInt("level", this.level);
            tag.putInt("l_inv", this.inventoryLevel);
            tag.putInt("l_he", this.healthLevel);
            tag.putInt("l_str", this.strengthLevel);
            tag.putInt("l_res", resourceEfficiencyLevel);
            tag.putInt("s_type", this.skinType);
            tag.putInt("e_type", this.eyeType);
            tag.putBoolean("e_glow", this.glowingEyes);
            tag.putString("form", this.form.getName());
            tag.putBoolean("hasIncreasedStats", this.hasIncreasedStats);
        }

        @Override
        protected ResourceLocation getDataType() {
            return ID;
        }

        public void setIncreasedStats(boolean hasIncreasedStats) {
            this.hasIncreasedStats = hasIncreasedStats;
        }

        public void setSkinType(int skinType) {
            this.skinType = skinType;
        }

        public void setEyeType(int eyeType) {
            this.eyeType = eyeType;
        }

        public void setGlowingEyes(boolean glowingEyes) {
            this.glowingEyes = glowingEyes;
        }

        public void setForm(WerewolfForm form) {
            this.form = form;
        }
    }
}
