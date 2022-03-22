package de.teamlapen.werewolves.client.render.layer;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WerewolfFormFaceOverlayLayer<T extends LivingEntity> extends WerewolfFaceOverlayLayer<T> {

    private final WerewolfForm form;

    public WerewolfFormFaceOverlayLayer(WerewolfForm form, LivingEntityRenderer<T, WerewolfBaseModel<T>> renderer, ResourceLocation[] overlays) {
        super(renderer, overlays);
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
