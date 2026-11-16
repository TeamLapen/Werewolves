package de.teamlapen.werewolves.client.gui;

import de.teamlapen.faction.api.world.entities.minion.IMinionData;
import de.teamlapen.faction.api.world.entities.minion.MinionAppearance;
import de.teamlapen.faction.client.gui.screens.AppearanceScreen;
import de.teamlapen.faction.client.gui.screens.ILastScreenProvider;
import de.teamlapen.faction.common.factions.minions.MinionData;
import de.teamlapen.faction.common.network.packets.server.ServerboundCustomizationPacket;
import de.teamlapen.faction.common.world.entities.customization.CustomizationData;
import de.teamlapen.gui.components.DropdownWidget;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.network.chat.Component;

import java.util.stream.IntStream;

public class WerewolfMinionAppearanceScreen extends AppearanceScreen<WerewolfMinionEntity> {

    private static final Component NAME = Component.translatable("gui.vampirism.minion_appearance");

    private int skinCount;
    private int skinType;
    private String minionName = "";
    private final IMinionData data;

    public WerewolfMinionAppearanceScreen(WerewolfMinionEntity entity, ILastScreenProvider backScreen) {
        this.data = entity.getData().orElseThrow();
        super(NAME, entity, backScreen);
    }

    @Override
    protected void init() {
        this.skinCount = 5; //TODO get from resourcepack

        this.minionName = MinionAppearance.NAME_TYPE.currentValue(this.entity, data);
        this.skinType = MinionAppearance.SKIN_TYPE.currentValue(this.entity, data);

        super.init();
    }

    @Override
    protected LayoutElement createLayout() {
        LinearLayout vertical = LinearLayout.vertical()
                .spacing(4);

        var name = new EditBox(this.font,  98, 12, Component.translatable("gui.vampirism.minion_appearance.name"));
        name.setTextColorUneditable(-1);
        name.setTextColor(-1);
        name.insertText(this.minionName);
        name.setMaxLength(MinionData.MAX_NAME_LENGTH);
        name.setResponder(this::onNameChanged);
        vertical.addChild(name);

        vertical.addChild(DropdownWidget.simple(0,0)
                .width(99)
                .itemHeight(20)
                .maxVisibleItems(5)
                .initialSelection(this.skinType)
                .onSelect(this::skin)
                .onHover(this::previewSkin)
                .simpleItems(IntStream.range(0, this.skinCount)
                        .mapToObj(type -> (Component) Component.translatable("gui.vampirism.minion_appearance.skin").append(" " + (type + 1)))
                        .toList())
                .build());

        return vertical;
    }

    @Override
    public void removed() {
        String name = minionName;
        if (name.isEmpty()) {
            name = Component.translatable("gui.vampirism.minion_appearance.minion").getString() + entity.getMinionId().orElse(0);
        }
        WerewolvesMod.proxy.sendToServer(new ServerboundCustomizationPacket(this.entity.getId(), CustomizationData
                .with(MinionAppearance.NAME_TYPE, name)
                .with(MinionAppearance.SKIN_TYPE, this.skinType)));
        super.removed();
    }

    private void onNameChanged(String newName) {
        this.minionName = newName;
        MinionAppearance.NAME_TYPE.setValue(this.entity, this.data, newName);
    }

    private void previewSkin(int type, boolean hovered) {
        if (hovered) {
            MinionAppearance.SKIN_TYPE.setValue(this.entity, this.data, type);
        } else {
            if (MinionAppearance.SKIN_TYPE.currentValue(this.entity, this.data) == type) {
                MinionAppearance.SKIN_TYPE.setValue(this.entity, this.data, this.skinType);
            }
        }
    }

    private void skin(int type) {
        MinionAppearance.SKIN_TYPE.setValue(this.entity, this.data, this.skinType = type);
    }

}
