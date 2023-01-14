package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.effects.BadOmenEffect;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, REFERENCE.MODID);

    public static final RegistryObject<LupusSanguinemEffect> LUPUS_SANGUINEM = EFFECTS.register("lupus_sanguinem", LupusSanguinemEffect::new);
    public static final RegistryObject<HowlingEffect> HOWLING = EFFECTS.register("howling", HowlingEffect::new);
    public static final RegistryObject<WerewolfWeakeningEffect> SILVER = EFFECTS.register("silver", () -> new WerewolfWeakeningEffect(0xC0C0C0));
    public static final RegistryObject<WerewolfWeakeningEffect> WOLFSBANE = EFFECTS.register("wolfsbane", () -> new WerewolfWeakeningEffect(0xC0C0C0));
    public static final RegistryObject<BleedingEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<UnWerewolfEffect> UN_WEREWOLF = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final RegistryObject<MobEffect> BAD_OMEN_WEREWOLF = EFFECTS.register("bad_omen_werewolf", () -> new BadOmenEffect() {
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });

    public static class V {
        public static final RegistryObject<MobEffect> FREEZE = RegistryObject.create(new ResourceLocation("vampirism", "freeze"), ForgeRegistries.Keys.MOB_EFFECTS, REFERENCE.MODID);
        public static final RegistryObject<MobEffect> POISON = RegistryObject.create(new ResourceLocation("vampirism", "poison"), ForgeRegistries.Keys.MOB_EFFECTS, REFERENCE.MODID);

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
