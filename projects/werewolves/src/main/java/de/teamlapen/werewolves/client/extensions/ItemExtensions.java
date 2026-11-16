package de.teamlapen.werewolves.client.extensions;

import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.armor.WolfHeadModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
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
            return layerType == EquipmentClientInfo.LayerType.HUMANOID && wolfHeadModel != null && itemStack.getEquipmentSlot() == net.minecraft.world.entity.EquipmentSlot.HEAD ? wolfHeadModel : original;
        }
    };

    @SubscribeEvent
    public static void onLayersLoaded(EntityRenderersEvent.AddLayers event) {
        wolfHeadModel = new WolfHeadModel(event.getEntityModels().bakeLayer(ModModelRender.WOLF_HEAD));
    }
}
