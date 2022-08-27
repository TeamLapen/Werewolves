package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record WerewolfModelWrapper<T extends LivingEntity>(WerewolfBaseModel<T> model,
                                                           Collection<RenderLayer<T, WerewolfBaseModel<T>>> layers,
                                                           List<ResourceLocation> textures, float shadow,
                                                           boolean skipPlayerModel) {

    public static class Builder<T extends LivingEntity> {
        private @Nullable WerewolfBaseModel<T> model = null;
        private @NotNull Collection<RenderLayer<T, WerewolfBaseModel<T>>> layers = Collections.emptyList();
        private @NotNull List<ResourceLocation> textures = Collections.emptyList();
        private float shadow = 0f;
        private boolean skipPlayerModel = false;

        public @NotNull Builder<T> model(WerewolfBaseModel<T> model) {
            this.model = model;
            return this;
        }

        public @NotNull Builder<T> layers(@NotNull Collection<RenderLayer<T, WerewolfBaseModel<T>>> function) {
            this.layers = function;
            return this;
        }

        public @NotNull Builder<T> textures(@NotNull List<ResourceLocation> textures) {
            this.textures = textures;
            return this;
        }

        public Builder<T> textures(ResourceLocation... textures) {
            return this.textures(Arrays.asList(textures));
        }

        public @NotNull Builder<T> shadow(float size) {
            this.shadow = size;
            return this;
        }

        public @NotNull Builder<T> skipPlayerModel() {
            this.skipPlayerModel = true;
            return this;
        }

        public @NotNull WerewolfModelWrapper<T> build() {
            return new WerewolfModelWrapper<>(this.model, this.layers, this.textures, this.shadow, this.skipPlayerModel);
        }

    }
}
