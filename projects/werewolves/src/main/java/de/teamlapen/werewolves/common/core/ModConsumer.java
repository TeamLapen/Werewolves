package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.world.entities.player.FactionPlayerConsumer;
import de.teamlapen.vampirism.common.world.entity.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModConsumer {

    public static final DeferredRegister<FactionPlayerConsumer> CONSUMER = DeferredRegister.create(FactionRegistries.Keys.FACTION_PLAYER_CONSUMER, REFERENCE.MODID);

    public static final DeferredHolder<FactionPlayerConsumer, FactionPlayerConsumer> ENABLE_CLIMBER = CONSUMER.register("enable_climber", () -> factionPlayer ->  {
        if (factionPlayer instanceof WerewolfPlayer werewolf) {
            werewolf.getSkillProperties().climber = true;
        }
    });
    // SurvivalWerewolfFormAction::climberSkillEnabled
    public static final DeferredHolder<FactionPlayerConsumer, FactionPlayerConsumer> DISABLE_CLIMBER = CONSUMER.register("disable_climber", () -> factionPlayer ->  {
        if (factionPlayer instanceof WerewolfPlayer werewolf) {
            werewolf.getSkillProperties().climber = false;
        }
    });
    public static final DeferredHolder<FactionPlayerConsumer, FactionPlayerConsumer> ENABLE_NIGHT_VISION = CONSUMER.register("enable_night_vision", () -> factionPlayer ->  {
        if (factionPlayer instanceof WerewolfPlayer werewolf) {
            werewolf.getSpecialAttributes().night_vision = true;
        }
    });
    // SurvivalWerewolfFormAction::climberSkillEnabled
    public static final DeferredHolder<FactionPlayerConsumer, FactionPlayerConsumer> DISABLE_NIGHT_VISION = CONSUMER.register("disable_night_vision", () -> factionPlayer ->  {
        if (factionPlayer instanceof WerewolfPlayer werewolf) {
            werewolf.getSpecialAttributes().night_vision = false;
        }
    });

    static void register(IEventBus bus) {
        CONSUMER.register(bus);
    }

}
