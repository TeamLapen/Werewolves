package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.vampirism.client.render.entities.DualBipedRenderer;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

public class WerewolfMinionRenderer extends DualBipedRenderer<WerewolfMinionEntity, WerewolfBaseModel<WerewolfMinionEntity>> {

    public WerewolfMinionRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WerewolfBeastModel<>(), new WerewolfBeastModel<>(), 0.5f);
    }

    @Override
    protected Pair<ResourceLocation, Boolean> determineTextureAndModel(WerewolfMinionEntity entity) {
        return Pair.of(new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast/beast_"), true);
    }

    @Override
    protected void scale(WerewolfMinionEntity entityIn, MatrixStack p_225620_2_, float p_225620_3_) {
        float s = entityIn.getScale();
        p_225620_2_.scale(s,s,s);
    }
}
