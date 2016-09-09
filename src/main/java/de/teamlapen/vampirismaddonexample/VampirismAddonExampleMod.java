package de.teamlapen.vampirismaddonexample;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = VampirismAddonExampleMod.MODID, name = VampirismAddonExampleMod.MODID, version = VampirismAddonExampleMod.VERSION, acceptedMinecraftVersions = "[1.10,)", dependencies = "required-after:Forge@[12.18.1.2076,);required-after:vampirism@[1.0.0-alpha1,)")
public class VampirismAddonExampleMod {

    public static final String MODID = "vampirism-addon-example";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static VampirismAddonExampleMod instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityStrongVampire.class, "strongVampire", 0, VampirismAddonExampleMod.instance, 80, 1, true);
        EntityRegistry.registerEgg(EntityStrongVampire.class, 0x8B15A3, 0x555555);

        //Should be handled in Client Proxy probably.
        RenderingRegistry.registerEntityRenderingHandler(EntityStrongVampire.class, new IRenderFactory<EntityStrongVampire>() {
            @Override
            public Render<? super EntityStrongVampire> createRenderFor(RenderManager manager) {
                return new RenderBiped<EntityStrongVampire>(manager, new ModelBiped(1), 1F);
            }
        });

    }


}
