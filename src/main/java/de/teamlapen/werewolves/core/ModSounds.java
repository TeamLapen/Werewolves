package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, REFERENCE.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_WEREWOLF_BITE = create("entity.werewolf.bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_WEREWOLF_HOWL = create("entity.werewolf.howl");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENTITY_WEREWOLF_GROWL = create("entity.werewolf.growl");

    static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> create(String soundNameIn) {
        ResourceLocation resourcelocation = new ResourceLocation(REFERENCE.MODID, soundNameIn);
        return SOUND_EVENTS.register(soundNameIn, () -> SoundEvent.createVariableRangeEvent(resourcelocation));
    }
}
