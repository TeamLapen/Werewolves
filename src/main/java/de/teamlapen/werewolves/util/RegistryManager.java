package de.teamlapen.werewolves.util;

import com.google.common.eventbus.Subscribe;
import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.core.WerewolfSkills;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

public class RegistryManager implements IInitListener {
    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {

    }

    @SubscribeEvent
    public void onRegisterSkills(RegistryEvent.Register<ISkill> event) {
        WerewolfSkills.registerWerewolfSkills(event.getRegistry());
    }
}
