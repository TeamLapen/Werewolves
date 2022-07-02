package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, REFERENCE.MODID);

    public static final RegistryObject<SoundEvent> entity_werewolf_bite = create("entity.werewolf.bite");
    public static final RegistryObject<SoundEvent> entity_werewolf_howl = create("entity.werewolf.howl");
    public static final RegistryObject<SoundEvent> entity_werewolf_growl = create("entity.werewolf.growl");

    static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    private static RegistryObject<SoundEvent> create(String soundNameIn) {
        ResourceLocation resourcelocation = new ResourceLocation(de.teamlapen.vampirism.REFERENCE.MODID, soundNameIn);
        return SOUND_EVENTS.register(soundNameIn, () -> new SoundEvent(resourcelocation));
    }
}
