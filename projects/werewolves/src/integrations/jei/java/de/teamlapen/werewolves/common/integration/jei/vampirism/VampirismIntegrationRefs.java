package de.teamlapen.werewolves.common.integration.jei.vampirism;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.RegistryManager;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

/**
 * Content of the Vampirism integration, looked up by id or class name so the JEI integration does not need it on its classpath.
 * <p>
 * Must only be resolved if Vampirism is loaded, because the content is only registered then.
 *
 * @param diffusers  the wolfsbane diffuser blocks, the first one is the normal diffuser
 * @param screen     the wolfsbane diffuser screen
 * @param fuelMap    the wolfsbane diffuser fuel data map
 */
record VampirismIntegrationRefs(List<Block> diffusers, Class<? extends AbstractContainerScreen<?>> screen, DataMapType<Item, ?> fuelMap) {

    private static final List<String> DIFFUSERS = List.of("wolfsbane_diffuser_normal", "wolfsbane_diffuser_long", "wolfsbane_diffuser_strong");
    private static final String SCREEN = "de.teamlapen.werewolves.integration.vampirism.client.gui.screen.WolfsbaneDiffuserScreen";
    private static final Identifier FUEL_MAP = WIdentifier.mod("wolfsbane_diffuser_fuel");
    /**
     * Field name of the burn duration in the fuel data map entries
     */
    private static final String BURN_DURATION = "burn_duration";

    static VampirismIntegrationRefs resolve() {
        List<Block> diffusers = DIFFUSERS.stream().map(name -> BuiltInRegistries.BLOCK.getOptional(WIdentifier.mod(name)).orElseThrow(() -> new IllegalStateException("Missing block werewolves:" + name))).toList();
        DataMapType<Item, ?> fuelMap = Objects.requireNonNull(RegistryManager.getDataMap(Registries.ITEM, FUEL_MAP), "Missing data map " + FUEL_MAP);
        return new VampirismIntegrationRefs(diffusers, screenClass(), fuelMap);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends AbstractContainerScreen<?>> screenClass() {
        try {
            return (Class<? extends AbstractContainerScreen<?>>) Class.forName(SCREEN).asSubclass(AbstractContainerScreen.class);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Missing screen " + SCREEN, e);
        }
    }

    /**
     * @return the burn duration in ticks if the item is a wolfsbane diffuser fuel
     */
    OptionalInt burnTime(Holder<Item> item) {
        return burnTime(this.fuelMap, item);
    }

    /**
     * The fuel type implements a Vampirism interface, so the burn duration is read from the encoded data map value instead
     */
    private static <T> OptionalInt burnTime(DataMapType<Item, T> type, Holder<Item> item) {
        T value = item.getData(type);
        if (value == null) {
            return OptionalInt.empty();
        }
        return type.codec().encodeStart(JsonOps.INSTANCE, value).result()
                .filter(JsonElement::isJsonObject)
                .map(json -> json.getAsJsonObject().get(BURN_DURATION))
                .filter(json -> json != null && json.isJsonPrimitive())
                .map(json -> OptionalInt.of(json.getAsInt()))
                .orElse(OptionalInt.empty());
    }
}
