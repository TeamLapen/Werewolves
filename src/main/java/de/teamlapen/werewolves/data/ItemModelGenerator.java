package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.RegUtil;
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
            add(ModBlocks.SILVER_ORE);
            add(ModBlocks.DEEPSLATE_SILVER_ORE);
            add(ModBlocks.SILVER_BLOCK);
            add(ModBlocks.RAW_SILVER_BLOCK);
            add(ModBlocks.JACARANDA_LEAVES);
            add(ModBlocks.JACARANDA_LOG);
            add(ModBlocks.MAGIC_LEAVES);
            add(ModBlocks.MAGIC_LOG);
            add(ModBlocks.MAGIC_PLANKS);
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF);
            add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED);
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
        }};
        Set<Supplier<? extends Block>> blockLayer = new HashSet<>() {{
            add(ModBlocks.JACARANDA_SAPLING);
            add(ModBlocks.MAGIC_SAPLING);
            add(ModBlocks.WOLFSBANE);
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
        this.item(ModItems.OIL_BOTTLE.get(), modLoc("item/oil_bottle"), modLoc("item/oil_bottle_overlay"));

        this.item(ModItems.RAW_SILVER.get());
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

    public ItemModelBuilder item(Item item, String parent, ResourceLocation... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc(parent)).texture("layer0", REFERENCE.MODID + ":item/" + RegUtil.id(item).getPath());
        }
        return item(RegUtil.id(item).getPath(), texture);
    }

    public ItemModelBuilder blockLayer(Block item, ResourceLocation... texture) {
        if (texture.length == 0) {
            return withExistingParent(item, mcLoc("item/generated")).texture("layer0", REFERENCE.MODID + ":block/" + RegUtil.id(item).getPath());
        }
        return item(RegUtil.id(item).getPath(), texture);
    }

    @Nonnull
    public ItemModelBuilder withExistingParent(Item name, ResourceLocation parent) {
        return super.withExistingParent(RegUtil.id(name).getPath(), parent);
    }

    @Nonnull
    public ItemModelBuilder withExistingParent(Item name, Item parent) {
        return this.withExistingParent(name, RegUtil.id(parent));
    }

    @Nonnull
    public ItemModelBuilder withExistingParent(Block name, ResourceLocation parent) {
        return super.withExistingParent(RegUtil.id(name).getPath(), parent);
    }

    public ItemModelBuilder block(Block name) {
        try {
            return super.withExistingParent(RegUtil.id(name).getPath(), REFERENCE.MODID + ":block/" + RegUtil.id(name).getPath());
        } catch (IllegalStateException e) {
            return getBuilder(RegUtil.id(name).getPath()).parent(new ModelFile.UncheckedModelFile(REFERENCE.MODID + ":block/" + RegUtil.id(name).getPath()));
        }
    }
}
