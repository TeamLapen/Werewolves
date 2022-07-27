package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedArmorLayer.class)
public class BipedArmorLayerMixin<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> {

    @Inject(method = "renderArmorPiece(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/BipedArmorLayer;setPartVisibility(Lnet/minecraft/client/renderer/entity/model/BipedModel;Lnet/minecraft/inventory/EquipmentSlotType;)V", shift = At.Shift.AFTER))
    private void renderArmorPiece(MatrixStack matrixStack, IRenderTypeBuffer renderBuffer, T entity, EquipmentSlotType equipmentSlotType, int p_241739_5_, A model, CallbackInfo ci){
        if (!(entity instanceof PlayerEntity))return;
        if (!Helper.isWerewolf(((PlayerEntity) entity)))return;
        WerewolfPlayer werewolf = WerewolfPlayer.get(((PlayerEntity) entity));
        if (!FormHelper.isFormActionActive(werewolf)) return;
        if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.WEAR_ARMOR.get()) && werewolf.getActionHandler().isActionActive(ModActions.HUMAN_FORM.get())) return;
        model.setAllVisible(false);
    }
}
