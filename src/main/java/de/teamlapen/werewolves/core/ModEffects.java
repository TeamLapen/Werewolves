package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.effects.BadOmenEffect;
import de.teamlapen.werewolves.effects.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, REFERENCE.MODID);

    public static final RegistryObject<LupusSanguinemEffect> LUPUS_SANGUINEM = EFFECTS.register("lupus_sanguinem", LupusSanguinemEffect::new);
    public static final RegistryObject<HowlingEffect> HOWLING = EFFECTS.register("howling", HowlingEffect::new);
    public static final RegistryObject<SilverEffect> SILVER = EFFECTS.register("silver", SilverEffect::new);
    public static final RegistryObject<BleedingEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<UnWerewolfEffect> UN_WEREWOLF = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final RegistryObject<Effect> BAD_OMEN_WEREWOLF = EFFECTS.register("bad_omen_werewolf", () -> new BadOmenEffect() {
        @Override
        public IFaction<?> getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });


    public static class V {
        public static final RegistryObject<Effect> FREEZE = RegistryObject.of(new ResourceLocation("vampirism", "freeze"), ForgeRegistries.POTIONS);
        public static final RegistryObject<Effect> POISON = RegistryObject.of(new ResourceLocation("vampirism", "poison"), ForgeRegistries.POTIONS);

        private static void init() {}
    }

    static void registerEffects(IEventBus bus) {
        EFFECTS.register(bus);
    }

    static {
        V.init();
    }
}
