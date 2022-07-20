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

    public static final RegistryObject<LupusSanguinemEffect> lupus_sanguinem = EFFECTS.register("lupus_sanguinem", LupusSanguinemEffect::new);
    public static final RegistryObject<HowlingEffect> howling = EFFECTS.register("howling", HowlingEffect::new);
    public static final RegistryObject<SilverEffect> silver = EFFECTS.register("silver", SilverEffect::new);
    public static final RegistryObject<BleedingEffect> bleeding = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final RegistryObject<UnWerewolfEffect> un_werewolf = EFFECTS.register("un_werewolf", UnWerewolfEffect::new);
    public static final RegistryObject<Effect> bad_omen_werewolf = EFFECTS.register("bad_omen_werewolf", () -> new BadOmenEffect() {
        @Override
        public IFaction getFaction() {
            return WReference.WEREWOLF_FACTION;
        }
    });

    public static class V {
        public static final RegistryObject<Effect> freeze = RegistryObject.of(new ResourceLocation("vampirism", "freeze"), ForgeRegistries.POTIONS);
        public static final RegistryObject<Effect> poison = RegistryObject.of(new ResourceLocation("vampirism", "poison"), ForgeRegistries.POTIONS);
    }

    static void registerEffects(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
