package de.teamlapen.werewolves.client.render.layer;

import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class WerewolfFormFaceOverlayLayer<T extends LivingEntity> extends WerewolfFaceOverlayLayer<T> {

    private final WerewolfForm form;

    public WerewolfFormFaceOverlayLayer(WerewolfForm form, @NotNull LivingEntityRenderer<T, WerewolfBaseModel<T>> renderer, ResourceLocation[] overlays) {
        super(renderer, overlays);
        this.form = form;
    }

    @Override
    public int getEyeType(@NotNull IWerewolf werewolf) {
        return werewolf.getEyeType(this.form);
    }

    @Override
    public boolean hasGlowingEyes(@NotNull IWerewolf werewolf) {
        return werewolf.hasGlowingEyes(this.form);
    }
}
