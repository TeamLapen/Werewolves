package de.teamlapen.werewolves.player;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

@SuppressWarnings("DeprecatedIsStillUsed")
public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {
    @Deprecated
    public SimpleWerewolfSkill(String id) {
        this(new ResourceLocation(REFERENCE.MODID, id), false);
    }

    @Deprecated
    public SimpleWerewolfSkill(String id, boolean desc) {
        this(new ResourceLocation(REFERENCE.MODID, id), desc);
    }

    public SimpleWerewolfSkill(ResourceLocation id) {
        this(id, false);
    }

    public SimpleWerewolfSkill(ResourceLocation id, boolean desc) {
        this.setRegistryName(id);
        if (desc) setHasDefaultDescription();
    }

    @Nonnull
    @Override
    public IPlayableFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }

    public static class ToggleWerewolfAction extends SimpleWerewolfSkill {
        private final BiConsumer<IWerewolfPlayer, Boolean> toggler;

        public ToggleWerewolfAction(String id, BiConsumer<IWerewolfPlayer, Boolean> toggler) {
            super(id);
            this.toggler = toggler;
        }

        @Override
        protected void onEnabled(IWerewolfPlayer player) {
            toggler.accept(player, true);
        }

        @Override
        protected void onDisabled(IWerewolfPlayer player) {
            toggler.accept(player, false);
        }
    }
}
