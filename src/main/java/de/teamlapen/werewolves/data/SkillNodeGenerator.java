package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.data.recipebuilder.FinishedSkillNode;
import de.teamlapen.vampirism.data.recipebuilder.SkillNodeBuilder;
import de.teamlapen.vampirism.entity.player.lord.skills.LordSkills;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class SkillNodeGenerator extends de.teamlapen.vampirism.data.SkillNodeGenerator {

    public SkillNodeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerSkillNodes(Consumer<FinishedSkillNode> consumer) {
        ResourceLocation skill1 = werewolf(this.modId("werewolf"), ModSkills.HUMAN_FORM.get()).build(consumer, this.modId("skill1"));
        ResourceLocation skill2 = werewolf(skill1, ModSkills.NIGHT_VISION.get()).build(consumer, this.modId("skill2"));

        ResourceLocation beast1 = werewolf(skill2, ModSkills.RAGE.get()).build(consumer, this.modId("beast1"));
        ResourceLocation beast2 = werewolf(beast1, ModSkills.BEAST_FORM.get()).build(consumer, this.modId("beast2"));
        ResourceLocation beast3_1 = werewolf(beast2, ModSkills.THICK_FUR.get()).build(consumer, this.modId("beast3_1"));
        ResourceLocation beast3 = werewolf(beast2, ModSkills.DAMAGE.get(), ModSkills.RESISTANCE.get()).build(consumer, this.modId("beast3"));
        ResourceLocation beast4 = werewolf(beast3, ModSkills.STUN_BITE.get(), ModSkills.BLEEDING_BITE.get()).build(consumer, this.modId("beast4"));
        ResourceLocation beast5 = werewolf(beast4, ModSkills.HEALTH_AFTER_KILL.get()).build(consumer, this.modId("beast5"));

        ResourceLocation beast6 = werewolf(beast5, ModSkills.THROAT_SEEKER.get()).build(consumer, this.modId("beast6"));
        ResourceLocation beast7 = werewolf(beast5, ModSkills.FEAR.get()).build(consumer, this.modId("beast7"));


        ResourceLocation survival1 = werewolf(skill2, ModSkills.SIXTH_SENSE.get(), ModSkills.SENSE.get()).build(consumer, this.modId("survival1"));
        ResourceLocation survival2 = werewolf(survival1, ModSkills.SURVIVAL_FORM.get()).build(consumer, this.modId("survival2"));
        ResourceLocation survival3 = werewolf(survival2, ModSkills.WOLF_PAWN.get(), ModSkills.SPEED.get()).build(consumer, this.modId("survival3"));
        ResourceLocation survival4 = werewolf(survival3, ModSkills.LEAP.get(), ModSkills.CLIMBER.get()).build(consumer, this.modId("survival4"));
        ResourceLocation survival5 = werewolf(survival4, ModSkills.MOVEMENT_TACTICS.get()).build(consumer, this.modId("survival5"));


        ResourceLocation util1 = werewolf(skill2, ModSkills.HOWLING.get()).build(consumer, this.modId("util1"));
        ResourceLocation util2 = werewolf(util1, ModSkills.WOLF_PACK.get()).build(consumer, this.modId("util2"));
        ResourceLocation util3 = werewolf(util2, ModSkills.NOT_MEAT.get(), ModSkills.WATER_LOVER.get()).build(consumer, this.modId("util3"));

        ResourceLocation other1 = werewolf(util3, ModSkills.FREE_WILL.get()).build(consumer, this.modId("other1"));
        ResourceLocation other2 = werewolf(other1, ModSkills.SILVER_BLOODED.get()).build(consumer, this.modId("other2"));
        ResourceLocation other3 = werewolf(other2, ModSkills.WEAR_ARMOR.get()).build(consumer, this.modId("other3"));

        ResourceLocation other4 = werewolf(util3, ModSkills.HEALTH_REG.get()).build(consumer, this.modId("other4"));
        ResourceLocation other5 = werewolf(other4, ModSkills.HIDE_NAME.get()).build(consumer, this.modId("other5"));

        ResourceLocation lord2 = werewolf(modId("werewolf_lord"), ModSkills.MINION_STATS_INCREASE.get()).build(consumer, this.modId("lord2"));
        ResourceLocation lord3 = werewolf(modId("werewolf_lord"), LordSkills.LORD_SPEED.get(), LordSkills.LORD_ATTACK_SPEED.get()).build(consumer, this.modId("lord3"));
        ResourceLocation lord4 = werewolf(modId("werewolf_lord"), ModSkills.MINION_COLLECT.get()).build(consumer, this.modId("lord4"));
        ResourceLocation lord5 = werewolf(modId("werewolf_lord"), LordSkills.MINION_RECOVERY.get()).build(consumer, this.modId("lord5"));

    }

    public static SkillNodeBuilder werewolf(ResourceLocation parent, ISkill<?>... skills) {
        return SkillNodeBuilder.skill(parent, skills).faction(WReference.WEREWOLF_FACTION);
    }

    private ResourceLocation modId(String string) {
        return new ResourceLocation(REFERENCE.MODID, string);
    }
}
