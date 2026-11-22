package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

/**
 * The data and assets of the Vampirism integration are a built-in pack in {@code packs/vampirism} of the mod jar.
 * <p>
 * The pack is only registered when Vampirism is loaded, so its content does not need any mod loaded conditions and its files don't collide with the ones of the main mod (e.g. tags).
 */
public class CompatPacks {

    public static void addPackFinders(AddPackFindersEvent event) {
        for (PackType type : PackType.values()) {
            event.addPackFinders(WIdentifier.mod("packs/vampirism"), type, Component.translatable("pack.werewolves.vampirism"), PackSource.BUILT_IN, true, Pack.Position.TOP);
        }
    }
}
