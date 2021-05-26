package de.teamlapen.werewolves.client.gui;

import de.teamlapen.lib.lib.client.gui.widget.ScrollableArrayTextComponentList;
import de.teamlapen.lib.lib.client.gui.widget.ScrollableListWidget;
import de.teamlapen.vampirism.client.gui.AppearanceScreen;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

public class WerewolfPlayerAppearanceScreen extends AppearanceScreen<PlayerEntity> {

    private static final ITextComponent NAME = new TranslationTextComponent("gui.vampirism.appearance");

    private WerewolfPlayer werewolf;

    private WerewolfForm activeForm;
    private int skinType;
    private int eyeType;

    private ScrollableListWidget<Pair<Integer, ITextComponent>> eyeList;
    private ScrollableListWidget<Pair<Integer, ITextComponent>> skinList;
    private ExtendedButton eyeButton;
    private ExtendedButton skinButton;

    public WerewolfPlayerAppearanceScreen(@Nullable Screen backScreen) {
        super(NAME, Minecraft.getInstance().player, backScreen);
        this.werewolf = WerewolfPlayer.get(Minecraft.getInstance().player);
    }

    @Override
    public void onClose() {
        WerewolvesMod.dispatcher.sendToServer(new WerewolfAppearancePacket(this.entity.getEntityId(), "", activeForm, eyeType, skinType));
        super.onClose();
    }

    @Override
    protected void init() {
        super.init();
        switchToForm(WerewolfForm.HUMAN);
    }

    private void switchToForm(WerewolfForm form) {
        this.activeForm = form;
        this.skinType = werewolf.getSkinType(form);
        this.eyeType = werewolf.getEyeType(form);

        this.eyeList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft+20, this.guiTop+30+19, 99, 100, 20, REFERENCE.EYE_TYPE_COUNT,new TranslationTextComponent("gui.vampirism.appearance.eye"),this::eye, this::hoverEye));
        this.skinList = this.addButton(new ScrollableArrayTextComponentList(this.guiLeft+20, this.guiTop+50+19, 99, 80, 20, REFERENCE.SKIN_TYPE_COUNT,new TranslationTextComponent("gui.werewolves.appearance.skin"),this::skin, this::hoverSkin));
        this.eyeButton = this.addButton(new ExtendedButton(eyeList.x, eyeList.y - 20, eyeList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setEyeListVisibility(!eyeList.visible);
        }));
        this.skinButton = this.addButton(new ExtendedButton(skinList.x, skinList.y - 20, skinList.getWidth() + 1, 20, new StringTextComponent(""), (b) -> {
            this.setSkinListVisibility(!skinList.visible);
        }));
        this.setEyeListVisibility(false);
        this.setSkinListVisibility(false);
    }

    private void setEyeListVisibility(boolean show) {
        eyeButton.setMessage(eyeList.getMessage().deepCopy().appendString(" " + (eyeType + 1)));
        this.eyeList.visible = show;
        this.skinButton.visible = !show;
        if (show) this.skinList.visible = false;
    }

    private void setSkinListVisibility(boolean show) {
        skinButton.setMessage(skinList.getMessage().deepCopy().appendString(" " + (skinType + 1)));
        this.skinList.visible = show;
        if (show) this.eyeList.visible = false;
    }

    private void eye(int eyeType) {
        this.werewolf.setEyeType(this.activeForm, eyeType);
        this.setEyeListVisibility(false);
    }

    private void skin(int fangType) {
        this.werewolf.setSkinType(this.activeForm, fangType);
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
