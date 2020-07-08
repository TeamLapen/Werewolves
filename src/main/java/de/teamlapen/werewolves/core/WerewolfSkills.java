package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.SimpleWerewolfSkill;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill werewolf_form = getNull();
    public static final ISkill howling = getNull();
    public static final ISkill werewolf_form_more_time = getNull();
    public static final ISkill werewolf_form_time_regain = getNull();

    @SuppressWarnings("deprecation")
    static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(REFERENCE.WEREWOLF_PLAYER_KEY));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "werewolf_form"), WerewolfActions.werewolf_form));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "howling"), WerewolfActions.howling));
        registry.register(new ActionSkill<>(new ResourceLocation(REFERENCE.MODID, "bite"), WerewolfActions.bite));
        registry.register(new SimpleWerewolfSkill("werewolf_form_more_time"));
        registry.register(new SimpleWerewolfSkill("werewolf_form_time_regain") {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                player.getRepresentingPlayer().getAttribute(WReference.werewolfFormTimeGain).applyModifier(new AttributeModifier(WUtils.WEREWOLF_FORM_TIME, "werewolf_form_regain_skill", WerewolvesConfig.BALANCE.SKILLS.TIME_REGAIN.increase.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                player.getRepresentingPlayer().getAttribute(WReference.werewolfFormTimeGain).removeModifier(WUtils.WEREWOLF_FORM_TIME);
            }
        });
    }
}
