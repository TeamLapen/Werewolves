package de.teamlapen.werewolves.data.provider.generator;

import de.teamlapen.faction.client.color.tint.RefinementTint;
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

        this.accessory(ModItems.DREAM_CATCHER.get(), "dream_catcher");
        this.accessory(ModItems.CHARM_BRACELET.get(), "charm_bracelet");
        this.accessory(ModItems.BONE_NECKLACE.get(), "bone_necklace");



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

    /**
     * Two layered refinement item, the second layer is tinted by the refinement rarity
     */
    protected void accessory(Item item, String texture) {
        Identifier model = ModelTemplates.TWO_LAYERED_ITEM.create(item, TextureMapping.layered(new Material(modLoc("item/" + texture + "_layer0")), new Material(modLoc("item/" + texture + "_layer1"))), this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, BLANK_LAYER, new RefinementTint()));
    }

    protected void flatWithTexture(Item item, Identifier texture) {
        this.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(new Material(texture)), this.modelOutput)));
    }
}
