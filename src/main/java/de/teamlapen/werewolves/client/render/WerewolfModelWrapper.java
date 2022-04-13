package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record WerewolfModelWrapper<T extends LivingEntity>(WerewolfBaseModel<T> model,
                                                           Collection<RenderLayer<T, WerewolfBaseModel<T>>> layers,
                                                           List<ResourceLocation> textures, float shadow,
                                                           boolean skipPlayerModel) {

    public static class Builder<T extends LivingEntity> {
        private WerewolfBaseModel<T> model = null;
        private Collection<RenderLayer<T, WerewolfBaseModel<T>>> layers = Collections.emptyList();
        private List<ResourceLocation> textures = Collections.emptyList();
        private float shadow = 0f;
        private boolean skipPlayerModel = false;

        public Builder<T> model(WerewolfBaseModel<T> model) {
            this.model = model;
            return this;
        }

        public Builder<T> layers(@Nonnull Collection<RenderLayer<T, WerewolfBaseModel<T>>> function) {
            this.layers = function;
            return this;
        }

        public Builder<T> textures(@Nonnull List<ResourceLocation> textures) {
            this.textures = textures;
            return this;
        }

        public Builder<T> textures(ResourceLocation... textures) {
            return this.textures(Arrays.asList(textures));
        }

        public Builder<T> shadow(float size) {
            this.shadow = size;
            return this;
        }

        public Builder<T> skipPlayerModel() {
            this.skipPlayerModel = true;
            return this;
        }

        public WerewolfModelWrapper<T> build() {
            return new WerewolfModelWrapper<>(this.model, this.layers, this.textures, this.shadow, this.skipPlayerModel);
        }

    }
}
