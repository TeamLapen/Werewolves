package de.teamlapen.vampirewerewolf.client.render;

import de.teamlapen.vampirewerewolf.client.model.ModelWerewolf;
import de.teamlapen.vampirewerewolf.entities.EntityWerewolf;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;


public class RenderWerewolf extends RenderLiving<EntityWerewolf> {
    private static final ResourceLocation ghostTexture = new ResourceLocation(REFERENCE.MODID + ":textures/entity/werewolf.png");

    public RenderWerewolf(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelWerewolf(), 2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWerewolf entity) {
        return ghostTexture;
    }

}
