package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.advancements.critereon.FactionCriterionTrigger;
import de.teamlapen.vampirism.advancements.critereon.FactionSubPredicate;
import de.teamlapen.vampirism.advancements.critereon.VampireActionCriterionTrigger;
import de.teamlapen.vampirism.core.ModAdvancements;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.data.provider.TagProvider;
import de.teamlapen.werewolves.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeAdvancementProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AdvancementProvider extends net.neoforged.neoforge.common.data.AdvancementProvider {


    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */
    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    private static class ModAdvancementGenerator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
            var teen_wolf = Advancement.Builder.advancement()
                    .display(ModItems.DARK_PELT_HELMET.get(), Component.translatable("advancements.werewolves"), Component.translatable("advancements.werewolves.teen_wolf.desc"), WResourceLocation.mod("textures/block/stripped_jacaranda_log.png"), AdvancementType.TASK, true, false, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("teen_wolf"), existingFileHelper);

            var bad_moon_rising = Advancement.Builder.advancement()
                    .parent(teen_wolf)
                    .display(ModItems.WEREWOLF_MINION_CHARM.get(), Component.translatable("advancements.werewolves.bad_moon_rising"), Component.translatable("advancements.werewolves.bad_moon_rising.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.TRANSFORM_FULL_MOON))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("bad_moon_rising"), existingFileHelper);

            var howling_moon = Advancement.Builder.advancement()
                    .parent(bad_moon_rising)
                    .display(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get(), Component.translatable("advancements.werewolves.howling_moon"), Component.translatable("advancements.werewolves.howling_moon.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.HOWLING))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("howling_moon"), existingFileHelper);

            var dont_touch_that = Advancement.Builder.advancement()
                    .parent(howling_moon)
                    .display(ModItems.SILVER_INGOT.get(), Component.translatable("advancements.werewolves.dont_touch_that"), Component.translatable("advancements.werewolves.dont_touch_that.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.TOUCH_SILVER))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("dont_touch_that"), existingFileHelper);

            var little_pigs = Advancement.Builder.advancement()
                    .parent(dont_touch_that)
                    .display(Items.PORKCHOP, Component.translatable("advancements.werewolves.little_pigs"), Component.translatable("advancements.werewolves.little_pigs.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(EntityType.HOGLIN))))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("little_pigs"), existingFileHelper);

            var little_red_riding_hood = Advancement.Builder.advancement()
                    .parent(little_pigs)
                    .display(de.teamlapen.vampirism.core.ModItems.HUNTER_AXE_NORMAL.get(), Component.translatable("advancements.werewolves.little_red_riding_hood"), Component.translatable("advancements.werewolves.little_red_riding_hood.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("action", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(ModTags.Entities.HUNTER))))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("little_red_riding_hood"), existingFileHelper);

            var unrivaled_predator = Advancement.Builder.advancement()
                    .parent(little_red_riding_hood)
                    .display(ModItems.CRACKED_BONE.get(), Component.translatable("advancements.werewolves.unrivaled_predator"), Component.translatable("advancements.werewolves.unrivaled_predator.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, REFERENCE.HIGHEST_WEREWOLF_LEVEL))
                    .save(saver, WResourceLocation.mod("unrivaled_predator"), existingFileHelper);

            var lord_of_the_hunt = Advancement.Builder.advancement()
                    .parent(unrivaled_predator)
                    .display(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get(), Component.translatable("advancements.werewolves.lord_of_the_hunt"), Component.translatable("advancements.werewolves.lord_of_the_hunt.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.lord(WReference.WEREWOLF_FACTION, REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL))
                    .save(saver, WResourceLocation.mod("lord_of_the_hunt"), existingFileHelper);


            var beast_of_gevauden = Advancement.Builder.advancement()
                    .parent(little_red_riding_hood)
                    .display(ModItems.PELT_HELMET.get(), Component.translatable("advancements.werewolves.beast_of_gevauden"), Component.translatable("advancements.werewolves.beast_of_gevauden.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .addCriterion("village", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.VILLAGE_CONQUERED))
                    .save(saver, WResourceLocation.mod("beast_of_gevauden"), existingFileHelper);

            var insatiable_beast = addAllMeats(Advancement.Builder.advancement()
                    .parent(beast_of_gevauden)
                    .display(ModItems.LIVER.get(), Component.translatable("advancements.werewolves.insatiable_beast"), Component.translatable("advancements.werewolves.insatiable_beast.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1)))
                    .save(saver, WResourceLocation.mod("insatiable_beast"), existingFileHelper);

            var standing_here_i_realize = Advancement.Builder.advancement()
                    .parent(insatiable_beast)
                    .display(Items.NETHERITE_BLOCK, Component.translatable("advancements.werewolves.standing_here_i_realize"), Component.translatable("advancements.werewolves.standing_here_i_realize.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .addCriterion("armor", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.ARMOR_PARTIAL))
                    .save(saver, WResourceLocation.mod("standing_here_i_realize"), existingFileHelper);

            var bite_the_evil_in_the_bud = Advancement.Builder.advancement()
                    .parent(standing_here_i_realize)
                    .display(de.teamlapen.vampirism.core.ModItems.MOTHER_CORE.get(), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud"), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("killed", ModAdvancements.TRIGGER_MOTHER_WIN.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("bite_the_evil_in_the_bud"), existingFileHelper);

            var thick_fur_coat = Advancement.Builder.advancement()
                    .parent(bite_the_evil_in_the_bud)
                    .display(ModItems.WHITE_PELT_CHESTPLATE.get(), Component.translatable("advancements.werewolves.thick_fur_coat"), Component.translatable("advancements.werewolves.thick_fur_coat.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("pelt_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHITE_PELT_HELMET, ModItems.WHITE_PELT_CHESTPLATE, ModItems.WHITE_PELT_LEGGINGS, ModItems.WHITE_PELT_BOOTS))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .save(saver, WResourceLocation.mod("thick_fur_coat"), existingFileHelper);

            var another_one_bites_the_dust = Advancement.Builder.advancement()
                    .parent(thick_fur_coat)
                    .display(ModItems.WEREWOLF_TOOTH.get(), Component.translatable("advancements.werewolves.another_one_bites_the_dust"), Component.translatable("advancements.werewolves.another_one_bites_the_dust.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(WReference.WEREWOLF_FACTION, 1))
                    .addCriterion("devour", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.empty(), new DamageSourcePredicate.Builder().tag(TagPredicate.is(de.teamlapen.werewolves.core.ModTags.DamageTypes.BITE))))
                    .save(saver, WResourceLocation.mod("another_one_bites_the_dust"), existingFileHelper);

            var hairy_situation = Advancement.Builder.advancement()
                    .parent(teen_wolf)
                    .display(ModItems.PELT.get(), Component.translatable("advancements.werewolves.hairy_situation"), Component.translatable("advancements.werewolves.hairy_situation.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PELT))
                    .addCriterion("dark_pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DARK_PELT))
                    .addCriterion("white_pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHITE_PELT))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(saver, WResourceLocation.mod("hairy_situation"), existingFileHelper);

            HolderLookup.RegistryLookup<Biome> biomeRegistryLookup = registries.lookupOrThrow(Registries.BIOME);

            var dog_soldiers = Advancement.Builder.advancement()
                    .parent(hairy_situation)
                    .display(ModBlocks.MAGIC_SAPLING.get(), Component.translatable("advancements.werewolves.dog_soldiers"), Component.translatable("advancements.werewolves.dog_soldiers.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomeRegistryLookup.getOrThrow(ModBiomes.WEREWOLF_FOREST))))
                    .save(saver, WResourceLocation.mod("dog_soldiers"), existingFileHelper);

            var natural_succession = Advancement.Builder.advancement()
                    .parent(dog_soldiers)
                    .display(ModBlocks.WOLFSBANE.get(), Component.translatable("advancements.werewolves.natural_succession"), Component.translatable("advancements.werewolves.natural_succession.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("killed", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(ModEntities.ALPHA_WEREWOLF.get()))))
                    .save(saver, WResourceLocation.mod("natural_succession"), existingFileHelper);

            var silver_hand = Advancement.Builder.advancement()
                    .parent(natural_succession)
                    .display(ModItems.SILVER_SWORD.get(), Component.translatable("advancements.werewolves.silver_hand"), Component.translatable("advancements.werewolves.silver_hand.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("killed", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().subPredicate(FactionSubPredicate.faction(WReference.WEREWOLF_FACTION)), new DamageSourcePredicate.Builder().direct(new EntityPredicate.Builder().equipment(new EntityEquipmentPredicate.Builder().mainhand(ItemPredicate.Builder.item().of(de.teamlapen.werewolves.core.ModTags.Items.SILVER_ITEM))))))
                    .save(saver, WResourceLocation.mod("silver_hand"), existingFileHelper);

        }

        private Advancement.Builder addAllMeats(Advancement.Builder builder) {
            Stream<Item> meat = Stream.of(Items.BEEF, Items.COOKED_BEEF, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.CHICKEN, Items.COOKED_CHICKEN, Items.MUTTON, Items.COOKED_MUTTON, Items.RABBIT, Items.COOKED_RABBIT,
                    Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH,
                    Items.ROTTEN_FLESH, ModItems.V.HUMAN_HEART.get(), ModItems.LIVER.get());
            meat.forEach(item -> builder.addCriterion(BuiltInRegistries.ITEM.getKey(item).toString(), ConsumeItemTrigger.TriggerInstance.usedItem(item)));
            return builder;
        }
    }
}
