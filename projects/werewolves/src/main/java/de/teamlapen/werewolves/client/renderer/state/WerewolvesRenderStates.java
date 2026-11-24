package de.teamlapen.werewolves.client.renderer.state;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.WerewolvesModClient;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.renderer.entities.WerewolfRenderState;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Avatar;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.minecraft.client.entity.ClientAvatarEntity;

import java.util.List;

/**
 * Additional render data attached to vanilla render states
 */
public class WerewolvesRenderStates {

    /**
     * Fully extracted werewolf render state of a transformed (non human like) werewolf player. If present, the player is rendered as werewolf instead.
     */
    public static final ContextKey<WerewolfRenderState> WEREWOLF_PLAYER = create("werewolf_player");
    /**
     * Texture for the ears and claws of the human form
     */
    public static final ContextKey<Identifier> HUMAN_FORM_TEXTURE = create("human_form_texture");

    private static <T> ContextKey<T> create(String id) {
        return new ContextKey<>(WIdentifier.mod(id));
    }

    public static void addRenderStateModifier(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState renderState) {
                extractWerewolfPlayer(avatar, renderState);
            }
        });
    }

    /**
     * @return The werewolf form that should be rendered for the given player. Respects the preview of the appearance screen
     */
    public static WerewolfForm getRenderForm(AbstractClientPlayer player) {
        if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen screen && screen.isRenderForm()) {
            return screen.getActiveForm();
        }
        return WerewolfPlayer.get(player).getForm();
    }

    private static List<Identifier> humanTextures;

    private static void extractWerewolfPlayer(Avatar avatar, AvatarRenderState state) {
        state.setRenderData(WEREWOLF_PLAYER, null);
        state.setRenderData(HUMAN_FORM_TEXTURE, null);
        if (!(avatar instanceof AbstractClientPlayer player) || !Helper.isWerewolf(player)) return;
        WerewolfForm form = getRenderForm(player);
        if (form == WerewolfForm.HUMAN) {
            if (humanTextures == null || humanTextures.isEmpty()) {
                humanTextures = WerewolfEarsModel.getHumanTextures();
            }
            if (!humanTextures.isEmpty()) {
                state.setRenderData(HUMAN_FORM_TEXTURE, humanTextures.get(WerewolfPlayer.get(player).getSkinType(WerewolfForm.HUMAN) % humanTextures.size()));
            }
        } else if (form.isTransformed() && !form.isHumanLike() && WerewolvesModClient.services().playerRenderer() != null) {
            state.setRenderData(WEREWOLF_PLAYER, WerewolvesModClient.services().playerRenderer().extract(player, form, state.partialTick));
        }
    }

    public static void clearCaches() {
        humanTextures = null;
    }
}
