package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.util.RegUtil;
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
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(@NotNull DataGenerator generator, @NotNull ExistingFileHelper existingFileHelper) {
        super(generator, REFERENCE.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Supplier<? extends Block>> blockParent = new HashSet<>() {{
            add(ModBlocks.SILVER_ORE);
            add(ModBlocks.DEEPSLATE_SILVER_ORE);
            add(ModBlocks.SILVER_BLOCK);
            add(ModBlocks.RAW_SILVER_BLOCK);
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF);
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED);
            add(ModBlocks.JACARANDA_LOG);
            add(ModBlocks.MAGIC_LOG);
            add(ModBlocks.STRIPPED_JACARANDA_LOG);
            add(ModBlocks.STRIPPED_MAGIC_LOG);
            add(ModBlocks.JACARANDA_PLANKS);
            add(ModBlocks.MAGIC_PLANKS);
            add(ModBlocks.JACARANDA_STAIRS);
            add(ModBlocks.MAGIC_STAIRS);
            add(ModBlocks.JACARANDA_WOOD);
            add(ModBlocks.MAGIC_WOOD);
            add(ModBlocks.STRIPPED_JACARANDA_WOOD);
            add(ModBlocks.STRIPPED_MAGIC_WOOD);
            add(ModBlocks.JACARANDA_PRESSURE_PLATE);
            add(ModBlocks.MAGIC_PRESSURE_PLATE);
            add(ModBlocks.JACARANDA_SLAB);
            add(ModBlocks.MAGIC_SLAB);
            add(ModBlocks.JACARANDA_FENCE_GATE);
            add(ModBlocks.MAGIC_FENCE_GATE);
            add(ModBlocks.JACARANDA_FENCE);
            add(ModBlocks.MAGIC_FENCE);
            add(ModBlocks.JACARANDA_LEAVES);
            add(ModBlocks.MAGIC_LEAVES);
        }};
        Set<Supplier<? extends Item>> itemsLayer = new HashSet<>() {{
            add(ModItems.SILVER_INGOT);
            add(ModItems.LIVER);
            add(ModItems.CRACKED_BONE);
            add(ModItems.INJECTION_UN_WEREWOLF);
            add(ModItems.WEREWOLF_TOOTH);
            add(ModItems.WEREWOLF_MINION_CHARM);
            add(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE);
            add(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED);
            add(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL);
            add(ModItems.SILVER_NUGGET);
            add(ModItems.SILVER_HELMET);
            add(ModItems.SILVER_CHESTPLATE);
            add(ModItems.SILVER_LEGGINGS);
            add(ModItems.SILVER_BOOTS);
            add(ModItems.WOLF_BERRIES);
            add(ModItems.JACARANDA_BOAT);
            add(ModItems.MAGIC_BOAT);
            add(ModItems.JACARANDA_CHEST_BOAT);
            add(ModItems.MAGIC_CHEST_BOAT);
        }};
        Set<Supplier<? extends Block>> blockLayer = new HashSet<>() {{
            add(ModBlocks.WOLFSBANE);
            add(ModBlocks.DAFFODIL);
            add(ModBlocks.JACARANDA_SAPLING);
            add(ModBlocks.MAGIC_SAPLING);
        }};
        Set<Supplier<? extends Item>> itemsHandHeld = new HashSet<>() {{
            add(ModItems.SILVER_AXE);
            add(ModItems.SILVER_PICKAXE);
            add(ModItems.SILVER_SWORD);
            add(ModItems.SILVER_SHOVEL);
            add(ModItems.SILVER_HOE);
        }};

        blockParent.stream().map(Supplier::get).forEach(this::block);
        itemsLayer.stream().map(Supplier::get).forEach(this::item);
        blockLayer.stream().map(Supplier::get).forEach(this::blockLayer);
        itemsHandHeld.stream().map(Supplier::get).forEach(item -> item(item, "item/handheld"));

        this.withExistingParent(ModItems.WEREWOLF_BEAST_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.WEREWOLF_SURVIVALIST_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.HUMAN_WEREWOLF_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));
        this.withExistingParent(ModItems.ALPHA_WEREWOLF_SPAWN_EGG.get(), this.mcLoc("item/template_spawn_egg"));

        this.item(ModItems.DREAM_CATCHER.get(), modLoc("item/dream_catcher_layer0"), modLoc("item/dream_catcher_layer1"));
        this.item(ModItems.CHARM_BRACELET.get(), modLoc("item/charm_bracelet_layer0"), modLoc("item/charm_bracelet_layer1"));
        this.item(ModItems.BONE_NECKLACE.get(), modLoc("item/bone_necklace_layer0"), modLoc("item/bone_necklace_layer1"));

        this.item(ModItems.RAW_SILVER.get());

        withExistingParent(ModBlocks.JACARANDA_TRAPDOOR.get(), modLoc("block/jacaranda_trapdoor_bottom"));
        withExistingParent(ModBlocks.MAGIC_TRAPDOOR.get(), modLoc("block/magic_trapdoor_bottom"));

        item(ModBlocks.JACARANDA_DOOR.get().asItem(), modLoc("item/jacaranda_door"));
        item(ModBlocks.MAGIC_DOOR.get().asItem(), modLoc("item/magic_door"));

        withExistingParent(ModBlocks.JACARANDA_BUTTON.get(), modLoc("block/jacaranda_button_inventory"));
        withExistingParent(ModBlocks.MAGIC_BUTTON.get(), modLoc("block/magic_button_inventory"));
        withExistingParent(ModBlocks.JACARANDA_FENCE.get(), modLoc("block/jacaranda_fence_inventory"));
        withExistingParent(ModBlocks.MAGIC_FENCE.get(), modLoc("block/magic_fence_inventory"));
        item(ModItems.JACARANDA_SIGN.get(), modLoc("item/jacaranda_sign"));
        item(ModItems.MAGIC_SIGN.get(), modLoc("item/magic_sign"));
        this.withExistingParent(ModItems.WITCH_HEAD.get(), mcLoc("item/template_skull"));
    }

    @NotNull
    @Override
    public String getName() {
        return "Werewolves item model generator";
    }

    public ItemModelBuilder item(String item, ResourceLocation @NotNull ... texture) {
        ItemModelBuilder model = withExistingParent(item, mcLoc("item/generated"));
        for (int i = 0; i < texture.length; i++) {
            model.texture("layer" + i, texture[i]);
        }
        return model;
    }

    public ItemModelBuilder item(@NotNull Item item, ResourceLocation... texture) {
        return item(item, "item/generated", texture);
    }

    public ItemModelBuilder item(@NotNull Item item, String parent, ResourceLocation @NotNull ... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc(parent)).texture("layer0", REFERENCE.MODID + ":item/" + RegUtil.id(item).getPath());
        }
        return item(RegUtil.id(item).getPath(), texture);
    }

    public ItemModelBuilder blockLayer(@NotNull Block item, ResourceLocation @NotNull ... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc("item/generated")).texture("layer0", REFERENCE.MODID + ":block/" + RegUtil.id(item).getPath());
        }
        return item(RegUtil.id(item).getPath(), texture);
    }

    @NotNull
    public ItemModelBuilder withExistingParent(@NotNull Item name, ResourceLocation parent) {
        return super.withExistingParent(RegUtil.id(name).getPath(), parent);
    }

    @NotNull
    public ItemModelBuilder withExistingParent(@NotNull Item name, @NotNull Item parent) {
        return this.withExistingParent(name, RegUtil.id(parent));
    }

    @NotNull
    public ItemModelBuilder withExistingParent(@NotNull Block name, ResourceLocation parent) {
        try {
            return super.withExistingParent(RegUtil.id(name).getPath(), parent);
        } catch (IllegalStateException e) {
            return getBuilder(RegUtil.id(name).getPath()).parent(new ModelFile.UncheckedModelFile(parent));
        }
    }

    public ItemModelBuilder block(@NotNull Block name) {
        try {
            return super.withExistingParent(RegUtil.id(name).getPath(), REFERENCE.MODID + ":block/" + RegUtil.id(name).getPath());
        } catch (IllegalStateException e) {
            return getBuilder(RegUtil.id(name).getPath()).parent(new ModelFile.UncheckedModelFile(REFERENCE.MODID + ":block/" + RegUtil.id(name).getPath()));
        }
    }
}
