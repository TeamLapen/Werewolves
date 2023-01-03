package de.teamlapen.werewolves.client.gui;

import de.teamlapen.lib.lib.client.gui.components.ScrollableArrayTextComponentList;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.client.gui.screens.AppearanceScreen;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.vampirism.network.ServerboundAppearancePacket;
import de.teamlapen.werewolves.client.render.WerewolfMinionRenderer;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

import javax.annotation.Nullable;

public class WerewolfMinionAppearanceScreen extends AppearanceScreen<WerewolfMinionEntity> {

    private static final Component NAME = Component.translatable("gui.vampirism.minion_appearance");

    private int skinType;
    private int eyeType;
    private boolean glowingEyes;
    private ScrollableArrayTextComponentList skinList;
    private ScrollableArrayTextComponentList eyeList;
    private ExtendedButton skinButton;
    private ExtendedButton eyeButton;
    private Checkbox glowingEyeButton;
    private EditBox nameWidget;

    public WerewolfMinionAppearanceScreen(WerewolfMinionEntity entity, @Nullable Screen backScreen) {
        super(NAME, entity, backScreen);
    }

    @Override
    protected void init() {
        super.init();
        this.nameWidget = this.addRenderableWidget(new EditBox(font, this.guiLeft + 21, this.guiTop + 29, 98, 12, Component.translatable("gui.vampirism.minion_appearance.name")));
        this.nameWidget.setValue(entity.getMinionData().map(MinionData::getName).orElse("Minion"));
        this.nameWidget.setTextColorUneditable(-1);
        this.nameWidget.setTextColor(-1);
        this.nameWidget.setMaxLength(MinionData.MAX_NAME_LENGTH);
        this.nameWidget.setResponder(this::onNameChanged);

        int skinCount = ((WerewolfMinionRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(this.entity)).getSkinTextureCount(this.entity.getForm());
        int eyeCount = ((WerewolfMinionRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(this.entity)).getEyeTextureCount(this.entity.getForm());

        this.eyeType = this.entity.getEyeType();
        this.skinType = this.entity.getSkinType();
        this.glowingEyes = this.entity.hasGlowingEyes();

        this.glowingEyeButton = this.addRenderableWidget(new Checkbox(this.guiLeft + 20, this.guiTop + 86, 99, 20, Component.translatable("gui.werewolves.minion_appearance.glowing_eyes"), glowingEyes) {
            @Override
            public void onPress() {
                super.onPress();
                glowingEyes = selected();
                entity.setGlowingEyes(glowingEyes);
            }
        });
        this.skinList = this.addRenderableWidget(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 43 + 19, 99, 80, 20, skinCount, Component.translatable("gui.vampirism.minion_appearance.skin"), this::setSkinType, this::previewSkin));
        this.eyeList = this.addRenderableWidget(new ScrollableArrayTextComponentList(this.guiLeft + 20, this.guiTop + 64 + 19, 99, 60, 20, eyeCount, Component.translatable("gui.vampirism.appearance.eye"), this::setEyeType, this::previewEye));
        this.skinButton = this.addRenderableWidget(new ExtendedButton(this.skinList.getX(), this.skinList.getY() - 20, this.skinList.getWidth() + 1, 20, Component.empty(), (b) -> setSkinListVisibility(!this.skinList.visible)));
        this.eyeButton = this.addRenderableWidget(new ExtendedButton(this.eyeList.getX(), this.eyeList.getY() - 20, this.eyeList.getWidth() + 1, 20, Component.empty(), (b) -> setHatListVisibility(!this.eyeList.visible)));

        this.setSkinListVisibility(false);
        this.setHatListVisibility(false);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.eyeList.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            if (!this.skinList.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
                return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
            }
        }
        return true;
    }

    @Override
    public void removed() {
        String name = this.nameWidget.getValue();
        if (name.isEmpty()) {
            name = Component.translatable("text.vampirism.minion").toString() + this.entity.getMinionId().orElse(0);
        }
        VampirismMod.dispatcher.sendToServer(new ServerboundAppearancePacket(this.entity.getId(), name, this.skinType, this.eyeType, this.glowingEyes ? 1 : 0));
        super.removed();
    }

    private void setEyeType(int type) {
        this.entity.setEyeType(this.eyeType = type);
        this.setHatListVisibility(false);
    }

    private void setSkinType(int type) {
        this.entity.setSkinType(this.skinType = type);
        this.setSkinListVisibility(false);
    }

    private void onNameChanged(String newName) {
        this.entity.changeMinionName(newName);
    }

    private void previewEye(int type, boolean hovered) {
        if (hovered) {
            this.entity.setEyeType(type);
        } else {
            if (this.entity.getEyeType() == type) {
                this.entity.setEyeType(this.eyeType);
            }
        }
    }

    private void previewSkin(int type, boolean hovered) {
        if (hovered) {
            this.entity.setSkinType(type);
        } else {
            if (this.entity.getSkinType() == type) {
                this.entity.setSkinType(this.skinType);
            }
        }
    }

    private void setHatListVisibility(boolean show) {
        this.eyeButton.setMessage(eyeList.getMessage().copy().append(" " + (this.eyeType + 1)));
        this.eyeList.visible = show;
        this.glowingEyeButton.visible = !show;
        if (show) {
            this.skinList.visible = false;
        }
    }

    private void setSkinListVisibility(boolean show) {
        this.skinButton.setMessage(skinList.getMessage().copy().append(" " + (skinType + 1)));
        this.skinList.visible = show;
        this.eyeButton.visible = !show;
        this.glowingEyeButton.visible = !show;
        if (show) {
            this.eyeList.visible = false;
        }
    }
}
