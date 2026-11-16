package de.teamlapen.werewolves.data.provider.generator;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Item models of all non block items. Block item models are created in {@link ModBlockModelGenerators}
 */
public class ModItemModelGenerator extends ItemModelGenerators {

    private static final ModelTemplate DIFFUSER_CORE = new ModelTemplate(Optional.of(vLoc("item/garlic_diffuser_core")), Optional.empty(), TextureSlot.TEXTURE);

    public ModItemModelGenerator(ItemModelGenerators generator) {
        super(generator.itemModelOutput, generator.modelOutput);
    }

    @Override
    public void run() {
        Stream.<Supplier<? extends Item>>of(
                ModItems.SILVER_INGOT,
                ModItems.LIVER,
                ModItems.CRACKED_BONE,
                ModItems.INJECTION_UN_WEREWOLF,
                ModItems.WEREWOLF_TOOTH,
                ModItems.WEREWOLF_MINION_CHARM,
                ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE,
                ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED,
                ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL,
                ModItems.SILVER_NUGGET,
                ModItems.SILVER_HELMET,
                ModItems.SILVER_CHESTPLATE,
                ModItems.SILVER_LEGGINGS,
                ModItems.SILVER_BOOTS,
                ModItems.WOLF_BERRIES,
                ModItems.JACARANDA_BOAT,
                ModItems.MAGIC_BOAT,
                ModItems.JACARANDA_CHEST_BOAT,
                ModItems.MAGIC_CHEST_BOAT,
                ModItems.PELT,
                ModItems.DARK_PELT,
                ModItems.WHITE_PELT,
                ModItems.PELT_HELMET,
                ModItems.PELT_CHESTPLATE,
                ModItems.PELT_LEGGINGS,
                ModItems.PELT_BOOTS,
                ModItems.DARK_PELT_HELMET,
                ModItems.DARK_PELT_CHESTPLATE,
                ModItems.DARK_PELT_LEGGINGS,
                ModItems.DARK_PELT_BOOTS,
                ModItems.WHITE_PELT_HELMET,
                ModItems.WHITE_PELT_CHESTPLATE,
                ModItems.WHITE_PELT_LEGGINGS,
                ModItems.WHITE_PELT_BOOTS,
                ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE,
                ModItems.WOLFSBANE_FINDER,
                ModItems.RAW_SILVER,
                // since 1.21.5 spawn eggs use their own flat texture instead of a tinted template
                ModItems.WEREWOLF_BEAST_SPAWN_EGG,
                ModItems.WEREWOLF_SURVIVALIST_SPAWN_EGG,
                ModItems.HUMAN_WEREWOLF_SPAWN_EGG,
                ModItems.ALPHA_WEREWOLF_SPAWN_EGG
        ).map(Supplier::get).forEach(item -> this.generateFlatItem(item, ModelTemplates.FLAT_ITEM));

        Stream.<Supplier<? extends Item>>of(
                ModItems.SILVER_AXE,
                ModItems.SILVER_PICKAXE,
                ModItems.SILVER_SWORD,
                ModItems.SILVER_SHOVEL,
                ModItems.SILVER_HOE
        ).map(Supplier::get).forEach(item -> this.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM));

        Stream.<Supplier<? extends Block>>of(
                ModBlocks.JACARANDA_SAPLING,
                ModBlocks.MAGIC_SAPLING
        ).map(Supplier::get).forEach(this::blockLayer);

        this.generateLayeredItem(ModItems.DREAM_CATCHER.get(), new Material(modLoc("item/dream_catcher_layer0")), new Material(modLoc("item/dream_catcher_layer1")));
        this.generateLayeredItem(ModItems.CHARM_BRACELET.get(), new Material(modLoc("item/charm_bracelet_layer0")), new Material(modLoc("item/charm_bracelet_layer1")));
        this.generateLayeredItem(ModItems.BONE_NECKLACE.get(), new Material(modLoc("item/bone_necklace_layer0")), new Material(modLoc("item/bone_necklace_layer1")));

        this.diffuserCore(ModItems.WOLFSBANE_DIFFUSER_CORE.get(), modLoc("block/wolfsbane_diffuser_inside"));
        this.diffuserCore(ModItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED.get(), modLoc("block/wolfsbane_diffuser_improved_inside"));

        this.flatWithTexture(ModItems.CROSSBOW_ARROW_SILVER_BOLT.get(), vLoc("item/quarrel"));
    }

    protected static Identifier vLoc(String string) {
        return WIdentifier.loc("vampirism", string);
    }

    protected static Identifier modLoc(String string) {
        return WIdentifier.mod(string);
    }

    protected void blockLayer(ItemLike block) {
        Item item = block.asItem();
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(TextureMapping.getBlockTexture((Block) block)), this.modelOutput)));
    }

    protected void flatWithTexture(Item item, Identifier texture) {
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(new Material(texture)), this.modelOutput)));
    }

    protected void diffuserCore(Item item, Identifier texture) {
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(DIFFUSER_CORE.create(ModelLocationUtils.getModelLocation(item), new TextureMapping().put(TextureSlot.TEXTURE, new Material(texture)), this.modelOutput)));
    }
}
