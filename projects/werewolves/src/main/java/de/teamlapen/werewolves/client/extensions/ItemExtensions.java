package de.teamlapen.werewolves.client.extensions;

import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.armor.WolfHeadModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemExtensions {

    @Nullable
    private static WolfHeadModel wolfHeadModel;

    public static final IClientItemExtensions WOLF_PELT = new IClientItemExtensions() {
        @Override
        public @NotNull Model<?> getHumanoidArmorModel(@NotNull ItemStack itemStack, EquipmentClientInfo.@NotNull LayerType layerType, @NotNull Model original) {
            return isWolfHead(itemStack, layerType) ? wolfHeadModel : original;
        }

        /**
         * The wolf head model uses a different texture layout than the humanoid armor layer
         */
        @Override
        public @Nullable Identifier getArmorTexture(@NotNull ItemStack stack, EquipmentClientInfo.@NotNull LayerType type, EquipmentClientInfo.@NotNull Layer layer, @NotNull Identifier _default) {
            return isWolfHead(stack, type) ? layer.textureId().withPath(path -> "textures/entity/equipment/wolf_head/" + path + ".png") : null;
        }
    };

    private static boolean isWolfHead(ItemStack stack, EquipmentClientInfo.LayerType type) {
        // ItemStack#getEquipmentSlot is only an override hook and returns null for these items, so check the equippable component
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        return type == EquipmentClientInfo.LayerType.HUMANOID && wolfHeadModel != null && equippable != null && equippable.slot() == EquipmentSlot.HEAD;
    }

    @SubscribeEvent
    public static void onLayersLoaded(EntityRenderersEvent.AddLayers event) {
        wolfHeadModel = new WolfHeadModel(event.getEntityModels().bakeLayer(ModModelRender.WOLF_HEAD));
    }
}
