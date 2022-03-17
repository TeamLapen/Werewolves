package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.effects.BadOmenEffect;
import de.teamlapen.werewolves.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEffects {

    public static final LupusSanguinemEffect lupus_sanguinem = getNull();
    public static final HowlingEffect howling = getNull();
    public static final SilverEffect silver = getNull();
    public static final BleedingEffect bleeding = getNull();
    public static final UnWerewolfEffect un_werewolf = getNull();
    public static final MobEffect bad_omen_werewolf = getNull();

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public static final MobEffect freeze = getNull();
        public static final MobEffect poison = getNull();
    }

    static void registerEffects(IForgeRegistry<MobEffect> registry) {
        registry.register(new LupusSanguinemEffect());
        registry.register(new HowlingEffect());
        registry.register(new SilverEffect());
        registry.register(new BleedingEffect());
        registry.register(new UnWerewolfEffect());
        registry.register(new BadOmenEffect(REFERENCE.MODID, REFERENCE.WEREWOLF_PLAYER_KEY) {
            @Override
            public IFaction<?> getFaction() {
                return WReference.WEREWOLF_FACTION;
            }
        });
    }
}
