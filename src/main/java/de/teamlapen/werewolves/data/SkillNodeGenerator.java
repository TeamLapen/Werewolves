package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.data.recipebuilder.FinishedSkillNode;
import de.teamlapen.vampirism.data.recipebuilder.SkillNodeBuilder;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SkillNodeGenerator extends de.teamlapen.vampirism.data.SkillNodeGenerator {

    public SkillNodeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerSkillNodes(Consumer<FinishedSkillNode> consumer) {
        ResourceLocation skill1 = werewolf(this.modId("werewolf"), WerewolfSkills.HUMAN_FORM).build(consumer, this.modId("skill1"));
        ResourceLocation skill2 = werewolf(skill1, WerewolfSkills.NIGHT_VISION).build(consumer, this.modId("skill2"));

        ResourceLocation beast1 = werewolf(skill2, WerewolfSkills.RAGE).build(consumer, this.modId("beast1"));
        ResourceLocation beast2 = werewolf(beast1, WerewolfSkills.BEAST_FORM).build(consumer, this.modId("beast2"));
        ResourceLocation beast3_1 = werewolf(beast2, WerewolfSkills.THICK_FUR).build(consumer, this.modId("beast3_1"));
        ResourceLocation beast3 = werewolf(beast2, WerewolfSkills.DAMAGE, WerewolfSkills.RESISTANCE).build(consumer, this.modId("beast3"));
        ResourceLocation beast4 = werewolf(beast3, WerewolfSkills.STUN_BITE, WerewolfSkills.BLEEDING_BITE).build(consumer, this.modId("beast4"));
        ResourceLocation beast5 = werewolf(beast4, WerewolfSkills.HEALTH_AFTER_KILL).build(consumer, this.modId("beast5")); //TODO not only health bt also saturation

        ResourceLocation beast6 = werewolf(beast5, WerewolfSkills.THROAT_SEEKER).build(consumer, this.modId("beast6"));
        ResourceLocation beast7 = werewolf(beast5, WerewolfSkills.FEAR).build(consumer, this.modId("beast7"));


        ResourceLocation survival1 = werewolf(skill2, WerewolfSkills.SIXTH_SENSE, WerewolfSkills.SENSE).build(consumer, this.modId("survival1"));
        ResourceLocation survival2 = werewolf(survival1, WerewolfSkills.SURVIVAL_FORM).build(consumer, this.modId("survival2"));
        ResourceLocation survival3 = werewolf(survival2, WerewolfSkills.WOLF_PAWN, WerewolfSkills.SPEED).build(consumer, this.modId("survival3"));
        ResourceLocation survival4 = werewolf(survival3, WerewolfSkills.LEAP, WerewolfSkills.CLIMBER).build(consumer, this.modId("survival4"));
        ResourceLocation survival5 = werewolf(survival4, WerewolfSkills.MOVEMENT_TACTICS).build(consumer, this.modId("survival5"));


        ResourceLocation util1 = werewolf(skill2, WerewolfSkills.HOWLING).build(consumer, this.modId("util1"));
        ResourceLocation util2 = werewolf(util1, WerewolfSkills.WOLF_PACK).build(consumer, this.modId("util2"));
        ResourceLocation util3 = werewolf(util2, WerewolfSkills.NOT_MEAT, WerewolfSkills.WATER_LOVER).build(consumer, this.modId("util3"));

        ResourceLocation other1 = werewolf(util3, WerewolfSkills.FREE_WILL).build(consumer, this.modId("other1"));
        ResourceLocation other2 = werewolf(other1, WerewolfSkills.SILVER_BLOODED).build(consumer, this.modId("other2"));
        ResourceLocation other3 = werewolf(other2, WerewolfSkills.WEAR_ARMOR).build(consumer, this.modId("other3"));

        ResourceLocation other4 = werewolf(util3, WerewolfSkills.HEALTH_REG).build(consumer, this.modId("other4"));
        ResourceLocation other5 = werewolf(other4, WerewolfSkills.HIDE_NAME).build(consumer, this.modId("other5"));

        ResourceLocation lord0 = modId("werewolf_lord");

        werewolf(lord0, WerewolfSkills.WEREWOLF_MINION_STATS_INCREASE).build(consumer, this.modId("lord_2"));
        werewolf(lord0, WerewolfSkills.WEREWOLF_LORD_SPEED, WerewolfSkills.WEREWOLF_LORD_ATTACK_SPEED).build(consumer, this.modId("lord_3"));
        werewolf(lord0, WerewolfSkills.WEREWOLF_MINION_COLLECT).build(consumer, this.modId("lord_4"));
        werewolf(lord0, WerewolfSkills.WEREWOLF_MINION_RECOVERY).build(consumer, this.modId("lord_5"));
    }

    @SafeVarargs
    public static SkillNodeBuilder werewolf(ResourceLocation parent, Supplier<ISkill>... skills) {
        return SkillNodeBuilder.skill(parent, Arrays.stream(skills).map(Supplier::get).toArray(ISkill[]::new)).faction(WReference.WEREWOLF_FACTION);
    }

    private ResourceLocation modId(String string) {
        return new ResourceLocation(REFERENCE.MODID, string);
    }
}
