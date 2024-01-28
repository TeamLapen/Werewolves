package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.data.recipebuilder.FinishedSkillNode;
import de.teamlapen.vampirism.data.recipebuilder.SkillNodeBuilder;
import de.teamlapen.vampirism.entity.player.lord.skills.LordSkills;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SkillNodeGenerator extends de.teamlapen.vampirism.data.provider.SkillNodeProvider {

    public SkillNodeGenerator(PackOutput packOutput) {
        super(packOutput, REFERENCE.MODID);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void registerSkillNodes(@NotNull Consumer<FinishedSkillNode> consumer) {
        ResourceLocation skill1 = werewolf(this.modId("werewolf"), ModSkills.HUMAN_FORM).build(consumer, this.modId("skill1"));
        ResourceLocation skill2 = werewolf(skill1, ModSkills.NIGHT_VISION).build(consumer, this.modId("skill2"));

        ResourceLocation beast1 = werewolf(skill2, ModSkills.RAGE).build(consumer, this.modId("beast1"));
        ResourceLocation beast2 = werewolf(beast1, ModSkills.BEAST_FORM).build(consumer, this.modId("beast2"));
        ResourceLocation beast3_1 = werewolf(beast2, ModSkills.DAMAGE).build(consumer, this.modId("beast3_1"));
        ResourceLocation beast3 = werewolf(beast2, ModSkills.THICK_FUR, ModSkills.RESISTANCE).build(consumer, this.modId("beast3"));
        ResourceLocation beast4 = werewolf(beast3, ModSkills.STUN_BITE, ModSkills.BLEEDING_BITE).build(consumer, this.modId("beast4"));
        ResourceLocation beast5 = werewolf(beast4, ModSkills.HEALTH_AFTER_KILL).build(consumer, this.modId("beast5"));

        ResourceLocation beast6 = werewolf(beast5, ModSkills.BEAST_RAGE).build(consumer, this.modId("beast6"));
        ResourceLocation beast7 = werewolf(beast5, ModSkills.FEAR).build(consumer, this.modId("beast7"));


        ResourceLocation survival1 = werewolf(skill2, ModSkills.SIXTH_SENSE, ModSkills.SENSE).build(consumer, this.modId("survival1"));
        ResourceLocation survival2 = werewolf(survival1, ModSkills.SURVIVAL_FORM).build(consumer, this.modId("survival2"));
        ResourceLocation survival3 = werewolf(survival2, ModSkills.WOLF_PAWN, ModSkills.SPEED).build(consumer, this.modId("survival3"));
        ResourceLocation survival4 = werewolf(survival3, ModSkills.LEAP, ModSkills.CLIMBER).build(consumer, this.modId("survival4"));
        ResourceLocation survival5 = werewolf(survival4, ModSkills.MOVEMENT_TACTICS).build(consumer, this.modId("survival5"));

        ResourceLocation util1 = werewolf(skill2, ModSkills.HOWLING).build(consumer, this.modId("util1"));
        ResourceLocation util2 = werewolf(util1, ModSkills.WOLF_PACK, ModSkills.DIGGER).build(consumer, this.modId("util2"));
        ResourceLocation util3 = werewolf(util2, ModSkills.NOT_MEAT, ModSkills.HEALTH_REG).build(consumer, this.modId("util3"));

        ResourceLocation other1 = werewolf(util3, ModSkills.FREE_WILL).build(consumer, this.modId("other1"));
        ResourceLocation other2 = werewolf(other1, ModSkills.SILVER_BLOODED).build(consumer, this.modId("other2"));
        ResourceLocation other3 = werewolf(other2, ModSkills.WEAR_ARMOR).build(consumer, this.modId("other3"));

        ResourceLocation other4 = werewolf(util3, ModSkills.WATER_LOVER).build(consumer, this.modId("other4"));
        ResourceLocation other5 = werewolf(other4, ModSkills.HIDE_NAME).build(consumer, this.modId("other5"));
        ResourceLocation other6 = werewolf(other4, ModSkills.ENHANCED_DIGGER).build(consumer, this.modId("other6"));

        ResourceLocation lord0 = modId("werewolf_lord");

        werewolf(lord0, ModSkills.MINION_STATS_INCREASE).build(consumer, this.modId("lord_2"));
        werewolf(lord0, LordSkills.LORD_SPEED, LordSkills.LORD_ATTACK_SPEED).build(consumer, this.modId("lord_3"));
        werewolf(lord0, ModSkills.MINION_COLLECT).build(consumer, this.modId("lord_4"));
        werewolf(lord0, LordSkills.MINION_RECOVERY).build(consumer, this.modId("lord_5"));
    }

    @SuppressWarnings("unchecked")
    public static SkillNodeBuilder werewolf(ResourceLocation parent, Supplier<? extends ISkill<?>>... skills) {
        return SkillNodeBuilder.skill(parent, Arrays.stream(skills).map(Supplier::get).toArray(ISkill[]::new)).faction(WReference.WEREWOLF_FACTION);
    }
}
