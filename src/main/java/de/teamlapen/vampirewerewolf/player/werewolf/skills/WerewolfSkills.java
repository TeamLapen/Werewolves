package de.teamlapen.vampirewerewolf.player.werewolf.skills;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.player.skills.SimpleWerewolfSkill;
import de.teamlapen.vampirewerewolf.player.werewolf.actions.WerewolfActions;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirewerewolf.util.VReference;
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

    public static final ISkill werewolf_werewolf = UtilLib.getNull();

    public static void registerWerewolfSkills(IForgeRegistry<ISkill> registry) {
        registry.register(new SimpleWerewolfSkill(VReference.WEREWOLF_FACTION.getKey(), 32, 0, false));
        registry.register(new ActionSkill<IWerewolfPlayer>(new ResourceLocation("vampirewerewolf", "werewolf_werewolf"), WerewolfActions.werewolf_werewolf) {
            @Override
            public String getLocalizedDescription() {
                return UtilLib.translate("text.vampirewerewolf.skill.werewolve_werewolve.desc");
            }
        });
    }

    public static void buildSkillTree(SkillNode root) {
        ISkillManager skillManager = VampirismAPI.skillManager();
        SkillNode skill2 = skillManager.createSkillNode(root, werewolf_werewolf);
    }
}
