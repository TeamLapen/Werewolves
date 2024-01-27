package de.teamlapen.werewolves.client.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.items.IWerewolvesBoat;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class WerewolvesBoatRenderer extends BoatRenderer {

    private final Map<IWerewolvesBoat.BoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public WerewolvesBoatRenderer(EntityRendererProvider.@NotNull Context context, boolean hasChest) {
        super(context, hasChest);
        this.boatResources = Stream.of(IWerewolvesBoat.BoatType.values()).collect(ImmutableMap.toImmutableMap((type) -> type, (type) -> {
            return Pair.of(new ResourceLocation(getTextureLocation(type, hasChest)), this.createBoatModel(context, type, hasChest));
        }));
    }

    private @NotNull BoatModel createBoatModel(EntityRendererProvider.@NotNull Context context, IWerewolvesBoat.@NotNull BoatType type, boolean hasChest) {
        return hasChest ? new ChestBoatModel(context.bakeLayer(ModModelRender.createChestBoatModelName(type))) : new BoatModel(context.bakeLayer(ModModelRender.createBoatModelName(type)));
    }

    public @NotNull String getTextureLocation(IWerewolvesBoat.@NotNull BoatType type, boolean hasChest) {
        return hasChest ? REFERENCE.MODID + ":textures/entity/chest_boat/" + type.getName() + ".png" : REFERENCE.MODID + ":textures/entity/boat/" + type.getName() + ".png";
    }

    @NotNull
    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@NotNull Boat boat) {
        return this.boatResources.get(((IWerewolvesBoat) boat).getBType());
    }
}
