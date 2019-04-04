package de.teamlapen.vampirewerewolf.core;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.vampirewerewolf.player.werewolf.actions.WerewolfActions;
import de.teamlapen.vampirewerewolf.player.werewolf.skills.WerewolfSkills;
import de.teamlapen.vampirewerewolf.util.VReference;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.SkillEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolderRegistry;

public class RegistryManager implements IInitListener {

    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
        switch (step) {
            case INIT:
                break;
            case PRE_INIT:
                break;
            case POST_INIT:
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public void onRegisterActions(RegistryEvent.Register<IAction> event) {
        WerewolfActions.registerDefaultActions(event.getRegistry());
        ObjectHolderRegistry.INSTANCE.applyObjectHolders();
    }

    @SubscribeEvent
    public void onRegisterSkills(RegistryEvent.Register<ISkill> event) {
        WerewolfSkills.registerWerewolfSkills(event.getRegistry());
    }

    @SubscribeEvent
    public void onSkillNodeCreated(SkillEvent.CreatedNode event) {
        if (event.getNode().isRoot()) {
            if (event.getNode().getFaction().equals(VReference.WEREWOLF_FACTION)) {
                WerewolfSkills.buildSkillTree(event.getNode());
            }
        }
    }
}
