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

    public static final RegistryObject<LupusSanguinemEffect> lupus_sanguinem = EFFECTS.register("lupus_sanguinem", LupusSanguinemEffect::new);
    public static final RegistryObject<HowlingEffect> howling = EFFECTS.register("howling", HowlingEffect::new);
    public static final RegistryObject<SilverEffect> silver = EFFECTS.register("silver", SilverEffect::new);
    public static final RegistryObject<BleedingEffect> bleeding = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<UnWerewolfEffect> un_werewolf = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final RegistryObject<MobEffect> bad_omen_werewolf = EFFECTS.register("bad_omen_werewolf", () -> new BadOmenEffect(REFERENCE.MODID, REFERENCE.WEREWOLF_PLAYER_KEY) {
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });

    public static class V {
        public static final RegistryObject<MobEffect> freeze = RegistryObject.create(new ResourceLocation("vampirism", "freeze"), ForgeRegistries.MOB_EFFECTS);
        public static final RegistryObject<MobEffect> poison = RegistryObject.create(new ResourceLocation("vampirism", "poison"), ForgeRegistries.MOB_EFFECTS);

        static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
