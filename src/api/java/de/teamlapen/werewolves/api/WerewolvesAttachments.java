package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAttachments;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WerewolvesAttachments {

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IWerewolfPlayer>> WEREWOLF_PLAYER = DeferredHolder.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Keys.WEREWOLF_PLAYER);

    public static class Keys {
        public static final ResourceLocation WEREWOLF_PLAYER = new ResourceLocation(WReference.MODID, "werewolf_player");
        public static final ResourceLocation WOLFSBANE_HANDLER = new ResourceLocation(WReference.MODID, "wolfbane_handler");
        public static final ResourceLocation DAMAGE_HANDLER = new ResourceLocation(WReference.MODID, "damage_handler");
    }
}
