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
            add(ModBlocks.SILVER_ORE.get());
            add(ModBlocks.SILVER_BLOCK.get());
            add(ModBlocks.JACARANDA_LEAVES.get());
            add(ModBlocks.JACARANDA_LOG.get());
            add(ModBlocks.MAGIC_LEAVES.get());
            add(ModBlocks.MAGIC_LOG.get());
            add(ModBlocks.MAGIC_PLANKS.get());
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get());
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get());
        }};
        Set<Item> itemsLayer = new HashSet<Item>() {{
            add(ModItems.SILVER_INGOT.get());
            add(ModItems.LIVER.get());
            add(ModItems.CRACKED_BONE.get());
            add(ModItems.INJECTION_UN_WEREWOLF.get());
            add(ModItems.WEREWOLF_TOOTH.get());
            add(ModItems.WEREWOLF_MINION_CHARM.get());
            add(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get());
            add(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get());
            add(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get());
            add(ModItems.SILVER_NUGGET.get());
        }};
        Set<Block> blockLayer = new HashSet<Block>() {{
            add(ModBlocks.JACARANDA_SAPLING.get());
            add(ModBlocks.MAGIC_SAPLING.get());
            add(ModBlocks.WOLFSBANE.get());
        }};
        Set<Item> itemsHandHeld = new HashSet<Item>() {{
            add(ModItems.SILVER_AXE.get());
            add(ModItems.SILVER_PICKAXE.get());
            add(ModItems.SILVER_SWORD.get());
            add(ModItems.SILVER_SHOVEL.get());
            add(ModItems.SILVER_HOE.get());
        }};

        blockParent.forEach(this::block);
        itemsLayer.forEach(this::item);
        blockLayer.forEach(this::blockLayer);
        itemsHandHeld.forEach(item -> item(item, "item/handheld"));

        this.withExistingParent(ModItems.WEREWOLF_BEAST_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.WEREWOLF_SURVIVALIST_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.HUMAN_WEREWOLF_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.ALPHA_WEREWOLF_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));

        this.item(ModItems.DREAM_CATCHER.get(), modLoc("item/dream_catcher_layer0"), modLoc("item/dream_catcher_layer1"));
        this.item(ModItems.CHARM_BRACELET.get(), modLoc("item/charm_bracelet_layer0"), modLoc("item/charm_bracelet_layer1"));
        this.item(ModItems.BONE_NECKLACE.get(), modLoc("item/bone_necklace_layer0"), modLoc("item/bone_necklace_layer1"));
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
