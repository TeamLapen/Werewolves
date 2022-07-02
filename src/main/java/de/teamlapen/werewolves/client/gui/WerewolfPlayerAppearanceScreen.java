package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableArrayTextComponentList;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableListWidget;
import de.teamlapen.vampirism.client.gui.AppearanceScreen;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

public class WerewolfPlayerAppearanceScreen extends AppearanceScreen<PlayerEntity> {

    private static final ITextComponent NAME = new TranslationTextComponent("gui.vampirism.appearance");

    private final WerewolfPlayer werewolf;

    private WerewolfForm activeForm;
    private int skinType;
    private int eyeType;
    private boolean glowingEyes;

    private boolean renderForm;

    private Button human;
    private Button beast;
    private Button survival;

    private ScrollableListWidget<Pair<Integer, ITextComponent>> eyeList;
    private ScrollableListWidget<Pair<Integer, ITextComponent>> skinList;
    private ExtendedButton eyeButton;
    private ExtendedButton skinButton;
    private CheckboxButton glowingEyesButton;

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
    public void resize(Minecraft minecraft, int width, int height) {
        updateServer();
        WerewolfForm form = this.activeForm;
        super.resize(minecraft, width, height);
        switchToForm(form);
    }

    private void updateServer() {
        WerewolvesMod.dispatcher.sendToServer(new WerewolfAppearancePacket(this.entity.getId(), "", activeForm, eyeType, skinType, glowingEyes?1:0));
    }

    @Override
    protected void init() {
        super.init();
        Button.ITooltip notUnlocked = (button, stack, mouseX, mouseY) -> renderTooltip(stack,new TranslationTextComponent("text.werewolves.not_unlocked"), mouseX, mouseY);
        boolean beastUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.beast_form.get());
        boolean survivalUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.survival_form.get());
        this.human = this.addButton(new Button( this.guiLeft + 5, this.guiTop + 20, 67,20, WerewolfForm.HUMAN.getTextComponent(), (button1)-> switchToForm(WerewolfForm.HUMAN)));
        this.beast = this.addButton(new Button( this.guiLeft + 71, this.guiTop + 20, 40,20, WerewolfForm.BEAST.getTextComponent(), (button1)-> switchToForm(WerewolfForm.BEAST), beastUnlocked? Button.NO_TOOLTIP:notUnlocked));
        this.survival = this.addButton(new Button( this.guiLeft + 111, this.guiTop + 20, 55,20, WerewolfForm.SURVIVALIST.getTextComponent(), (button1)-> switchToForm(WerewolfForm.SURVIVALIST), survivalUnlocked? Button.NO_TOOLTIP:notUnlocked));
        this.switchToForm(WerewolfForm.HUMAN);
    }

    /**
     * disable/enable buttons for the different forms
     */
    private void sync() {
        boolean beastUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.beast_form.get());
        boolean survivalUnlocked = werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.survival_form.get());
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
            ((ScreenModifier) this).removeButton(this.eyeButton);
            ((ScreenModifier) this).removeButton(this.skinButton);
            ((ScreenModifier) this).removeButton(this.eyeList);
            ((ScreenModifier) this).removeButton(this.skinList);
            ((ScreenModifier) this).removeButton(this.glowingEyesButton);
        }
        if (this.activeForm != null) {
            this.updateServer();
        }

        this.activeForm = form;
        this.skinType = werewolf.getSkinType(form);
        this.eyeType = werewolf.getEyeType(form);
        this.glowingEyes = werewolf.hasGlowingEyes(form);

        this.eyeList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 30 + 19 + 20, 99, 100, 20, REFERENCE.EYE_TYPE_COUNT, new TranslationTextComponent("text.werewolves.appearance.eye"), this::eye, this::hoverEye));
        this.skinList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 50 + 19 + 20, 99, 80, 20, form.getSkinTypes(), new TranslationTextComponent("text.werewolves.appearance.skin"), this::skin, this::hoverSkin));
        this.eyeButton = this.addButton(new ExtendedButton(eyeList.x, eyeList.y - 20, eyeList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setEyeListVisibility(!eyeList.visible);
        }));
        this.skinButton = this.addButton(new ExtendedButton(skinList.x, skinList.y - 20, skinList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setSkinListVisibility(!skinList.visible);
        }));
        this.glowingEyesButton = this.addButton(new CheckboxButton(this.guiLeft + 20, this.guiTop + 90, 99, 20, new TranslationTextComponent("gui.vampirism.appearance.glowing_eye"), this.glowingEyes) {
            public void onPress() {
                super.onPress();
                glowingEyes = this.selected();
                werewolf.setGlowingEyes(activeForm, glowingEyes);
            }
        });
        this.setEyeListVisibility(false);
        this.setSkinListVisibility(false);
        sync();
    }

    @Override
    public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks) {
        this.renderForm = true;
        super.render(mStack, mouseX, mouseY, partialTicks);
        this.eyeList.render(mStack, mouseX, mouseY, partialTicks);
        this.skinList.render(mStack, mouseX, mouseY, partialTicks);
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
