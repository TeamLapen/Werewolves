package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.world.entities.extensions.IEntity;
import de.teamlapen.faction.common.util.AttachmentSynchronization;
import de.teamlapen.sync.api.IAttachmentSync;
import de.teamlapen.werewolves.api.WerewolvesAttachments;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.common.world.attachments.LevelDamage;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, REFERENCE.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LevelWolfsbane>> LEVEL_WOLFSBANE = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.WOLFSBANE_HANDLER.getPath(), () -> AttachmentType.builder(LevelWolfsbane::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LevelDamage>> LEVEL_DAMAGE = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.DAMAGE_HANDLER.getPath(), () -> AttachmentType.builder(new LevelDamage.Factory()).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<WerewolfPlayer>> WEREWOLF_PLAYER = ATTACHMENT_TYPES.register(WerewolvesAttachments.Keys.WEREWOLF_PLAYER.getPath(), () -> syncAttachment(new WerewolfPlayer.AttachmentOptions()).copyOnDeath().build());

    static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }

    private static <T extends IAttachmentSync & IEntity, Z extends IAttachmentHolder> AttachmentType.Builder<T> syncAttachment(AttachmentSynchronization<T, Z> options) {
        return AttachmentType.builder(options).serialize(options).sync(options);
    }

}
