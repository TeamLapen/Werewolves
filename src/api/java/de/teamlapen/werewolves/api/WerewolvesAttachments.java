package de.teamlapen.werewolves.api;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WerewolvesAttachments {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IWerewolfPlayer>> WEREWOLF_PLAYER = DeferredHolder.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Keys.WEREWOLF_PLAYER);

    public static class Keys {
        public static final ResourceLocation WEREWOLF_PLAYER = WResourceLocation.mod("werewolf_player");
        public static final ResourceLocation WOLFSBANE_HANDLER = WResourceLocation.mod("wolfbane_handler");
        public static final ResourceLocation DAMAGE_HANDLER = WResourceLocation.mod("damage_handler");
    }
}
