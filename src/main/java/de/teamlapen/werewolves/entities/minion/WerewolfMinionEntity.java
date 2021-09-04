package de.teamlapen.werewolves.entities.minion;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.BalanceMobProps;
import de.teamlapen.vampirism.entity.VampirismEntity;
import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.vampirism.entity.minion.MinionEntity;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.vampirism.entity.minion.management.MinionTasks;
import de.teamlapen.werewolves.entities.IWerewolf;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class WerewolfMinionEntity extends MinionEntity<WerewolfMinionEntity.WerewolfMinionData> implements IWerewolf {

    static {
        MinionData.registerDataType(WerewolfMinionEntity.WerewolfMinionData.ID, WerewolfMinionEntity.WerewolfMinionData::new);
    }

    public static void init() {

    }

    public static AttributeModifierMap.MutableAttribute getAttributeBuilder() {
        return BasicHunterEntity.getAttributeBuilder();
    }

    public WerewolfMinionEntity(EntityType<? extends VampirismEntity> type, World world) {
        super(type, world, VampirismAPI.factionRegistry().getPredicate(WReference.WEREWOLF_FACTION, true, true, false, false, null).or(e -> !(e instanceof IFactionEntity) && (e instanceof IMob) && !(e instanceof CreeperEntity)));
    }

    @Override
    public List<IMinionTask<?, ?>> getAvailableTasks() {
        return Lists.newArrayList(MinionTasks.follow_lord, MinionTasks.defend_area, MinionTasks.stay, MinionTasks.protect_lord);
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
        super.openAppearanceScreen();
    }

    @Override
    public void openStatsScreen() {
        super.openStatsScreen();
    }

    @Override
    protected boolean canConsume(ItemStack stack) {
        return super.canConsume(stack);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected void onMinionDataReceived(@Nonnull WerewolfMinionData data) {
        super.onMinionDataReceived(data);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        return super.mobInteract(player, hand);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    private void updateAttackGoal() {

    }

    private void updateAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(BalanceMobProps.mobProps.MINION_MAX_HEALTH + BalanceMobProps.mobProps.MINION_MAX_HEALTH_PL * getMinionData().map((WerewolfMinionData::getHealthLevel)).orElse(0));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE + BalanceMobProps.mobProps.MINION_ATTACK_DAMAGE_PL * getMinionData().map(WerewolfMinionData::getStrengthLevel).orElse(0));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(BalanceMobProps.mobProps.VAMPIRE_HUNTER_SPEED); //TODO
    }

    public static class WerewolfMinionData extends MinionData {
        public static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "werewolf");

        public static final int MAX_LEVEL = 6;
        public static final int MAX_LEVEL_INVENTORY = 2;
        public static final int MAX_LEVEL_HEALTH = 3;
        public static final int MAX_LEVEL_STRENGTH = 3;

        private int level;
        private int inventoryLevel;
        private int healthLevel;
        private int strengthLevel;

        public WerewolfMinionData(String name) {
            super(name, 9);
            this.level = 0;
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

        public int getRemainingStatPoints() {
            return Math.max(0, this.level - this.inventoryLevel - this.healthLevel - this.strengthLevel);
        }

        @Override
        public boolean hasUsedSkillPoints() {
            return this.inventoryLevel + this.healthLevel + this.strengthLevel > 0;
        }

        @Override
        public void resetStats(MinionEntity<?> entity) {
            this.inventoryLevel = 0;
            this.strengthLevel = 0;
            this.healthLevel = 0;
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
            super.handleMinionAppearanceConfig(name, data);
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
        }

        @Override
        public void serializeNBT(CompoundNBT tag) {
            super.serializeNBT(tag);
            tag.putInt("level", level);
            tag.putInt("l_inv", inventoryLevel);
            tag.putInt("l_he", healthLevel);
            tag.putInt("l_str", strengthLevel);
        }

        @Override
        protected ResourceLocation getDataType() {
            return ID;
        }
    }
}
