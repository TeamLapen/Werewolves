package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.client.gui.components.SimpleButtonScrollWidget;
import de.teamlapen.vampirism.client.gui.screens.AppearanceScreen;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.client.gui.ScreenAccessor;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.ServerboundWerewolfAppearancePacket;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class WerewolfPlayerAppearanceScreen extends AppearanceScreen<Player> {

    private static final Component NAME = Component.translatable("gui.vampirism.appearance");

    private final WerewolfPlayer werewolf;

    private WerewolfForm activeForm;
    private int skinType;
    private int eyeType;
    private boolean glowingEyes;

    private boolean renderForm;

    private Button human;
    private Button beast;
    private Button survival;

    private SimpleButtonScrollWidget eyeList;
    private SimpleButtonScrollWidget skinList;
    private ExtendedButton eyeButton;
    private ExtendedButton skinButton;
    private Checkbox glowingEyesButton;

    public WerewolfPlayerAppearanceScreen(@Nullable Screen backScreen) {
        super(NAME, Minecraft.getInstance().player, backScreen);
        this.werewolf = WerewolfPlayer.get(Minecraft.getInstance().player);
    }

    @Override
    public void removed() {
        updateServer();
        super.removed();
    }

    @Override
    public void resize(@Nonnull Minecraft minecraft, int width, int height) {
        updateServer();
        WerewolfForm form = this.activeForm;
        super.resize(minecraft, width, height);
        switchToForm(form);
    }

    private void updateServer() {
        WerewolvesMod.dispatcher.sendToServer(new ServerboundWerewolfAppearancePacket(this.entity.getId(), "", activeForm, eyeType, skinType, glowingEyes ? 1 : 0));
    }

    @Override
    protected void init() {
        super.init();
        Tooltip notUnlocked = Tooltip.create(Component.translatable("text.werewolves.not_unlocked"));
        this.human = this.addRenderableWidget(new ExtendedButton(this.guiLeft + 5, this.guiTop + 20, 67, 20, WerewolfForm.HUMAN.getTextComponent(), (button1) -> switchToForm(WerewolfForm.HUMAN)));
        this.beast = this.addRenderableWidget(new ExtendedButton(this.guiLeft + 71, this.guiTop + 20, 40, 20, WerewolfForm.BEAST.getTextComponent(), (button1) -> switchToForm(WerewolfForm.BEAST)));
        this.survival = this.addRenderableWidget(new ExtendedButton(this.guiLeft + 111, this.guiTop + 20, 55, 20, WerewolfForm.SURVIVALIST.getTextComponent(), (button1) -> switchToForm(WerewolfForm.SURVIVALIST)));
        this.switchToForm(WerewolfForm.HUMAN);
        if (!werewolf.getSkillHandler().isSkillEnabled(ModSkills.SURVIVAL_FORM.get())) {
            this.survival.setTooltip(notUnlocked);
        }
        if (!werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_FORM.get())) {
            this.beast.setTooltip(notUnlocked);
        }
    }

    /**
     * disable/enable buttons for the different forms
     */
    private void sync() {
        boolean beastUnlocked = werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_FORM.get());
        boolean survivalUnlocked = werewolf.getSkillHandler().isSkillEnabled(ModSkills.SURVIVAL_FORM.get());
        if (this.activeForm == WerewolfForm.BEAST) {
            this.beast.active = false;
            this.human.active = true;
            this.survival.active = survivalUnlocked;
            this.glowingEyesButton.active = true;
            this.glowingEyesButton.visible = true;
            this.eyeButton.visible = true;
        } else if (this.activeForm == WerewolfForm.SURVIVALIST) {
            this.beast.active = beastUnlocked;
            this.human.active = true;
            this.survival.active = false;
            this.glowingEyesButton.active = true;
            this.glowingEyesButton.visible = true;
            this.eyeButton.visible = true;
        } else {
            this.beast.active = beastUnlocked;
            this.human.active = false;
            this.survival.active = survivalUnlocked;
            this.glowingEyesButton.active = false;
            this.glowingEyesButton.visible = false;
            this.eyeButton.visible = false;
        }
    }

    /**
     * setup buttons for the different forms
     */
    private void switchToForm(WerewolfForm form) {
        if (this.eyeButton != null) {
            ((ScreenAccessor) this).invokeRemoveWidget(this.eyeButton);
            ((ScreenAccessor) this).invokeRemoveWidget(this.skinButton);
            ((ScreenAccessor) this).invokeRemoveWidget(this.eyeList);
            ((ScreenAccessor) this).invokeRemoveWidget(this.skinList);
            ((ScreenAccessor) this).invokeRemoveWidget(this.glowingEyesButton);
        }
        if (this.activeForm != null) {
            this.updateServer();
        }

        this.activeForm = form;
        this.skinType = werewolf.getSkinType(form);
        this.eyeType = werewolf.getEyeType(form);
        this.glowingEyes = werewolf.hasGlowingEyes(form);
        this.glowingEyesButton = this.addRenderableWidget(new Checkbox(this.guiLeft + 20, this.guiTop + 90, 99, 20, Component.translatable("gui.vampirism.appearance.glowing_eye"), this.glowingEyes) {
            public void onPress() {
                super.onPress();
                glowingEyes = this.selected();
                werewolf.setGlowingEyes(form, glowingEyes);
            }
        });
        this.eyeList = this.addRenderableWidget(SimpleButtonScrollWidget.builder(this.guiLeft + 20, this.guiTop + 30 + 19 + 20, 99, 100).setComponents(REFERENCE.EYE_TYPE_COUNT, (type) -> {
            return Component.translatable("text.werewolves.appearance.eye").append(" " + type);
        }).setButtonClickConsumer(this::eye).setButtonHoverConsumer(this::hoverEye).build());
        this.skinList = this.addRenderableWidget(SimpleButtonScrollWidget.builder(this.guiLeft + 20, this.guiTop + 50 + 19 + 20, 99, 80).setComponents(form.getSkinTypes(), (type) -> {
            return Component.translatable("text.werewolves.appearance.skin").append(" " + type);
        }).setButtonClickConsumer(this::skin).setButtonHoverConsumer(this::hoverSkin).build());
        this.eyeButton = this.addRenderableWidget(new ExtendedButton(eyeList.getX(), eyeList.getY() - 20, eyeList.getWidth(), 20, Component.empty(), (b) -> {
            this.setEyeListVisibility(!eyeList.visible);
        }));
        this.skinButton = this.addRenderableWidget(new ExtendedButton(skinList.getX(), skinList.getY() - 20, skinList.getWidth(), 20, Component.empty(), (b) -> {
            this.setSkinListVisibility(!skinList.visible);
        }));
        this.setEyeListVisibility(false);
        this.setSkinListVisibility(false);
        sync();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        List<? extends GuiEventListener> copyOf = List.copyOf(this.children());
        for (int i = copyOf.size() -1 ; i >= 0; i--) {
            GuiEventListener listener = copyOf.get(i);
            if (listener.mouseClicked(pMouseX, pMouseY, pButton)) {
                this.setFocused(listener);
                if (pButton == 0) {
                    this.setDragging(true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double pMouseX, double pMouseY) {
        List<? extends GuiEventListener> children = this.children();
        for (int i = children.size() - 1; i >= 0; i--) {
            GuiEventListener guieventlistener = children.get(i);
            if (guieventlistener.isMouseOver(pMouseX, pMouseY)) {
                return Optional.of(guieventlistener);
            }
        }

        return Optional.empty();
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderForm = true;
        super.render(stack, mouseX, mouseY, partialTicks);
        this.eyeList.render(stack, mouseX, mouseY, partialTicks);
        this.skinList.render(stack, mouseX, mouseY, partialTicks);
        this.renderForm = false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (!this.eyeList.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
            if (!this.skinList.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
                return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
            }
        }
        return true;
    }

    /**
     * used by werewolf renderer
     */
    public boolean isRenderForm() {
        return renderForm;
    }

    public WerewolfForm getActiveForm() {
        return this.activeForm;
    }

    private void setEyeListVisibility(boolean show) {
        this.eyeButton.setMessage(Component.translatable("text.werewolves.appearance.eye").append(" " + this.eyeType));
        this.eyeList.visible = show;
        this.skinButton.visible = !show;
        if (show) this.skinList.visible = false;
    }

    private void setSkinListVisibility(boolean show) {
        this.skinButton.setMessage(Component.translatable("text.werewolves.appearance.skin").append(" " + this.skinType));
        this.skinList.visible = show;
        if (show) this.eyeList.visible = false;
    }

    private void eye(int eyeType) {
        this.eyeType = eyeType;
        this.werewolf.setEyeType(this.activeForm, eyeType);
        this.setEyeListVisibility(false);
    }

    private void skin(int skinType) {
        this.skinType = skinType;
        this.werewolf.setSkinType(this.activeForm, skinType);
        this.setSkinListVisibility(false);
    }

    private void hoverEye(int eyeType, boolean hovered) {
        if (hovered) {
            werewolf.setEyeType(this.activeForm, eyeType);
        } else {
            if (werewolf.getEyeType() == eyeType) {
                werewolf.setEyeType(this.activeForm, this.eyeType);
            }
        }
    }

    private void hoverSkin(int skintype, boolean hovered) {
        if (hovered) {
            werewolf.setSkinType(this.activeForm, skintype);
        } else {
            if (werewolf.getSkinType() == skintype) {
                werewolf.setSkinType(this.activeForm, this.skinType);
            }
        }
    }
}
