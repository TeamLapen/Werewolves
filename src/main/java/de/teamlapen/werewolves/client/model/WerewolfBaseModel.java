package de.teamlapen.werewolves.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfBaseModel<T extends LivingEntity> extends EntityModel<T> {

    public abstract void setVisible(boolean visible);

    public abstract void setSneak(boolean sneak);

}
