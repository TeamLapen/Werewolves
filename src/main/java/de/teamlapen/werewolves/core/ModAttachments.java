package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.WerewolvesAttachments;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.LevelDamage;
import de.teamlapen.werewolves.world.LevelWolfsbane;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, REFERENCE.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LevelWolfsbane>> LEVEL_WOLFSBANE = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.WOLFSBANE_HANDLER.getPath(), () -> AttachmentType.builder(LevelWolfsbane::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LevelDamage>> LEVEL_DAMAGE = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.DAMAGE_HANDLER.getPath(), () -> AttachmentType.builder(new LevelDamage.Factory()).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<WerewolfPlayer>> WEREWOLF_PLAYER = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.WEREWOLF_PLAYER.getPath(), () -> AttachmentType.builder(new WerewolfPlayer.Factory()).serialize(new WerewolfPlayer.Serializer()).copyOnDeath().build());

    static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }

}
