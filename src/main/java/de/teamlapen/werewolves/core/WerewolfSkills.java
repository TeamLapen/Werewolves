package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.player.skills.ActionSkill;
import de.teamlapen.vampirism.player.skills.VampirismSkill;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill werewolf_form = getNull();
    public static final ISkill howling = getNull();

    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID,"werewolf_form"), WerewolfActions.werewolf_form));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "howling"), WerewolfActions.howling));
        registry.register(new SimpleWerewolfSkill("eat_flesh"){
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                ((WerewolfPlayer)player).getSpecialAttributes().eatFlesh = true;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                ((WerewolfPlayer)player).getSpecialAttributes().eatFlesh = false;
            }
        });
    }
}
