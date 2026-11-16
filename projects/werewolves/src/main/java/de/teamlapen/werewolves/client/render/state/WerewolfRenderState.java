package de.teamlapen.werewolves.client.render.state;

import de.teamlapen.werewolves.api.entities.werewolf.IWerewolfDataholder;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

/**
 * Render state shared by all werewolf models (entities, minions and transformed players)
 */
public class WerewolfRenderState extends HumanoidRenderState {

    public WerewolfForm form = WerewolfForm.NONE;
    public Identifier texture = MissingTextureAtlasSprite.getLocation();
    public int skinType;
    public int eyeType;
    public boolean glowingEyes;

    /**
     * Extracts the humanoid and werewolf specific data into the given state.
     *
     * @param form the form that should be rendered. Data is extracted for this form
     */
    public static void extract(LivingEntity entity, IWerewolfDataholder werewolf, WerewolfForm form, WerewolfRenderState state, float partialTicks, ItemModelResolver itemModelResolver) {
        HumanoidMobRenderer.extractHumanoidRenderState(entity, state, partialTicks, itemModelResolver);
        state.form = form;
        state.skinType = Math.max(0, werewolf.getSkinType(form));
        state.eyeType = Math.max(0, werewolf.getEyeType(form));
        state.glowingEyes = werewolf.hasGlowingEyes(form);
    }
}
