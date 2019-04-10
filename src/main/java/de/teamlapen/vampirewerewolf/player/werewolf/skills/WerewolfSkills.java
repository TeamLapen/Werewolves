package de.teamlapen.vampirewerewolf.player.werewolf.skills;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.player.skills.SimpleWerewolfSkill;
import de.teamlapen.vampirewerewolf.player.werewolf.WerewolfPlayer;
import de.teamlapen.vampirewerewolf.player.werewolf.actions.WerewolfActions;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillManager;
import de.teamlapen.vampirism.api.entity.player.skills.SkillNode;
import de.teamlapen.vampirism.player.skills.ActionSkill;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill werewolf = UtilLib.getNull();
    public static final ISkill return_damage = UtilLib.getNull();
    public static final ISkill better_raw_meat = UtilLib.getNull();
    public static final ISkill werewolf_mining = UtilLib.getNull();

    //TODO Skill Icons
    public static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(VReference.WEREWOLF_FACTION.getKey(), 32, 0, false));
        registry.register(new ActionSkill<IWerewolfPlayer>(new ResourceLocation(REFERENCE.MODID, "werewolf"), WerewolfActions.werewolf_werewolf) {
            @Override
            public String getLocalizedDescription() {
                return UtilLib.translate("text.vampirewerewolf.skill.werewolve_werewolve.desc");
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "return_damage"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().healing = true;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().healing = false;
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "better_raw_meat"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().moreFoodFromRawMeat = true;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().moreFoodFromRawMeat = false;
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "werewolf_mining"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfharvest = true;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfharvest = false;
            }
        });
    }

    public static void buildSkillTree(SkillNode root) {
        ISkillManager skillManager = VampirismAPI.skillManager();
        SkillNode skill2 = skillManager.createSkillNode(root, return_damage);
        SkillNode skill3 = skillManager.createSkillNode(skill2, werewolf);
        SkillNode skill4 = skillManager.createSkillNode(skill3, better_raw_meat);
        SkillNode skill5 = skillManager.createSkillNode(skill4, werewolf_mining);
    }
}
