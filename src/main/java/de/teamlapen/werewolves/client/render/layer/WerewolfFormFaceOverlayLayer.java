package de.teamlapen.werewolves.client.render.layer;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class WerewolfFormFaceOverlayLayer<T extends LivingEntity> extends WerewolfFaceOverlayLayer<T> {

    private final WerewolfForm form;

    public WerewolfFormFaceOverlayLayer(WerewolfForm form, LivingRenderer<T, WerewolfBaseModel<T>> renderer, ResourceLocation[] overlays) {
        super(renderer, overlays);
        this.form = form;
    }

    @Override
    public int getEyeType(IWerewolf werewolf) {
        return werewolf.getEyeType(form);
    }

    @Override
    public boolean hasGlowingEyes(IWerewolf werewolf) {
        return werewolf.hasGlowingEyes(form);
    }
}
