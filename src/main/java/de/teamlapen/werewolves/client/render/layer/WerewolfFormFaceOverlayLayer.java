package de.teamlapen.werewolves.client.render.layer;

import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WerewolfFormFaceOverlayLayer<T extends LivingEntity, E extends WerewolfBaseModel<T>> extends WerewolfFaceOverlayLayer<T, E> {

    private final WerewolfForm form;

    public WerewolfFormFaceOverlayLayer(WerewolfForm form, RenderLayerParent<T, E>  renderer) {
        super(renderer);
        this.form = form;
    }

    @Override
    public int getEyeType(IWerewolf werewolf) {
        return werewolf.getEyeType(this.form);
    }

    @Override
    public boolean hasGlowingEyes(IWerewolf werewolf) {
        return werewolf.hasGlowingEyes(this.form);
    }
}
