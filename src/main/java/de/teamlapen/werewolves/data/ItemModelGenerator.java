package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, REFERENCE.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Block> blockParent = new HashSet<Block>() {{
            add(ModBlocks.silver_ore);
            add(ModBlocks.silver_block);
            add(ModBlocks.jacaranda_leaves);
            add(ModBlocks.jacaranda_log);
            add(ModBlocks.magic_leaves);
            add(ModBlocks.magic_log);
            add(ModBlocks.magic_planks);
            add(ModBlocks.totem_top_werewolves_werewolf);
            add(ModBlocks.totem_top_werewolves_werewolf_crafted);
        }};
        Set<Item> itemsLayer = new HashSet<Item>() {{
            add(ModItems.silver_ingot);
            add(ModItems.liver);
            add(ModItems.bone);
            add(ModItems.injection_un_werewolf);
            add(ModItems.werewolf_tooth);
        }};
        Set<Block> blockLayer = new HashSet<Block>() {{
            add(ModBlocks.jacaranda_sapling);
            add(ModBlocks.magic_sapling);
            add(ModBlocks.wolfsbane);
        }};
        Set<Item> itemsHandHeld = new HashSet<Item>() {{
            add(ModItems.silver_axe);
            add(ModItems.silver_pickaxe);
            add(ModItems.silver_sword);
            add(ModItems.silver_shovel);
            add(ModItems.silver_hoe);
        }};

        blockParent.forEach(this::block);
        itemsLayer.forEach(this::item);
        blockLayer.forEach(this::blockLayer);
        itemsHandHeld.forEach(item -> item(item, "item/handheld"));

        this.withExistingParent(ModItems.werewolf_beast_spawn_egg, this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.werewolf_survivalist_spawn_egg, this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.human_werewolf_spawn_egg, this.mcLoc("item/template_spawn_egg"));
    }

    @Nonnull
    @Override
    public String getName() {
        return "Werewolves item model generator";
    }

    public ItemModelBuilder item(String item, ResourceLocation... texture) {
        ItemModelBuilder model = withExistingParent(item, mcLoc("item/generated"));
        for (int i = 0; i < texture.length; i++) {
            model.texture("layer" + i, texture[i]);
        }
        return model;
    }

    public ItemModelBuilder item(Item item, ResourceLocation... texture) {
        return item(item, "item/generated", texture);
    }

    @SuppressWarnings("ConstantConditions")
    public ItemModelBuilder item(Item item, String parent, ResourceLocation... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc(parent)).texture("layer0", REFERENCE.MODID + ":item/" + item.getRegistryName().getPath());
        }
        return item(item.getRegistryName().getPath(), texture);
    }

    @SuppressWarnings("ConstantConditions")
    public ItemModelBuilder blockLayer(Block item, ResourceLocation... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc("item/generated")).texture("layer0", REFERENCE.MODID + ":block/" + item.getRegistryName().getPath());
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
