package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, REFERENCE.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Supplier<? extends Block>> blockParent = new HashSet<>() {{
            add(ModBlocks.silver_ore);
            add(ModBlocks.deepslate_silver_ore);
            add(ModBlocks.silver_block);
            add(ModBlocks.raw_silver_block);
            add(ModBlocks.jacaranda_leaves);
            add(ModBlocks.jacaranda_log);
            add(ModBlocks.magic_leaves);
            add(ModBlocks.magic_log);
            add(ModBlocks.magic_planks);
            add(ModBlocks.totem_top_werewolves_werewolf);
            add(ModBlocks.totem_top_werewolves_werewolf_crafted);
        }};
        Set<Supplier<? extends Item>> itemsLayer = new HashSet<>() {{
            add(ModItems.silver_ingot);
            add(ModItems.liver);
            add(ModItems.cracked_bone);
            add(ModItems.injection_un_werewolf);
            add(ModItems.werewolf_tooth);
            add(ModItems.werewolf_minion_charm);
            add(ModItems.werewolf_minion_upgrade_simple);
            add(ModItems.werewolf_minion_upgrade_enhanced);
            add(ModItems.werewolf_minion_upgrade_special);
            add(ModItems.silver_nugget);
        }};
        Set<Supplier<? extends Block>> blockLayer = new HashSet<>() {{
            add(ModBlocks.jacaranda_sapling);
            add(ModBlocks.magic_sapling);
            add(ModBlocks.wolfsbane);
        }};
        Set<Supplier<? extends Item>> itemsHandHeld = new HashSet<>() {{
            add(ModItems.silver_axe);
            add(ModItems.silver_pickaxe);
            add(ModItems.silver_sword);
            add(ModItems.silver_shovel);
            add(ModItems.silver_hoe);
        }};

        blockParent.stream().map(Supplier::get).forEach(this::block);
        itemsLayer.stream().map(Supplier::get).forEach(this::item);
        blockLayer.stream().map(Supplier::get).forEach(this::blockLayer);
        itemsHandHeld.stream().map(Supplier::get).forEach(item -> item(item, "item/handheld"));

        this.withExistingParent(ModItems.werewolf_beast_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.werewolf_survivalist_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.human_werewolf_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.alpha_werewolf_spawn_egg.get(), this.mcLoc("item/template_spawn_egg"));

        this.item(ModItems.dream_catcher.get(), modLoc("item/dream_catcher_layer0"), modLoc("item/dream_catcher_layer1"));
        this.item(ModItems.charm_bracelet.get(), modLoc("item/charm_bracelet_layer0"), modLoc("item/charm_bracelet_layer1"));
        this.item(ModItems.bone_necklace.get(), modLoc("item/bone_necklace_layer0"), modLoc("item/bone_necklace_layer1"));
        this.item(ModItems.oil_bottle.get(), modLoc("item/oil_bottle"), modLoc("item/oil_bottle_overlay"));

        this.item(ModItems.raw_silver.get());
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
