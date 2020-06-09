package de.teamlapen.werewolves.player;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.IWerewolf;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SimpleWerewolfSkill extends VampirismSkill<IWerewolfPlayer> {

    private static final Supplier<IPlayableFaction<IWerewolfPlayer>> WEREWOLF_FACTION = () -> WReference.WEREWOLF_FACTION;

    private final Supplier<IPlayableFaction<?>> faction;

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
        super(WEREWOLF_FACTION);
        this.faction = ()-> WReference.WEREWOLF_FACTION;
        this.setRegistryName(id);
        if (desc) setHasDefaultDescription();
    }

    @Nonnull
    @Override
    public IPlayableFaction<?> getFaction() {
        return faction.get();
    }
}
