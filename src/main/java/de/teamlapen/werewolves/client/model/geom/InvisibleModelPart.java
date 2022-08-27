package de.teamlapen.werewolves.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.mixin.client.ModelPartAccessor;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

public class InvisibleModelPart extends ModelPart {

    public InvisibleModelPart(@NotNull ModelPart part) {
        super(Collections.emptyList(), ((ModelPartAccessor) part).getChildren());
    }

    @Override
    public void render(PoseStack p_104307_, VertexConsumer p_104308_, int p_104309_, int p_104310_, float p_104311_, float p_104312_, float p_104313_, float p_104314_) {
        if (this.visible) {
            if (!this.children().isEmpty()) {
                p_104307_.pushPose();
                this.translateAndRotate(p_104307_);

                for (ModelPart modelpart : this.children().values()) {
                    modelpart.render(p_104307_, p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);
                }

                p_104307_.popPose();
            }
        }
    }

    @Override
    public void visit(PoseStack p_171313_, ModelPart.Visitor p_171314_, String p_171315_) {
        if (!this.children().isEmpty()) {
            p_171313_.pushPose();
            this.translateAndRotate(p_171313_);

            String s = p_171315_ + "/";
            this.children().forEach((p_171320_, p_171321_) -> {
                p_171321_.visit(p_171313_, p_171314_, s + p_171320_);
            });
            p_171313_.popPose();
        }
    }

    @Override
    public @NotNull Stream<ModelPart> getAllParts() {
        return this.children().values().stream().flatMap(ModelPart::getAllParts);
    }

    private Map<String, ModelPart> children() {
        return ((ModelPartAccessor) this).getChildren();
    }
}
