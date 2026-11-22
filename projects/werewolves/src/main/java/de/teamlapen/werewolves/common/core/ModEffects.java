package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.common.world.effects.FactionBadOmenMobEffect;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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
    public static final DeferredHolder<MobEffect, UnWerewolfEffect> UN_WEREWOLF = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> BAD_OMEN_WEREWOLF = EFFECTS.register("bad_omen_werewolf", () -> new FactionBadOmenMobEffect(ModFactions.WEREWOLF));
    public static final DeferredHolder<MobEffect, MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
    public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

    private static final ResourceKey<MobEffect> VAMPIRISM_BLEEDING = ResourceKey.create(Registries.MOB_EFFECT, WIdentifier.loc(REFERENCE.VMODID, "bleeding"));

    /**
     * @return Vampirism's bleeding effect if Vampirism is installed, otherwise the werewolves one
     */
    public static Holder<MobEffect> bleeding() {
        return BuiltInRegistries.MOB_EFFECT.get(VAMPIRISM_BLEEDING).<Holder<MobEffect>>map(holder -> holder).orElse(BLEEDING);
    }

    static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
