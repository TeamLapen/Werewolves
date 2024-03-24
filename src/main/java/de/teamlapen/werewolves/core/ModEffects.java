package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.effects.BadOmenEffect;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, REFERENCE.MODID);

    public static final DeferredHolder<MobEffect, LupusSanguinemEffect> LUPUS_SANGUINEM = EFFECTS.register("lupus_sanguinem", LupusSanguinemEffect::new);
    public static final DeferredHolder<MobEffect, HowlingEffect> HOWLING = EFFECTS.register("howling", HowlingEffect::new);
    public static final DeferredHolder<MobEffect, WerewolfWeakeningEffect> SILVER = EFFECTS.register("silver", SilverEffect::new);
    public static final DeferredHolder<MobEffect, WerewolfWeakeningEffect> WOLFSBANE = EFFECTS.register("wolfsbane", WolfsbaneEffect::new);
    public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final DeferredHolder<MobEffect, UnWerewolfEffect> UN_WEREWOLF = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> BAD_OMEN_WEREWOLF = EFFECTS.register("bad_omen_werewolf", () -> new BadOmenEffect() {
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });
    public static final DeferredHolder<MobEffect, MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);

    public static class V {
        public static final DeferredHolder<MobEffect, MobEffect> POISON = DeferredHolder.create(ResourceKey.create(Registries.MOB_EFFECT, new ResourceLocation("vampirism", "poison")));

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
