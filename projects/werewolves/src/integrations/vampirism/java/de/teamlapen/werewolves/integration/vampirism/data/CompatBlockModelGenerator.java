package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.data.provider.models.ModModelTemplates;
import de.teamlapen.vampirism.data.provider.models.ModTextureSlots;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CompatBlockModelGenerator extends BlockModelGenerators {

    public CompatBlockModelGenerator(net.minecraft.client.data.models.BlockModelGenerators generators) {
        super(generators.blockStateOutput, generators.itemModelOutput, generators.modelOutput);
    }

    @Override
    public void run() {
        createDiffuser();
    }

    protected void createDiffuser() {
        Identifier defaultModel = ModModelTemplates.DIFFUSER.create(CompatBlocks.WOLFSBANE_DIFFUSER.get(), new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod("block/wolfsbane_diffuser_inside"))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(CompatBlocks.WOLFSBANE_DIFFUSER.get(), plainVariant(defaultModel)));
        createDefaultBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER.get(), defaultModel);

        Identifier strongModel = ModModelTemplates.DIFFUSER.create(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod("block/wolfsbane_diffuser_improved_inside"))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), plainVariant(strongModel)));
        createDefaultBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), strongModel);

        Identifier longModel = ModModelTemplates.DIFFUSER.create(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.loc("vampirism", "block/garlic_diffuser_core_long"))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), plainVariant(longModel)));
        createDefaultBlockItem(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), longModel);
    }


    protected void createDefaultBlockItem(Block block, Identifier model) {
        var item = block.asItem();
        if (item != Items.AIR) {
            this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(model));
        }
    }
}
