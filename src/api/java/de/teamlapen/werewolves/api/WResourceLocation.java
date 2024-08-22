package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.VReference;
import net.minecraft.resources.ResourceLocation;

public class WResourceLocation {

    public static ResourceLocation loc(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    public static ResourceLocation mod(String path) {
        return ResourceLocation.fromNamespaceAndPath(WReference.MODID, path);
    }

    public static ResourceLocation v(String path) {
        return ResourceLocation.fromNamespaceAndPath(VReference.MODID, path);
    }

    public static ResourceLocation common(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    public static ResourceLocation mc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
