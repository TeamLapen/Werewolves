package de.teamlapen.werewolves.client.gui;

import de.teamlapen.faction.client.gui.screens.AppearanceScreen;
import de.teamlapen.faction.client.gui.screens.ILastScreenProvider;
import de.teamlapen.gui.components.DropdownWidget;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.core.ModSkills;
import de.teamlapen.werewolves.common.network.ServerboundWerewolfAppearancePacket;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class WerewolfPlayerAppearanceScreen extends AppearanceScreen<Player> {

    private static final Component NAME = Component.translatable("gui.werewolves.appearance");

    private final WerewolfPlayer werewolf;
    @Nullable
    private final ILastScreenProvider backScreen;

    private final WerewolfForm activeForm;
    private int skinType;
    private int eyeType;
    private boolean glowingEyes;

    private boolean renderForm;

    public WerewolfPlayerAppearanceScreen(@Nullable ILastScreenProvider backScreen) {
        this(backScreen, WerewolfForm.HUMAN);
    }

    @SuppressWarnings("DataFlowIssue")
    public WerewolfPlayerAppearanceScreen(@Nullable ILastScreenProvider backScreen, WerewolfForm form) {
        super(NAME, Minecraft.getInstance().player, backScreen);
        this.backScreen = backScreen;
        this.werewolf = WerewolfPlayer.get(Minecraft.getInstance().player);
        this.activeForm = form;
    }

    @Override
    public void removed() {
        updateServer();
        super.removed();
    }

    private void updateServer() {
        WerewolvesMod.proxy.sendToServer(new ServerboundWerewolfAppearancePacket(this.entity.getId(), "", this.activeForm, this.eyeType, this.skinType, this.glowingEyes ? 1 : 0));
    }

    @Override
    protected void init() {
        this.skinType = this.werewolf.getSkinType(this.activeForm);
        this.eyeType = this.werewolf.getEyeType(this.activeForm);
        this.glowingEyes = this.werewolf.hasGlowingEyes(this.activeForm);
        super.init();
    }

    @Override
    protected @NotNull LayoutElement createLayout() {
        LinearLayout vertical = LinearLayout.vertical().spacing(4);

        Tooltip notUnlocked = Tooltip.create(Component.translatable("text.werewolves.not_unlocked"));
        boolean beastUnlocked = this.werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_FORM);
        boolean survivalUnlocked = this.werewolf.getSkillHandler().isSkillEnabled(ModSkills.SURVIVAL_FORM);

        LinearLayout forms = vertical.addChild(LinearLayout.horizontal());
        Button human = forms.addChild(new ExtendedButton(0, 0, 40, 20, WerewolfForm.HUMAN.getTextComponent(), (button) -> switchToForm(WerewolfForm.HUMAN)));
        human.active = this.activeForm != WerewolfForm.HUMAN;
        Button beast = forms.addChild(new ExtendedButton(0, 0, 40, 20, WerewolfForm.BEAST.getTextComponent(), (button) -> switchToForm(WerewolfForm.BEAST)));
        beast.active = beastUnlocked && this.activeForm != WerewolfForm.BEAST;
        if (!beastUnlocked) {
            beast.setTooltip(notUnlocked);
        }
        Button survival = forms.addChild(new ExtendedButton(0, 0, 55, 20, WerewolfForm.SURVIVALIST.getTextComponent(), (button) -> switchToForm(WerewolfForm.SURVIVALIST)));
        survival.active = survivalUnlocked && this.activeForm != WerewolfForm.SURVIVALIST;
        if (!survivalUnlocked) {
            survival.setTooltip(notUnlocked);
        }

        vertical.addChild(DropdownWidget.simple(0, 0)
                .width(99)
                .itemHeight(20)
                .maxVisibleItems(5)
                .initialSelection(this.skinType)
                .onSelect(this::skin)
                .onHover(this::hoverSkin)
                .simpleItems(IntStream.range(0, this.activeForm.getSkinTypes()).mapToObj(type -> (Component) Component.translatable("text.werewolves.appearance.skin").append(" " + (type + 1))).toList())
                .build());

        if (this.activeForm != WerewolfForm.HUMAN) {
            vertical.addChild(DropdownWidget.simple(0, 0)
                    .width(99)
                    .itemHeight(20)
                    .maxVisibleItems(5)
                    .initialSelection(this.eyeType)
                    .onSelect(this::eye)
                    .onHover(this::hoverEye)
                    .simpleItems(IntStream.range(0, REFERENCE.EYE_TYPE_COUNT).mapToObj(type -> (Component) Component.translatable("text.werewolves.appearance.eye").append(" " + (type + 1))).toList())
                    .build());

            vertical.addChild(Checkbox.builder(Component.translatable("gui.werewolves.appearance.glowing_eyes"), this.font).selected(this.glowingEyes).onValueChange((button, selected) -> {
                this.glowingEyes = selected;
                this.werewolf.setGlowingEyes(this.activeForm, selected);
            }).build());
        }

        return vertical;
    }

    /**
     * Reopens the screen for the given form. The changes of the current form are sent to the server in {@link #removed()}
     */
    private void switchToForm(WerewolfForm form) {
        if (form == this.activeForm) return;
        Minecraft.getInstance().setScreen(new WerewolfPlayerAppearanceScreen(this.backScreen, form));
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderForm = true;
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
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

    private void eye(int eyeType) {
        this.eyeType = eyeType;
        this.werewolf.setEyeType(this.activeForm, eyeType);
    }

    private void skin(int skinType) {
        this.skinType = skinType;
        this.werewolf.setSkinType(this.activeForm, skinType);
    }

    private void hoverEye(int eyeType, boolean hovered) {
        if (hovered) {
            this.werewolf.setEyeType(this.activeForm, eyeType);
        } else {
            if (this.werewolf.getEyeType(this.activeForm) == eyeType) {
                this.werewolf.setEyeType(this.activeForm, this.eyeType);
            }
        }
    }

    private void hoverSkin(int skinType, boolean hovered) {
        if (hovered) {
            this.werewolf.setSkinType(this.activeForm, skinType);
        } else {
            if (this.werewolf.getSkinType(this.activeForm) == skinType) {
                this.werewolf.setSkinType(this.activeForm, this.skinType);
            }
        }
    }
}
