package de.teamlapen.werewolves.client.gui;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.inventory.container.StoneAltarContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StoneAltarScreen extends AbstractContainerScreen<StoneAltarContainer> {
    private static final Identifier EMPTY_SLOT_LIVER = WIdentifier.mod("container/slot/liver");
    private static final Identifier EMPTY_SLOT_CRACKED_BONE = WIdentifier.mod("container/slot/cracked_bone");
    private static final Identifier BACKGROUND_LOCATION = WIdentifier.mod("textures/gui/stone_altar.png");

    private final CyclingSlotBackground liverIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground crackedBoneIcon = new CyclingSlotBackground(1);

    public StoneAltarScreen(StoneAltarContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new ExpBar(leftPos - 14, topPos));
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        var requirementsOpt = this.menu.getRequirements();
        this.liverIcon.tick(requirementsOpt.filter(requirements -> requirements.liverAmount > 0).map(requirements -> List.of(EMPTY_SLOT_LIVER)).orElse(List.of()));
        this.crackedBoneIcon.tick(requirementsOpt.filter(requirements -> requirements.bonesAmount > 0).map(requirements -> List.of(EMPTY_SLOT_CRACKED_BONE)).orElse(List.of()));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_LOCATION, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        this.liverIcon.extractRenderState(this.menu, graphics, a, this.leftPos, this.topPos);
        this.crackedBoneIcon.extractRenderState(this.menu, graphics, a, this.leftPos, this.topPos);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.hoveredSlot != null && this.hoveredSlot.index >= 0 && this.hoveredSlot.index < 2) {
            var requirementsOpt = this.menu.getRequirements();
            if (requirementsOpt.isPresent()) {
                var requirements = requirementsOpt.get();
                int slot = this.hoveredSlot.index;
                ItemStack stack = this.hoveredSlot.getItem();

                int requiredCount = slot == 0 ? requirements.liverAmount : requirements.bonesAmount;
                String requiredId = slot == 0 ? "liver" : "cracked_bone";

                if (requiredCount > 0 && stack.getCount() < requiredCount) {
                    Component tooltip = Component.translatable("gui.werewolves.stone_altar.ritual_missing_" + requiredId, requiredCount - stack.getCount());
                    ClientTooltipComponent clientTooltip = ClientTooltipComponent.create(tooltip.getVisualOrderText());
                    graphics.tooltip(this.font, List.of(clientTooltip), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, stack.get(DataComponents.TOOLTIP_STYLE));
                    return;
                }
            }
        }

        super.extractTooltip(graphics, mouseX, mouseY);
    }
}
