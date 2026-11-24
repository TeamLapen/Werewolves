package de.teamlapen.werewolves.client.model;

import de.teamlapen.werewolves.client.renderer.entities.WerewolfRenderState;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record WerewolfModelWrapper(@Nullable WerewolfBaseModel model,
                                   Collection<? extends RenderLayer<WerewolfRenderState, WerewolfBaseModel>> layers,
                                   List<Identifier> textures, float shadow) {

    public static class Builder {
        private WerewolfBaseModel model = null;
        private Collection<? extends RenderLayer<WerewolfRenderState, WerewolfBaseModel>> layers = Collections.emptyList();
        private List<Identifier> textures = Collections.emptyList();
        private float shadow = 0f;

        public Builder model(WerewolfBaseModel model) {
            this.model = model;
            return this;
        }

        public Builder layers(Collection<? extends RenderLayer<WerewolfRenderState, WerewolfBaseModel>> layers) {
            this.layers = layers;
            return this;
        }

        public Builder textures(List<Identifier> textures) {
            this.textures = textures;
            return this;
        }

        public Builder textures(Identifier... textures) {
            return this.textures(Arrays.asList(textures));
        }

        public Builder shadow(float size) {
            this.shadow = size;
            return this;
        }

        public WerewolfModelWrapper build() {
            return new WerewolfModelWrapper(this.model, this.layers, this.textures, this.shadow);
        }

    }
}
