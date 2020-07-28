package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, REFERENCE.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Block> blocks = new HashSet<Block>() {{
            add(ModBlocks.silver_ore);
            add(ModBlocks.silver_block);
            add(ModBlocks.wolfsbane);
            add(ModBlocks.jacaranda_leaves);
            add(ModBlocks.jacaranda_log);
            add(ModBlocks.jacaranda_sapling);
            add(ModBlocks.magic_leaves);
            add(ModBlocks.magic_log);
            add(ModBlocks.magic_sapling);
            add(ModBlocks.magic_planks);
        }};
        Set<Item> items = new HashSet<Item>() {{
            add(ModItems.silver_axe);
            add(ModItems.silver_pickaxe);
            add(ModItems.silver_sword);
            add(ModItems.silver_shovel);
            add(ModItems.silver_hoe);
            add(ModItems.silver_ingot);
            add(ModItems.liver);
        }};

        blocks.forEach(this::block);
        items.forEach(this::item);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Werewolves item model generator";
    }

    public ItemModelBuilder item(String item, ResourceLocation... texture) {
        ItemModelBuilder model = withExistingParent(item, mcLoc("item/generated"));
        if (texture != null) {
            for (int i = 0; i < texture.length; i++) {
                model.texture("layer" + i, texture[i]);
            }
        }
        return model;
    }

    @SuppressWarnings("ConstantConditions")
    public ItemModelBuilder item(Item item, ResourceLocation... texture) {
        if (texture != null && texture.length == 0) {
            return withExistingParent(item, mcLoc("item/generated")).texture("layer0", REFERENCE.MODID + ":item/" + item.getRegistryName().getPath());
        }
        return item(item.getRegistryName().getPath(), texture);
    }

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    public ItemModelBuilder withExistingParent(Item name, ResourceLocation parent) {
        return super.withExistingParent(name.getRegistryName().getPath(), parent);
    }

    @Nonnull
    public ItemModelBuilder withExistingParent(Item name, Item parent) {
        return this.withExistingParent(name, parent.getRegistryName());
    }

    @SuppressWarnings({"UnusedReturnValue", "ConstantConditions"})
    @Nonnull
    public ItemModelBuilder withExistingParent(Block name, ResourceLocation parent) {
        return super.withExistingParent(name.getRegistryName().getPath(), parent);
    }

    @SuppressWarnings("ConstantConditions")
    public ItemModelBuilder block(Block name) {
        try {
            return super.withExistingParent(name.getRegistryName().getPath(), REFERENCE.MODID + ":block/" + name.getRegistryName().getPath());
        } catch (IllegalStateException e) {
            return getBuilder(name.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(REFERENCE.MODID + ":block/" + name.getRegistryName().getPath()));
        }
    }
}
