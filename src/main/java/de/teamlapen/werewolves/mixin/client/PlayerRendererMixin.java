package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.render.layer.HumanWerewolfLayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Unique
    private WerewolfEarsModel<AbstractClientPlayer> werewolfEarsModel;
    @Unique
    private List<ResourceLocation> textures;
    @Deprecated
    private PlayerRendererMixin(EntityRendererProvider.Context p_174289_, PlayerModel<AbstractClientPlayer> p_174290_, float p_174291_) {
        super(p_174289_, p_174290_, p_174291_);
    }

    @Inject(method= "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", at = @At("RETURN"))
    private void addLayers(EntityRendererProvider.Context context, boolean isSlim, CallbackInfo ci) {
        this.werewolfEarsModel = new WerewolfEarsModel<>(context.bakeLayer(isSlim ? ModModelRender.EARS_CLAWS_SLIM : ModModelRender.EARS_CLAWS));
        this.addLayer(new HumanWerewolfLayer<>(this, this.werewolfEarsModel));
        this.textures = WerewolfEarsModel.getHumanTextures();
    }

    @Inject(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V", at = @At("RETURN"))
    private void renderWerewolfLeftHand(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear, CallbackInfo ci) {
        WerewolfPlayer werewolf = WerewolfPlayer.get(pPlayer);
        if (werewolf.getForm() == WerewolfForm.HUMAN) {
            ModelPart armPart = pRendererArm == this.model.rightArm ? this.werewolfEarsModel.rightArm : this.werewolfEarsModel.leftArm;
            this.model.copyPropertiesTo(this.werewolfEarsModel);
            armPart.render(pMatrixStack, pBuffer.getBuffer(RenderType.entityCutout(this.textures.get(werewolf.getSkinType() % this.textures.size()))), pCombinedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
