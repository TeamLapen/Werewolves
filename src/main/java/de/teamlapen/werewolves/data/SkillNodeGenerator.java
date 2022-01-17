package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.data.recipebuilder.FinishedSkillNode;
import de.teamlapen.vampirism.data.recipebuilder.SkillNodeBuilder;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class SkillNodeGenerator extends de.teamlapen.vampirism.data.SkillNodeGenerator {

    public SkillNodeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerSkillNodes(Consumer<FinishedSkillNode> consumer) {
        ResourceLocation skill1 = werewolf(this.modId("werewolf"), WerewolfSkills.human_form).build(consumer, this.modId("skill1"));
        ResourceLocation skill2 = werewolf(skill1, WerewolfSkills.night_vision).build(consumer, this.modId("skill2"));

        ResourceLocation beast1 = werewolf(skill2, WerewolfSkills.rage).build(consumer, this.modId("beast1"));
        ResourceLocation beast2 = werewolf(beast1, WerewolfSkills.beast_form).build(consumer, this.modId("beast2"));
        ResourceLocation beast3_1 = werewolf(beast2, WerewolfSkills.thick_fur).build(consumer, this.modId("beast3_1"));
        ResourceLocation beast3 = werewolf(beast2, WerewolfSkills.damage, WerewolfSkills.resistance).build(consumer, this.modId("beast3"));
        ResourceLocation beast4 = werewolf(beast3, WerewolfSkills.stun_bite, WerewolfSkills.bleeding_bite).build(consumer, this.modId("beast4"));
        ResourceLocation beast5 = werewolf(beast4, WerewolfSkills.health_after_kill).build(consumer, this.modId("beast5")); //TODO not only health bt also saturation

        ResourceLocation beast6 = werewolf(beast5, WerewolfSkills.throat_seeker).build(consumer, this.modId("beast6"));
        ResourceLocation beast7 = werewolf(beast5, WerewolfSkills.fear).build(consumer, this.modId("beast7"));


        ResourceLocation survival1 = werewolf(skill2, WerewolfSkills.sixth_sense, WerewolfSkills.sense).build(consumer, this.modId("survival1"));
        ResourceLocation survival2 = werewolf(survival1, WerewolfSkills.survival_form).build(consumer, this.modId("survival2"));
        ResourceLocation survival3 = werewolf(survival2, WerewolfSkills.wolf_pawn, WerewolfSkills.speed).build(consumer, this.modId("survival3"));
        ResourceLocation survival4 = werewolf(survival3, WerewolfSkills.leap, WerewolfSkills.climber).build(consumer, this.modId("survival4"));
        ResourceLocation survival5 = werewolf(survival4, WerewolfSkills.movement_tactics).build(consumer, this.modId("survival5"));


        ResourceLocation util1 = werewolf(skill2, WerewolfSkills.howling).build(consumer, this.modId("util1"));
        ResourceLocation util2 = werewolf(util1, WerewolfSkills.wolf_pack).build(consumer, this.modId("util2"));
        ResourceLocation util3 = werewolf(util2, WerewolfSkills.not_meat, WerewolfSkills.water_lover).build(consumer, this.modId("util3"));

        ResourceLocation other1 = werewolf(util3, WerewolfSkills.free_will).build(consumer, this.modId("other1"));
        ResourceLocation other2 = werewolf(other1, WerewolfSkills.silver_blooded).build(consumer, this.modId("other2"));
        ResourceLocation other3 = werewolf(other2, WerewolfSkills.wear_armor).build(consumer, this.modId("other3"));

        ResourceLocation other4 = werewolf(util3, WerewolfSkills.health_reg).build(consumer, this.modId("other4"));
        ResourceLocation other5 = werewolf(other4, WerewolfSkills.hide_name).build(consumer, this.modId("other5"));

    }

    public static SkillNodeBuilder werewolf(ResourceLocation parent, ISkill... skills) {
        return SkillNodeBuilder.skill(parent, skills).faction(WReference.WEREWOLF_FACTION);
    }

    private ResourceLocation modId(String string) {
        return new ResourceLocation(REFERENCE.MODID, string);
    }
}
