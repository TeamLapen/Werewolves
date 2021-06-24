package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableArrayTextComponentList;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableListWidget;
import de.teamlapen.vampirism.client.gui.AppearanceScreen;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
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
    public void onClose() {
        updateServer();
        super.onClose();
    }

    private void updateServer() {
        WerewolvesMod.dispatcher.sendToServer(new WerewolfAppearancePacket(this.entity.getEntityId(), "", activeForm, eyeType, skinType, glowingEyes?1:0));
    }

    @Override
    protected void init() {
        super.init();
        human = this.addButton(new Button( this.guiLeft + 20, this.guiTop + 20, 40,20, WerewolfForm.HUMAN.getTextComponent(), (button1)->{switchToForm(WerewolfForm.HUMAN);}));
        beast = this.addButton(new Button( this.guiLeft + 60, this.guiTop + 20, 40,20, WerewolfForm.BEAST.getTextComponent(), (button1)->{switchToForm(WerewolfForm.BEAST);}));
        survival = this.addButton(new Button( this.guiLeft + 100, this.guiTop + 20, 40,20, WerewolfForm.SURVIVALIST.getTextComponent(), (button1)->{switchToForm(WerewolfForm.SURVIVALIST);}));
        switchToForm(WerewolfForm.HUMAN);
    }

    private void sync() {
        if (this.activeForm == WerewolfForm.BEAST) {
            this.beast.active = false;
            this.human.active = true;
            this.survival.active = true;
            this.glowingEyesButton.active = true;
            this.glowingEyesButton.visible = true;
            this.eyeButton.visible = true;
        } else if (this.activeForm == WerewolfForm.SURVIVALIST) {
            this.beast.active = true;
            this.human.active = true;
            this.survival.active = false;
            this.glowingEyesButton.active = true;
            this.glowingEyesButton.visible = true;
            this.eyeButton.visible = true;
        } else {
            this.beast.active = true;
            this.human.active = false;
            this.survival.active = true;
            this.glowingEyesButton.active = false;
            this.glowingEyesButton.visible = false;
            this.eyeButton.visible = false;
        }
    }

    private void switchToForm(WerewolfForm form) {
        if (this.eyeButton != null) {
            ((ScreenModifier) this).removeButton(this.eyeButton);
            ((ScreenModifier) this).removeButton(this.skinButton);
            ((ScreenModifier) this).removeButton(this.eyeList);
            ((ScreenModifier) this).removeButton(this.skinList);
            ((ScreenModifier) this).removeButton(this.glowingEyesButton);
        }
        if (this.activeForm != null){
            this.updateServer();
        }

        this.activeForm = form;
        this.skinType = werewolf.getSkinType(form);
        this.eyeType = werewolf.getEyeType(form);
        this.glowingEyes = werewolf.getGlowingEyes(form);

        boolean human = form == WerewolfForm.HUMAN;

            this.glowingEyesButton = this.addButton(new CheckboxButton(this.guiLeft + 20, this.guiTop + 90, 99, 20, new TranslationTextComponent("gui.vampirism.appearance.glowing_eye"), this.glowingEyes) {
                public void onPress() {
                    super.onPress();
                    glowingEyes = this.isChecked();
                    werewolf.getGlowingEyes(form);
                }
            });
        this.eyeList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 30 + 19 + 20, 99, 100, 20, REFERENCE.EYE_TYPE_COUNT, new TranslationTextComponent("text.werewolves.appearance.eye"), this::eye, this::hoverEye));
        this.skinList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft+20, this.guiTop+50+19+20, 99, 80, 20, form.getSkinTypes(),new TranslationTextComponent("text.werewolves.appearance.skin"),this::skin, this::hoverSkin));
        this.eyeButton = this.addButton(new ExtendedButton(eyeList.x, eyeList.y - 20, eyeList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setEyeListVisibility(!eyeList.visible);
        }));
        this.skinButton = this.addButton(new ExtendedButton(skinList.x, skinList.y - 20, skinList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setSkinListVisibility(!skinList.visible);
        }));
        this.setEyeListVisibility(false);
        this.setSkinListVisibility(false);
        sync();
    }

    @Override
    public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks) {
        this.renderForm = true;
        super.render(mStack, mouseX, mouseY, partialTicks);
        this.renderForm = false;
    }

    public boolean isRenderForm() {
        return renderForm;
    }

    public WerewolfForm getActiveForm() {
        return this.activeForm;
    }

    private void setEyeListVisibility(boolean show) {
        this.eyeButton.setMessage(eyeList.getMessage().deepCopy().appendString(" " + (eyeType + 1)));
        this.eyeList.visible = show;
        this.skinButton.visible = !show;
        if (show) this.skinList.visible = false;
    }

    private void setSkinListVisibility(boolean show) {
        this.skinButton.setMessage(skinList.getMessage().deepCopy().appendString(" " + (skinType + 1)));
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
//        VampirePlayer.getOpt(this.minecraft.player).ifPresent(vampire -> {
//            if (hovered) {
//                vampire.setEyeType(eyeType);
//            } else {
//                if (vampire.getEyeType() == eyeType) {
//                    vampire.setEyeType(this.eyeType);
//                }
//            }
//        });
    }

    private void hoverSkin(int fangType, boolean hovered) {
//        VampirePlayer.getOpt(this.minecraft.player).ifPresent(vampire -> {
//            if (hovered) {
//                vampire.setFangType(fangType);
//            } else {
//                if (vampire.getFangType() == fangType) {
//                    vampire.setFangType(this.fangType);
//                }
//            }
//        });
    }
}
