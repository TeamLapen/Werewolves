package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;setPartVisibility(Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/EquipmentSlot;)V", shift = At.Shift.AFTER))
    private void renderArmorPiece(PoseStack matrixStack, MultiBufferSource renderBuffer, T entity, EquipmentSlot equipmentSlotType, int p_241739_5_, A model, CallbackInfo ci) {
        if (!(entity instanceof Player)) return;
        if (!Helper.isWerewolf(((Player) entity))) return;
        WerewolfPlayer werewolf = WerewolfPlayer.get(((Player) entity));
        if (!FormHelper.isFormActionActive(werewolf)) return;
        if (werewolf.getSkillHandler().isSkillEnabled(ModSkills.WEAR_ARMOR.get()) && werewolf.getActionHandler().isActionActive(ModActions.HUMAN_FORM.get())) {
            return;
        }
        model.setAllVisible(false);
    }
}
