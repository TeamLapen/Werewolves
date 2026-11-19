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
        createDiffuser(CompatBlocks.WOLFSBANE_DIFFUSER.get(), "block/wolfsbane_diffuser_core");
        createDiffuser(CompatBlocks.WOLFSBANE_DIFFUSER_STRONG.get(), "block/wolfsbane_diffuser_core_strong");
        createDiffuser(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), "block/wolfsbane_diffuser_core_long");

        createDiffuserCore(CompatBlocks.WOLFSBANE_DIFFUSER_CORE.get(), "wolfsbane_diffuser_core");
        createDiffuserCore(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_STRONG.get(), "wolfsbane_diffuser_core_strong");
        createDiffuserCore(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_LONG.get(), "wolfsbane_diffuser_core_long");
    }

    protected void createDiffuser(Block block, String coreTexture) {
        Identifier model = ModModelTemplates.DIFFUSER.create(block, new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod(coreTexture))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        createDefaultBlockItem(block, model);
    }

    protected void createDiffuserCore(Block block, String texture) {
        Identifier model = ModModelTemplates.DIFFUSER_CORE.create(block, new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod("block/" + texture))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        registerSimpleItemModel(block, WIdentifier.mod("item/" + texture));
        createFlatItemModel(block.asItem());
    }


    protected void createDefaultBlockItem(Block block, Identifier model) {
        var item = block.asItem();
        if (item != Items.AIR) {
            this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(model));
        }
    }
}
