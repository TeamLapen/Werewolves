package de.teamlapen.werewolves.data.provider;

import de.teamlapen.werewolves.common.core.ModArmorMaterials;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

/**
 * Generates the equipment asset definitions for the werewolves armor.
 * <p>
 * Textures are located at {@code textures/entity/equipment/humanoid/<asset>.png} and {@code textures/entity/equipment/humanoid_leggings/<asset>.png}.
 * The wolf pelt helmets use the wolf head model with {@code textures/entity/equipment/wolf_head/<asset>.png} (see {@link de.teamlapen.werewolves.client.extensions.ItemExtensions}).
 */
public class ModEquipmentAssetProvider extends EquipmentAssetProvider {

    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        humanoid(output, ModArmorMaterials.Asset.SILVER);
        humanoid(output, ModArmorMaterials.Asset.PELT_NORMAL);
        humanoid(output, ModArmorMaterials.Asset.PELT_ENHANCED);
        humanoid(output, ModArmorMaterials.Asset.PELT_ULTIMATE);
    }

    /**
     * Only adult humanoid layers. There are no baby textures (they use a different layout), so baby mobs don't render this armor
     */
    private static void humanoid(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output, ResourceKey<EquipmentAsset> asset) {
        output.accept(asset, EquipmentClientInfo.builder()
                .addLayers(EquipmentClientInfo.LayerType.HUMANOID, new EquipmentClientInfo.Layer(asset.identifier()))
                .addLayers(EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS, new EquipmentClientInfo.Layer(asset.identifier()))
                .build());
    }
}
