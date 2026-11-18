package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.data.provider.models.ModModelTemplates;
import de.teamlapen.vampirism.data.provider.models.ModTextureSlots;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

import static de.teamlapen.werewolves.api.WIdentifier.mod;

public class CompatItemModelGenerator extends ItemModelGenerators {

    public CompatItemModelGenerator(ItemModelGenerators generator) {
        super(generator.itemModelOutput, generator.modelOutput);
    }

    @Override
    public void run() {
        this.diffuserCore(ModItems.WOLFSBANE_DIFFUSER_CORE.get(), mod("block/wolfsbane_diffuser_inside"));
        this.diffuserCore(ModItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED.get(), mod("block/wolfsbane_diffuser_improved_inside"));
    }


    protected void diffuserCore(Item item, Identifier texture) {
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModModelTemplates.DIFFUSER_CORE.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(ModTextureSlots.CORE, new Material(texture)), this.modelOutput)));
    }
}
