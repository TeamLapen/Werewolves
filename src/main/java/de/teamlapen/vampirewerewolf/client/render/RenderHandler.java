package de.teamlapen.vampirewerewolf.client.render;

import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayerSpecialAttributes;
import de.teamlapen.vampirewerewolf.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
    private final Minecraft mc;

    //TODO Dummy Model
    private EntityZombie entityWerewolf;

    private int displayHeight, displayWidth;

    public RenderHandler(Minecraft mc) {
        this.mc = mc;
        this.displayHeight = mc.displayHeight;
        this.displayWidth = mc.displayWidth;
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        EntityPlayer player = event.getEntityPlayer();
        WerewolfPlayerSpecialAttributes werewolfAttributes = WerewolfPlayer.get(player).getSpecialAttributes();
        if (werewolfAttributes.werewolf) {
            event.setCanceled(true);
            if (entityWerewolf == null) {
                entityWerewolf = new EntityZombie(player.getEntityWorld());
            }

            float parTick = event.getPartialRenderTick();
            Render renderer = mc.getRenderManager().getEntityRenderObject(entityWerewolf);

            //copy values
            entityWerewolf.prevRenderYawOffset = player.prevRenderYawOffset;
            entityWerewolf.renderYawOffset = player.renderYawOffset;
            entityWerewolf.ticksExisted = player.ticksExisted;
            entityWerewolf.rotationPitch = player.rotationPitch;
            entityWerewolf.rotationYaw = player.rotationYaw;
            entityWerewolf.rotationYawHead = player.rotationYawHead;
            entityWerewolf.prevRotationYaw = player.prevRotationYaw;
            entityWerewolf.prevRotationPitch = player.prevRotationPitch;
            entityWerewolf.prevRotationYawHead = player.prevRotationYawHead;
            entityWerewolf.setInvisible(player.isInvisible());

            // Calculate render parameter
            float f1 = entityWerewolf.prevRotationYaw + (entityWerewolf.rotationYaw - entityWerewolf.prevRotationYaw) * parTick;
            double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * parTick;
            double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * parTick;
            double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * parTick;
            Entity entity = mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) parTick;
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) parTick;
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) parTick;
            mc.getRenderManager().renderEntity(entityWerewolf, d0 - d3, d1 - d4, d2 - d5, f1, event.getPartialRenderTick(), false);
        }
    }

    @SubscribeEvent
    public void onHandRendered(RenderSpecificHandEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            if (werewolf.getSpecialAttributes().werewolf && player.getHeldItemMainhand().isEmpty()) {
                event.setCanceled(true);
            }
        }
    }
}
