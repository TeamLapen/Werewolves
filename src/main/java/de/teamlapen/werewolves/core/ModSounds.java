package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModSounds {

    public static final SoundEvent entity_werewolf_bite = getNull();
    public static final SoundEvent entity_werewolf_howl = getNull();
    public static final SoundEvent entity_werewolf_growl = getNull();

    static void registerSounds(IForgeRegistry<SoundEvent> registry) {
        registry.register(create("entity.werewolf.bite"));
        registry.register(create("entity.werewolf.howl"));
        registry.register(create("entity.werewolf.growl"));
    }

    private static SoundEvent create(String soundNameIn) {
        ResourceLocation resourcelocation = new ResourceLocation(REFERENCE.MODID, soundNameIn);
        SoundEvent event = new SoundEvent(resourcelocation);
        event.setRegistryName(REFERENCE.MODID, soundNameIn.replaceAll("\\.", "_"));
        return event;
    }
}
