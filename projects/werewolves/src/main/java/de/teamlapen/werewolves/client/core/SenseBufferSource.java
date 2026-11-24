package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;

/**
 * Buffer source that redirects all geometry into the sense target (see {@link ModRenderPipelines#senseMask(RenderType)}),
 * drawn in a single flat color.
 */
class SenseBufferSource extends MultiBufferSource.BufferSource {

    private int color;

    SenseBufferSource(ByteBufferBuilder sharedBuffer) {
        super(sharedBuffer, new Object2ObjectLinkedOpenHashMap<>());
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        RenderType mask = ModRenderPipelines.senseMask(renderType);
        if (mask == null) {
            return NoOpConsumer.INSTANCE;
        }
        return new ColoredGenerator(super.getBuffer(mask), this.color);
    }

    /**
     * Keeps position and uv (needed for the cutout) and replaces the color, everything else is not part of the mask vertex format
     */
    private record ColoredGenerator(VertexConsumer delegate, int color) implements VertexConsumer {

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.delegate.addVertex(x, y, z).setColor(this.color);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            this.delegate.setUv(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            return this;
        }

        @Override
        public VertexConsumer setLineWidth(float width) {
            return this;
        }
    }

    private enum NoOpConsumer implements VertexConsumer {
        INSTANCE;

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            return this;
        }

        @Override
        public VertexConsumer setLineWidth(float width) {
            return this;
        }
    }
}
