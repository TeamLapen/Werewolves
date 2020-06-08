package de.teamlapen.werewolves.player;

import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;

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
        super(WReference.WEREWOLF_FACTION);
        this.setRegistryName(id);
        if (desc) setHasDefaultDescription();
    }
}
