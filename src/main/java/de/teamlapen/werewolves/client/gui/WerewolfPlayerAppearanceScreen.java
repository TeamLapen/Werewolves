package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableArrayTextComponentList;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableListWidget;
import de.teamlapen.vampirism.client.gui.AppearanceScreen;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.mixin.client.ScreenAccessor;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WerewolfPlayerAppearanceScreen extends AppearanceScreen<Player> {

    private static final Component NAME = new TranslatableComponent("gui.vampirism.appearance");

    private final WerewolfPlayer werewolf;

    private WerewolfForm activeForm;
    private int skinType;
    private int eyeType;
    private boolean glowingEyes;

    private boolean renderForm;

    private Button human;
    private Button beast;
    private Button survival;

    private ScrollableListWidget<Pair<Integer, Component>> eyeList;
    private ScrollableListWidget<Pair<Integer, Component>> skinList;
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
        WerewolvesMod.dispatcher.sendToServer(new WerewolfAppearancePacket(this.entity.getId(), "", activeForm, eyeType, skinType, glowingEyes ? 1 : 0));
    }

    @Override
    protected void init() {
        super.init();
        Button.OnTooltip notUnlocked = (button, stack, mouseX, mouseY) -> renderTooltip(stack, new TranslatableComponent("text.werewolves.not_unlocked"), mouseX, mouseY);
        boolean beastUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.beast_form);
        boolean survivalUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.survival_form);
        this.human = this.addRenderableWidget(new Button(this.guiLeft + 5, this.guiTop + 20, 67, 20, WerewolfForm.HUMAN.getTextComponent(), (button1) -> switchToForm(WerewolfForm.HUMAN)));
        this.beast = this.addRenderableWidget(new Button(this.guiLeft + 71, this.guiTop + 20, 40, 20, WerewolfForm.BEAST.getTextComponent(), (button1) -> switchToForm(WerewolfForm.BEAST), beastUnlocked ? Button.NO_TOOLTIP : notUnlocked));
        this.survival = this.addRenderableWidget(new Button(this.guiLeft + 111, this.guiTop + 20, 55, 20, WerewolfForm.SURVIVALIST.getTextComponent(), (button1) -> switchToForm(WerewolfForm.SURVIVALIST), survivalUnlocked ? Button.NO_TOOLTIP : notUnlocked));
        this.switchToForm(WerewolfForm.HUMAN);
    }

    /**
     * disable/enable buttons for the different forms
     */
    private void sync() {
        boolean beastUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.beast_form);
        boolean survivalUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.survival_form);
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
            ((ScreenAccessor) this).removeWidget_werewolves(this.eyeButton);
            ((ScreenAccessor) this).removeWidget_werewolves(this.skinButton);
            ((ScreenAccessor) this).removeWidget_werewolves(this.eyeList);
            ((ScreenAccessor) this).removeWidget_werewolves(this.skinList);
            ((ScreenAccessor) this).removeWidget_werewolves(this.glowingEyesButton);
        }
        if (this.activeForm != null) {
            this.updateServer();
        }

        this.activeForm = form;
        this.skinType = werewolf.getSkinType(form);
        this.eyeType = werewolf.getEyeType(form);
        this.glowingEyes = werewolf.hasGlowingEyes(form);

        this.eyeList = this.addRenderableWidget(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 30 + 19 + 20, 99, 100, 20, REFERENCE.EYE_TYPE_COUNT, new TranslatableComponent("text.werewolves.appearance.eye"), this::eye, this::hoverEye));
        this.skinList = this.addRenderableWidget(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 50 + 19 + 20, 99, 80, 20, form.getSkinTypes(), new TranslatableComponent("text.werewolves.appearance.skin"), this::skin, this::hoverSkin));
        this.eyeButton = this.addRenderableWidget(new ExtendedButton(eyeList.x, eyeList.y - 20, eyeList.getWidth() + 1, 20, new TextComponent(""), (b) -> {
            this.setEyeListVisibility(!eyeList.visible);
        }));
        this.skinButton = this.addRenderableWidget(new ExtendedButton(skinList.x, skinList.y - 20, skinList.getWidth() + 1, 20, new TextComponent(""), (b) -> {
            this.setSkinListVisibility(!skinList.visible);
        }));
        this.glowingEyesButton = this.addRenderableWidget(new Checkbox(this.guiLeft + 20, this.guiTop + 90, 99, 20, new TranslatableComponent("gui.vampirism.appearance.glowing_eye"), this.glowingEyes) {
            public void onPress() {
                super.onPress();
                glowingEyes = this.selected();
                werewolf.setGlowingEyes(form, glowingEyes);
            }
        });
        this.setEyeListVisibility(false);
        this.setSkinListVisibility(false);
        sync();
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderForm = true;
        super.render(stack, mouseX, mouseY, partialTicks);
        this.eyeList.render(stack, mouseX, mouseY, partialTicks);
        this.skinList.render(stack, mouseX, mouseY, partialTicks);
        this.renderForm = false;
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
        this.eyeButton.setMessage(eyeList.getMessage().copy().append(" " + (eyeType + 1)));
        this.eyeList.visible = show;
        this.skinButton.visible = !show;
        if (show) this.skinList.visible = false;
    }

    private void setSkinListVisibility(boolean show) {
        this.skinButton.setMessage(skinList.getMessage().copy().append(" " + (skinType + 1)));
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
