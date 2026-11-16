package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.VampirismAttachments;
import de.teamlapen.vampirism.api.world.IGarlicChunkHandler;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.world.IWolfsbaneChunkHandler;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WerewolvesAttachments {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IWerewolfPlayer>> WEREWOLF_PLAYER = DeferredHolder.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Keys.WEREWOLF_PLAYER);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IWolfsbaneChunkHandler>> WOLFSBANE_HANDLER = DeferredHolder.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Keys.WOLFSBANE_HANDLER);


    public static class Keys {
        public static final Identifier WEREWOLF_PLAYER = WIdentifier.mod("werewolf_player");
        public static final Identifier WOLFSBANE_HANDLER = WIdentifier.mod("wolfbane_handler");
        public static final Identifier DAMAGE_HANDLER = WIdentifier.mod("damage_handler");
    }
}
