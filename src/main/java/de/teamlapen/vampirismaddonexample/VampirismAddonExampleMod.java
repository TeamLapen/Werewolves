package de.teamlapen.vampirismaddonexample;

import de.teamlapen.vampirism.VampirismMod;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber
@Mod(modid = VampirismAddonExampleMod.MODID, name = VampirismAddonExampleMod.MODID, version = VampirismAddonExampleMod.VERSION, acceptedMinecraftVersions = "[1.10,)", dependencies = VampirismAddonExampleMod.DEPENDENCIES)
public class VampirismAddonExampleMod {

    public static final String MODID = "vampirism-addon-example";
    public static final String VERSION = "1.0";
    /**
     * TODO Adjust
     */
    public static final String DEPENDENCIES = "required-after:forge@[14.23.2.2611,);required-after:vampirism@[1.3.7,)";

    @Mod.Instance
    public static VampirismAddonExampleMod instance;

    private static int modEntityId = 0;

    @SubscribeEvent
    public static void onEntityRegistration(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityStrongVampire.class).id(new ResourceLocation(MODID, "strong_vampire"), modEntityId++)
                .name("vampirism.strong_vampire").tracker(80, 1, true).egg(0x8B15A3, 0x555555).build());
        VampirismMod.log.t("adsfadfasdfadsf");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        //Should probably be handled in Client Proxy.
        RenderingRegistry.registerEntityRenderingHandler(EntityStrongVampire.class, manager -> new RenderBiped<>(manager, new ModelBiped(1), 1F));

    }


}
