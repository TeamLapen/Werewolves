package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, REFERENCE.MODID);

    public static final RegistryObject<SoundEvent> entity_werewolf_bite = SOUNDS.register("entity_werewolf_bite", () -> create("entity.werewolf.bite"));
    public static final RegistryObject<SoundEvent> entity_werewolf_howl = SOUNDS.register("entity_werewolf_howl", () -> create("entity.werewolf.howl"));
    public static final RegistryObject<SoundEvent> entity_werewolf_growl = SOUNDS.register("entity_werewolf_growl", () -> create("entity.werewolf.growl"));

    static void registerSounds(IEventBus bus) {
        SOUNDS.register(bus);
    }

    private static SoundEvent create(String soundNameIn) {
        ResourceLocation resourcelocation = new ResourceLocation(REFERENCE.MODID, soundNameIn);
        SoundEvent event = new SoundEvent(resourcelocation);
        event.setRegistryName(REFERENCE.MODID, soundNameIn.replaceAll("\\.", "_"));
        return event;
    }
}
