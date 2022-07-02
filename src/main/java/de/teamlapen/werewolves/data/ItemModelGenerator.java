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
            add(ModBlocks.silver_ore.get());
            add(ModBlocks.silver_block.get());
            add(ModBlocks.jacaranda_leaves.get());
            add(ModBlocks.jacaranda_log.get());
            add(ModBlocks.magic_leaves.get());
            add(ModBlocks.magic_log.get());
            add(ModBlocks.magic_planks.get());
            add(ModBlocks.totem_top_werewolves_werewolf.get());
            add(ModBlocks.totem_top_werewolves_werewolf_crafted.get());
        }};
        Set<Item> itemsLayer = new HashSet<Item>() {{
            add(ModItems.silver_ingot.get());
            add(ModItems.liver.get());
            add(ModItems.cracked_bone.get());
            add(ModItems.injection_un_werewolf.get());
            add(ModItems.werewolf_tooth.get());
            add(ModItems.werewolf_minion_charm.get());
            add(ModItems.werewolf_minion_upgrade_simple.get());
            add(ModItems.werewolf_minion_upgrade_enhanced.get());
            add(ModItems.werewolf_minion_upgrade_special.get());
            add(ModItems.silver_nugget.get());
        }};
        Set<Block> blockLayer = new HashSet<Block>() {{
            add(ModBlocks.jacaranda_sapling.get());
            add(ModBlocks.magic_sapling.get());
            add(ModBlocks.wolfsbane.get());
        }};
        Set<Item> itemsHandHeld = new HashSet<Item>() {{
            add(ModItems.silver_axe.get());
            add(ModItems.silver_pickaxe.get());
            add(ModItems.silver_sword.get());
            add(ModItems.silver_shovel.get());
            add(ModItems.silver_hoe.get());
        }};

        blockParent.forEach(this::block);
        itemsLayer.forEach(this::item);
        blockLayer.forEach(this::blockLayer);
        itemsHandHeld.forEach(item -> item(item, "item/handheld"));

        this.withExistingParent(ModItems.werewolf_beast_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.werewolf_survivalist_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.human_werewolf_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.alpha_werewolf_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));

        this.item(ModItems.dream_catcher.get(), modLoc("item/dream_catcher_layer0"), modLoc("item/dream_catcher_layer1"));
        this.item(ModItems.charm_bracelet.get(), modLoc("item/charm_bracelet_layer0"), modLoc("item/charm_bracelet_layer1"));
        this.item(ModItems.bone_necklace.get(), modLoc("item/bone_necklace_layer0"), modLoc("item/bone_necklace_layer1"));
        this.item(ModItems.oil_bottle.get(), modLoc("item/oil_bottle"), modLoc("item/oil_bottle_overlay"));
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
