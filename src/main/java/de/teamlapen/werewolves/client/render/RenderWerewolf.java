package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.ModelWerewolf;
import de.teamlapen.werewolves.entity.EntityWerewolf;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWerewolf extends RenderLiving<EntityWerewolf> {
    private static final ResourceLocation werewolfTexture = new ResourceLocation(REFERENCE.MODID + ":textures/entity/direwolf.png");

    public RenderWerewolf(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelWerewolf(), 1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWerewolf entity) {
        return werewolfTexture;
    }

}
