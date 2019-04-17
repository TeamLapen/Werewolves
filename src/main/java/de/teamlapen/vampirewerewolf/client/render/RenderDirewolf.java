package de.teamlapen.vampirewerewolf.client.render;

import de.teamlapen.vampirewerewolf.client.model.ModelDirewolf;
import de.teamlapen.vampirewerewolf.entity.EntityDirewolf;
import de.teamlapen.vampirewerewolf.util.REFERENCE;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;


public class RenderDirewolf extends RenderLiving<EntityDirewolf> {
    private static final ResourceLocation direwolfTexture = new ResourceLocation(REFERENCE.MODID + ":textures/entity/direwolf.png");

    public RenderDirewolf(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelDirewolf(), 1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDirewolf entity) {
        return direwolfTexture;
    }
}
