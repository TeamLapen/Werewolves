package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.vampirism.client.gui.MinionStatsScreen;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nullable;

public class WerewolfMinionStatsScreen extends MinionStatsScreen<WerewolfMinionEntity.WerewolfMinionData, WerewolfMinionEntity> {

    private final MutableComponent inventoryLevel = Component.translatable("text.vampirism.minion.stats.inventory_level");
    private final MutableComponent healthLevel = Component.translatable(Attributes.MAX_HEALTH.getDescriptionId());
    private final MutableComponent strengthLevel = Component.translatable(Attributes.ATTACK_DAMAGE.getDescriptionId());
    private final MutableComponent resourceLevel = Component.translatable("text.vampirism.minion.stats.resource_level");

    public WerewolfMinionStatsScreen(WerewolfMinionEntity entity, @Nullable Screen backScreen) {
        super(entity, 4, backScreen);
    }

    @Override
    protected boolean areButtonsVisible(WerewolfMinionEntity.WerewolfMinionData data) {
        return data.getRemainingStatPoints() > 0 || data.getLevel() < WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL;
    }

    @Override
    protected int getRemainingStatPoints(WerewolfMinionEntity.WerewolfMinionData data) {
        return data.getRemainingStatPoints();
    }

    @Override
    protected boolean isActive(WerewolfMinionEntity.WerewolfMinionData data, int i) {
        return switch (i) {
            case 0 -> data.getRemainingStatPoints() > 0 && data.getInventoryLevel() < WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_INVENTORY;
            case 1 -> data.getRemainingStatPoints() > 0 && data.getHealthLevel() < WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_HEALTH;
            case 2 -> data.getRemainingStatPoints() > 0 && data.getStrengthLevel() < WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_STRENGTH;
            case 3 -> data.getRemainingStatPoints() > 0 && data.getResourceEfficiencyLevel() < WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES;
            default -> false;
        };
    }

    @Override
    protected void renderStats(PoseStack mStack, WerewolfMinionEntity.WerewolfMinionData data) {
        renderLevelRow(mStack, data.getLevel() + 1, WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL + 1);
        renderStatRow(mStack, 0, this.inventoryLevel, Component.literal(String.valueOf(data.getInventorySize())), data.getInventoryLevel() + 1, WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_INVENTORY + 1);
        renderStatRow(mStack, 1, this.healthLevel, Component.literal(String.valueOf(entity.getAttribute(Attributes.MAX_HEALTH).getBaseValue())), data.getHealthLevel() + 1, WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_HEALTH + 1);
        renderStatRow(mStack, 2, this.strengthLevel, Component.literal(String.valueOf(entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue())), data.getStrengthLevel() + 1, WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_STRENGTH + 1);
        renderStatRow(mStack, 3, this.resourceLevel, Component.literal((int) (Math.ceil((float) (data.getResourceEfficiencyLevel() + 1) / (WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES + 1) * 100)) + "%"), data.getResourceEfficiencyLevel() + 1, WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES + 1);

    }
}
