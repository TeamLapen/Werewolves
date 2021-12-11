package de.teamlapen.werewolves.entities.minion;

import com.google.common.collect.Lists;
import de.teamlapen.lib.HelperLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.config.BalanceMobProps;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.vampirism.entity.minion.MinionEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.werewolves.client.gui.WerewolfMinionAppearanceScreen;
import de.teamlapen.werewolves.client.gui.WerewolfMinionStatsScreen;
import de.teamlapen.werewolves.core.ModMinionTasks;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.items.WerewolfMinionUpgradeItem;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class WerewolfMinionEntity extends MinionEntity<WerewolfMinionEntity.WerewolfMinionData> implements IWerewolf {

    public static void registerMinionData() {
        MinionData.registerDataType(WerewolfMinionEntity.WerewolfMinionData.ID, WerewolfMinionEntity.WerewolfMinionData::new);
    }

    public static AttributeModifierMap.MutableAttribute getAttributeBuilder() {
        return BasicHunterEntity.getAttributeBuilder();
    }

    public WerewolfMinionEntity(EntityType<? extends VampirismEntity> type, World world) {
        super(type, world, VampirismAPI.factionRegistry().getPredicate(WReference.WEREWOLF_FACTION, true, true, false, false, null).or(e -> !(e instanceof IFactionEntity) && (e instanceof IMob) && !(e instanceof CreeperEntity)));
    }

    @Override
    public List<IMinionTask<?, ?>> getAvailableTasks() {
        return Lists.newArrayList(ModMinionTasks.V.follow_lord, ModMinionTasks.V.defend_area, ModMinionTasks.V.stay, ModMinionTasks.V.protect_lord, ModMinionTasks.collect_werewolf_items);
    }

    @Override
    public boolean shouldRenderLordSkin() {
        return false;
    }

    @Nonnull
    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    @Override
    public void openAppearanceScreen() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(new WerewolfMinionAppearanceScreen(this, Minecraft.getInstance().screen)));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openStatsScreen() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(new WerewolfMinionStatsScreen(this, Minecraft.getInstance().screen)));
    }

    @Override
    protected boolean canConsume(ItemStack stack) {
        if (!super.canConsume(stack)) return false;
        if (stack.isEdible() && !Helper.canWerewolfEatItem(stack)) return false;
        boolean fullHealth = this.getHealth() == this.getMaxHealth();
        return !fullHealth || !stack.isEdible();
    }

    @Nonnull
    @Override
    public ItemStack eat(@Nonnull World world, @Nonnull ItemStack stack) {
        if (stack.isEdible() && Helper.isRawMeat(stack)) {
            float healAmount = stack.getItem().getFoodProperties().getNutrition() / 2f;
            this.heal(healAmount);
        }
        return super.eat(world, stack);
    }

    @Override
    protected void onMinionDataReceived(@Nonnull WerewolfMinionData data) {
        super.onMinionDataReceived(data);
        this.updateAttributes();
    }

    @Override
    public Predicate<ItemStack> getEquipmentPredicate(EquipmentSlotType slotType) {
        return itemStack -> ((itemStack.getItem() instanceof IFactionExclusiveItem) && ((IFactionExclusiveItem) itemStack.getItem()).getExclusiveFaction().equals(WReference.WEREWOLF_FACTION)) || itemStack.getUseAnimation() == UseAction.DRINK || itemStack.getUseAnimation() == UseAction.EAT;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (!this.level.isClientSide() && isLord(player) && minionData != null) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.getItem() instanceof WerewolfMinionUpgradeItem && ((WerewolfMinionUpgradeItem) heldItem.getItem()).getFaction() == this.getFaction()) {
                if (this.minionData.level + 1 >= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMinLevel() && this.minionData.level + 1 <= ((WerewolfMinionUpgradeItem) heldItem.getItem()).getMaxLevel()) {
                    this.minionData.level++;
                    if (!player.abilities.instabuild) heldItem.shrink(1);
                    player.displayClientMessage(new TranslationTextComponent("text.werewolves.werewolf_minion.equipment_upgrade"), false);
                    HelperLib.sync(this);
                } else {
                    player.displayClientMessage(new TranslationTextComponent("text.werewolves.werewolf_minion.equipment_wrong"), false);

                }
                return ActionResultType.SUCCESS;
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
        return this.minionData != null ? this.minionData.form: WerewolfForm.NONE;
    }

    @Override
    public int getSkinType() {
        return this.minionData != null ? this.minionData.skinType : 0;
    }

    @Override
    public int getEyeType() {
        return this.minionData != null ? this.minionData.eyeType :0;
    }

    @Override
    public boolean hasGlowingEyes() {
        return this.minionData != null && this.minionData.glowingEyes;
    }

    private void updateAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(BalanceMobProps.mobProps.MINION_MAX_HEALTH + BalanceMobProps.mobProps.MINION_MAX_HEALTH_PL * getMinionData().map((WerewolfMinionData::getHealthLevel)).orElse(0));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE + BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE_PL * getMinionData().map(WerewolfMinionData::getStrengthLevel).orElse(0));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(BalanceMobProps.mobProps.VAMPIRE_HUNTER_SPEED); //TODO
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
        public static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "werewolf");

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

        public WerewolfMinionData(String name, int skinType, int eyeType, boolean glowingEyes, WerewolfForm form) {
            super(name, 9);
            assert !form.isHumanLike();
            this.level = 0;
            this.skinType = skinType;
            this.eyeType = eyeType;
            this.glowingEyes = glowingEyes;
            this.form = form;
        }

        private WerewolfMinionData() {
        }

        @Override
        public IFormattableTextComponent getFormattedName() {
            return super.getFormattedName().withStyle(WReference.WEREWOLF_FACTION.getChatColor());
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
        public void resetStats(MinionEntity<?> entity) {
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
            return this.inventoryLevel == 1 ? size + 3: (this.inventoryLevel == 2 ? size + 6:size);
        }

        @Override
        public void handleMinionAppearanceConfig(String name, int... data) {
            this.setName(name);
            this.skinType = data[0];
            this.eyeType = data[1];
            this.glowingEyes = data[2] == 1;
        }

        public boolean setLevel(int level){
            if (level < 0 || level > MAX_LEVEL) return false;
            boolean levelup = level > this.level;
            this.level = level;
            return levelup;
        }

        @Override
        public boolean upgradeStat(int statId, MinionEntity<?> entity) {
            if(super.upgradeStat(statId, entity)) return true;
            if (getRemainingStatPoints() == 0) {
                LOGGER.warn("Cannot upgrade minion stat as no stat points are left");
                return false;
            }
            assert entity instanceof WerewolfMinionEntity;
            switch (statId) {
                case 0:
                    if (inventoryLevel >= MAX_LEVEL_INVENTORY) return false;
                    inventoryLevel++;
                    this.getInventory().setAvailableSize(getInventorySize());
                    return true;
                case 1:
                    if (healthLevel >= MAX_LEVEL_HEALTH) return false;
                    healthLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    entity.setHealth(entity.getMaxHealth());
                    return true;
                case 2:
                    if (strengthLevel >= MAX_LEVEL_STRENGTH) return false;
                    strengthLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    return true;
                case 3:
                    if (resourceEfficiencyLevel >= MAX_LEVEL_RESOURCES) return false;
                    this.resourceEfficiencyLevel++;
                    ((WerewolfMinionEntity) entity).updateAttributes();
                    return true;
                default:
                    LOGGER.warn("Cannot upgrade minion stat {} as it does not exist", statId);
                    return false;
            }
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            super.deserializeNBT(nbt);
            this.level = nbt.getInt("level");
            this.inventoryLevel = nbt.getInt("l_inv");
            this.healthLevel = nbt.getInt("l_he");
            this.strengthLevel = nbt.getInt("l_str");
            this.resourceEfficiencyLevel = nbt.getInt("l_res");
            this.skinType = nbt.getInt("s_type");
            this.eyeType = nbt.getInt("e_type");
            this.glowingEyes = nbt.getBoolean("e_glow");
            this.form = WerewolfForm.getForm(nbt.getString("form"));
        }

        @Override
        public void serializeNBT(CompoundNBT tag) {
            super.serializeNBT(tag);
            tag.putInt("level", this.level);
            tag.putInt("l_inv", this.inventoryLevel);
            tag.putInt("l_he", this.healthLevel);
            tag.putInt("l_str", this.strengthLevel);
            tag.putInt("l_res", resourceEfficiencyLevel);
            tag.putInt("s_type", this.skinType);
            tag.putInt("e_type", this.eyeType);
            tag.putBoolean("e_glow", this.glowingEyes);
            tag.putString("form", this.form.getName());
        }

        @Override
        protected ResourceLocation getDataType() {
            return ID;
        }
    }
}
