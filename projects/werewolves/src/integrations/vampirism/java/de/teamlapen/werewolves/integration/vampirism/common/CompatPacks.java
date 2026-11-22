package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.Optional;

/**
 * The data and assets of the Vampirism integration are a built-in pack in {@code packs/vampirism} of the mod jar.
 * <p>
 * The pack is only registered when Vampirism is loaded, so its content does not need any mod loaded conditions and its files don't collide with the ones of the main mod (e.g. tags).
 */
public class CompatPacks {

    private static final String PACK_PATH = "packs/vampirism";

    /**
     * Same as {@link AddPackFindersEvent#addPackFinders}, but the pack is hidden from the pack selection screens.
     * It is required, so it stays enabled even though it is not part of the selection the screens commit.
     */
    public static void addPackFinders(AddPackFindersEvent event) {
        IModInfo modInfo = ModList.get().getModContainerById(REFERENCE.MODID).orElseThrow().getModInfo();
        String id = "mod/" + REFERENCE.MODID + ":" + PACK_PATH;
        Pack pack = Pack.readMetaAndCreate(
                new PackLocationInfo(id, Component.translatable("pack.werewolves.vampirism"), PackSource.BUILT_IN, Optional.of(new KnownPack("neoforge", id, modInfo.getVersion().toString()))),
                new JarContentsPackResources.JarContentsResourcesSupplier(modInfo.getOwningFile().getFile().getContents(), PACK_PATH),
                event.getPackType(),
                new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack != null) {
            Pack hidden = pack.hidden();
            event.addRepositorySource(consumer -> consumer.accept(hidden));
        }
    }
}
