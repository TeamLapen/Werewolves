package de.teamlapen.werewolves.api;

import net.minecraft.resources.Identifier;

public class WIdentifier {

    public static Identifier loc(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    public static Identifier mod(String path) {
        return Identifier.fromNamespaceAndPath(WReference.MODID, path);
    }
    public static String modString(String path) {
        return WReference.MODID + ":" + path;
    }

    public static Identifier common(String path) {
        return Identifier.fromNamespaceAndPath("c", path);
    }

    public static Identifier mc(String path) {
        return Identifier.withDefaultNamespace(path);
    }
}
