package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.api.util.VIdentifier;
import de.teamlapen.vampirism.client.color.item.QuarrelTint;
import de.teamlapen.vampirism.data.provider.models.ModModelTemplates;
import de.teamlapen.vampirism.data.provider.models.ModTextureSlots;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import static de.teamlapen.werewolves.api.WIdentifier.mod;

public class CompatItemModelGenerator extends ItemModelGenerators {

    public CompatItemModelGenerator(ItemModelGenerators generator) {
        super(generator.itemModelOutput, generator.modelOutput);
    }

    @Override
    public void run() {
        this.diffuserCore(CompatItems.WOLFSBANE_DIFFUSER_CORE.get(), mod("block/wolfsbane_diffuser_inside"));
        this.diffuserCore(CompatItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED.get(), mod("block/wolfsbane_diffuser_improved_inside"));

        var model = ModModelTemplates.TWO_LAYERED_ITEM.create(CompatItems.QUARREL_SILVER.get(), TextureMapping.layered(new Material(VIdentifier.mod("item/quarrel")), new Material(VIdentifier.mod("item/quarrel_tip"))), this.modelOutput);
        this.itemModelOutput.accept(CompatItems.QUARREL_SILVER.get(), ItemModelUtils.tintedModel(model, BLANK_LAYER, new QuarrelTint()));
    }


    protected void diffuserCore(Item item, Identifier texture) {
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModModelTemplates.DIFFUSER_CORE.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(ModTextureSlots.CORE, new Material(texture)), this.modelOutput)));
    }
}
