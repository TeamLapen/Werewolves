package de.teamlapen.werewolves.player.werewolf.skills;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillManager;
import de.teamlapen.vampirism.api.entity.player.skills.SkillNode;
import de.teamlapen.vampirism.player.skills.ActionSkill;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.player.skills.SimpleWerewolfSkill;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class WerewolfSkills {

    public static final ISkill werewolf = getNull();
    public static final ISkill werewolf_return_damage = getNull();
    public static final ISkill werewolf_better_raw_meat = getNull();
    public static final ISkill werewolf_mining = getNull();
    public static final ISkill werewolf2 = getNull();
    public static final ISkill werewolf3 = getNull();
    public static final ISkill animal_hunter = getNull();

    // TODO Skill Icons
    public static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(WReference.WEREWOLF_FACTION.getKey(), 32, 0, false));
        registry.register(new ActionSkill<IWerewolfPlayer>(new ResourceLocation(REFERENCE.MODID, "werewolf"), WerewolfActions.werewolf_werewolf) {
            @Override
            public String getLocalizedDescription() {
                return UtilLib.translate("text.werewolves.skill.werewolve_werewolve.desc");
            }

            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel++;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel--;
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "werewolf_return_damage"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().healing = true;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().healing = false;
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "werewolf_better_raw_meat"), 0, 0, true) {
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
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "werewolf2"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel++;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel--;
            }
        });
        registry.register(new SimpleWerewolfSkill(new ResourceLocation(REFERENCE.MODID, "werewolf3"), 0, 0, true) {
            @Override
            protected void onEnabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel++;
            }

            @Override
            protected void onDisabled(IWerewolfPlayer player) {
                WerewolfPlayer.get(player.getRepresentingPlayer()).getSpecialAttributes().werewolfLevel--;
            }
        });
        registry.register(new ActionSkill<IWerewolfPlayer>(new ResourceLocation(REFERENCE.MODID, "animal_hunter"), WerewolfActions.animal_hunter) {
            @Override
            public String getLocalizedDescription() {
                return UtilLib.translate("text.werewolves.skill.animal_hunter.desc");
            }
        });
    }

    public static void buildSkillTree(SkillNode root) {
        ISkillManager skillManager = VampirismAPI.skillManager();
        SkillNode skill1 = skillManager.createSkillNode(root, werewolf);

        SkillNode skill2 = skillManager.createSkillNode(skill1, werewolf_better_raw_meat);
        SkillNode skill3 = skillManager.createSkillNode(skill1, werewolf2);
        SkillNode skill4 = skillManager.createSkillNode(skill1, animal_hunter);

        SkillNode skill5 = skillManager.createSkillNode(skill3, werewolf_return_damage);
        SkillNode skill6 = skillManager.createSkillNode(skill3, werewolf3);
        SkillNode skill7 = skillManager.createSkillNode(skill3, werewolf_mining);
    }
}
