package de.teamlapen.werewolves.proxy;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import de.teamlapen.werewolves.client.core.ModKeys;
import de.teamlapen.werewolves.client.render.RenderHandler;
import de.teamlapen.werewolves.core.RegistryManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Clientside Proxy
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        RegistryManager.setupClientRegistryManager();
    }

    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
        super.onInitStep(step, event);
        RegistryManager.getRegistryManagerClient().onInitStep(step, event);
        switch (step) {
            case PRE_INIT:
                ModKeys.register();
                this.registerSubscriptions();
                break;
            case INIT:
                break;
            case POST_INIT:
                break;
        }
    }

    private void registerSubscriptions() {
        MinecraftForge.EVENT_BUS.register(new RenderHandler(Minecraft.getMinecraft()));
    }

    @Override
    public List<Entity> getRayTraceEntity() {
        Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        double d0 = 50;
        RayTraceResult result = entity.rayTrace(d0, 20.0F);
        List<Entity> list = Minecraft.getMinecraft().world.getEntitiesInAABBexcluding(entity, new AxisAlignedBB(result.getBlockPos()).grow(5.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate<Entity>) (@Nullable Entity p_apply_1_) -> p_apply_1_ != null && p_apply_1_.canBeCollidedWith()));
        return list;
    }
}
