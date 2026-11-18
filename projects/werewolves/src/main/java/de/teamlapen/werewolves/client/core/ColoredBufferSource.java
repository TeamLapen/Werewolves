package de.teamlapen.werewolves.client.core;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;

import java.util.SequencedMap;

/**
 * Buffer source that renders everything in a single color. Copy of the (package private) Vampirism blood vision buffer source.
 */
class ColoredBufferSource extends MultiBufferSource.BufferSource {

    private int color;

    protected ColoredBufferSource(SequencedMap<RenderType, ByteBufferBuilder> map, ByteBufferBuilder sharedBuffer) {
        super(sharedBuffer, map);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        return new ColoredGenerator(super.getBuffer(ModRenderPipelines.solidTransparencyEntity()), color);
    }

    private record ColoredGenerator(VertexConsumer consumer, int color) implements VertexConsumer {

        public ColoredGenerator {
            consumer.setColor(color);
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.consumer.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            this.consumer.setColor(this.color);
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            this.consumer.setUv(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            this.consumer.setUv1(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            this.consumer.setUv2(u, v);
            return this;
        }

        @Override
        public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            this.consumer.setNormal(normalX, normalY, normalZ);
            return this;
        }

        @Override
        public VertexConsumer setLineWidth(float width) {
            this.consumer.setLineWidth(width);
            return this;
        }
    }
}
