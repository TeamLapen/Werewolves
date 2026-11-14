package de.teamlapen.werewolves.data.provider;

import de.teamlapen.faction.common.advancements.criterion.FactionCriterionTrigger;
import de.teamlapen.faction.common.advancements.criterion.FactionSubPredicate;
import de.teamlapen.faction.common.advancements.criterion.LordCriterionTrigger;
import de.teamlapen.vampirism.common.tags.ModEntityTags;
import de.teamlapen.werewolves.common.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ModAdvancementProvider extends AdvancementProvider {


    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     */
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new ModAdvancementGenerator()));
    }

    private static class ModAdvancementGenerator implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
            var items = registries.lookupOrThrow(Registries.ITEM);

            var teen_wolf = Advancement.Builder.advancement()
                    .display(ModItems.DARK_PELT_HELMET.get(), Component.translatable("advancements.werewolves"), Component.translatable("advancements.werewolves.teen_wolf.desc"), WIdentifier.mod("textures/block/stripped_jacaranda_log.png"), AdvancementType.TASK, true, false, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("teen_wolf"));

            var bad_moon_rising = Advancement.Builder.advancement()
                    .parent(teen_wolf)
                    .display(ModItems.WEREWOLF_MINION_CHARM.get(), Component.translatable("advancements.werewolves.bad_moon_rising"), Component.translatable("advancements.werewolves.bad_moon_rising.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.TRANSFORM_FULL_MOON))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("bad_moon_rising"));

            var howling_moon = Advancement.Builder.advancement()
                    .parent(bad_moon_rising)
                    .display(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get(), Component.translatable("advancements.werewolves.howling_moon"), Component.translatable("advancements.werewolves.howling_moon.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.HOWLING))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("howling_moon"));

            var dont_touch_that = Advancement.Builder.advancement()
                    .parent(howling_moon)
                    .display(ModItems.SILVER_INGOT.get(), Component.translatable("advancements.werewolves.dont_touch_that"), Component.translatable("advancements.werewolves.dont_touch_that.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.TOUCH_SILVER))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("dont_touch_that"));

            var little_pigs = Advancement.Builder.advancement()
                    .parent(dont_touch_that)
                    .display(Items.PORKCHOP, Component.translatable("advancements.werewolves.little_pigs"), Component.translatable("advancements.werewolves.little_pigs.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("action", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(entityTypes, EntityType.HOGLIN))))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("little_pigs"));

            var little_red_riding_hood = Advancement.Builder.advancement()
                    .parent(little_pigs)
                    .display(de.teamlapen.vampirism.common.core.ModItems.HUNTER_AXE_NORMAL.get(), Component.translatable("advancements.werewolves.little_red_riding_hood"), Component.translatable("advancements.werewolves.little_red_riding_hood.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("action", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(entityTypes, ModEntityTags.HUNTER))))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("little_red_riding_hood"));

            var unrivaled_predator = Advancement.Builder.advancement()
                    .parent(little_red_riding_hood)
                    .display(ModItems.CRACKED_BONE.get(), Component.translatable("advancements.werewolves.unrivaled_predator"), Component.translatable("advancements.werewolves.unrivaled_predator.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("faction", LordCriterionTrigger.TriggerInstance.lord(ModFactions.WEREWOLF, REFERENCE.HIGHEST_WEREWOLF_LEVEL))
                    .save(saver, WIdentifier.mod("unrivaled_predator"));

            var lord_of_the_hunt = Advancement.Builder.advancement()
                    .parent(unrivaled_predator)
                    .display(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get(), Component.translatable("advancements.werewolves.lord_of_the_hunt"), Component.translatable("advancements.werewolves.lord_of_the_hunt.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", LordCriterionTrigger.TriggerInstance.lord(ModFactions.WEREWOLF, REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL))
                    .save(saver, WIdentifier.mod("lord_of_the_hunt"));


            var beast_of_gevauden = Advancement.Builder.advancement()
                    .parent(little_red_riding_hood)
                    .display(ModItems.PELT_HELMET.get(), Component.translatable("advancements.werewolves.beast_of_gevauden"), Component.translatable("advancements.werewolves.beast_of_gevauden.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .addCriterion("village", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.VILLAGE_CONQUERED))
                    .save(saver, WIdentifier.mod("beast_of_gevauden"));

            var insatiable_beast = addAllMeats(items, Advancement.Builder.advancement()
                    .parent(beast_of_gevauden)
                    .display(ModItems.LIVER.get(), Component.translatable("advancements.werewolves.insatiable_beast"), Component.translatable("advancements.werewolves.insatiable_beast.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1)))
                    .save(saver, WIdentifier.mod("insatiable_beast"));

            var standing_here_i_realize = Advancement.Builder.advancement()
                    .parent(insatiable_beast)
                    .display(Items.NETHERITE_BLOCK, Component.translatable("advancements.werewolves.standing_here_i_realize"), Component.translatable("advancements.werewolves.standing_here_i_realize.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .addCriterion("armor", WerewolfActionCriterionTrigger.TriggerInstance.of(WerewolfActionCriterionTrigger.Action.ARMOR_PARTIAL))
                    .save(saver, WIdentifier.mod("standing_here_i_realize"));

            var bite_the_evil_in_the_bud = Advancement.Builder.advancement()
                    .parent(standing_here_i_realize)
                    .display(de.teamlapen.vampirism.common.core.ModItems.MOTHER_CORE.get(), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud"), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("killed", de.teamlapen.vampirism.common.core.ModAdvancements.TRIGGER_MOTHER_WIN.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("bite_the_evil_in_the_bud"));

            var thick_fur_coat = Advancement.Builder.advancement()
                    .parent(bite_the_evil_in_the_bud)
                    .display(ModItems.WHITE_PELT_CHESTPLATE.get(), Component.translatable("advancements.werewolves.thick_fur_coat"), Component.translatable("advancements.werewolves.thick_fur_coat.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("pelt_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHITE_PELT_HELMET, ModItems.WHITE_PELT_CHESTPLATE, ModItems.WHITE_PELT_LEGGINGS, ModItems.WHITE_PELT_BOOTS))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("thick_fur_coat"));

            var another_one_bites_the_dust = Advancement.Builder.advancement()
                    .parent(thick_fur_coat)
                    .display(ModItems.WEREWOLF_TOOTH.get(), Component.translatable("advancements.werewolves.another_one_bites_the_dust"), Component.translatable("advancements.werewolves.another_one_bites_the_dust.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .addCriterion("devour", KilledTrigger.TriggerInstance.playerKilledEntity(Optional.empty(), new DamageSourcePredicate.Builder().tag(TagPredicate.is(de.teamlapen.werewolves.common.core.ModTags.DamageTypes.BITE))))
                    .save(saver, WIdentifier.mod("another_one_bites_the_dust"));

            var hairy_situation = Advancement.Builder.advancement()
                    .parent(teen_wolf)
                    .display(ModItems.PELT.get(), Component.translatable("advancements.werewolves.hairy_situation"), Component.translatable("advancements.werewolves.hairy_situation.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PELT))
                    .addCriterion("dark_pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DARK_PELT))
                    .addCriterion("white_pelt", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHITE_PELT))
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .save(saver, WIdentifier.mod("hairy_situation"));

            HolderLookup.RegistryLookup<Biome> biomeRegistryLookup = registries.lookupOrThrow(Registries.BIOME);

            var dog_soldiers = Advancement.Builder.advancement()
                    .parent(hairy_situation)
                    .display(ModBlocks.MAGIC_SAPLING.get(), Component.translatable("advancements.werewolves.dog_soldiers"), Component.translatable("advancements.werewolves.dog_soldiers.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomeRegistryLookup.getOrThrow(ModBiomes.WEREWOLF_FOREST))))
                    .save(saver, WIdentifier.mod("dog_soldiers"));

            var natural_succession = Advancement.Builder.advancement()
                    .parent(dog_soldiers)
                    .display(ModBlocks.WOLFSBANE.get(), Component.translatable("advancements.werewolves.natural_succession"), Component.translatable("advancements.werewolves.natural_succession.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("killed", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(entityTypes, ModEntities.ALPHA_WEREWOLF.get()))))
                    .save(saver, WIdentifier.mod("natural_succession"));

            var silver_hand = Advancement.Builder.advancement()
                    .parent(natural_succession)
                    .display(ModItems.SILVER_SWORD.get(), Component.translatable("advancements.werewolves.silver_hand"), Component.translatable("advancements.werewolves.silver_hand.desc"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("killed", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().subPredicate(FactionSubPredicate.faction(ModFactions.WEREWOLF)), new DamageSourcePredicate.Builder().direct(new EntityPredicate.Builder().equipment(new EntityEquipmentPredicate.Builder().mainhand(ItemPredicate.Builder.item().of(items, de.teamlapen.werewolves.common.core.ModTags.Items.SILVER_ITEM))))))
                    .save(saver, WIdentifier.mod("silver_hand"));

        }

        private Advancement.Builder addAllMeats(HolderLookup.RegistryLookup<Item> items, Advancement.Builder builder) {
            Stream<Item> meat = Stream.of(Items.BEEF, Items.COOKED_BEEF, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.CHICKEN, Items.COOKED_CHICKEN, Items.MUTTON, Items.COOKED_MUTTON, Items.RABBIT, Items.COOKED_RABBIT,
                    Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH,
                    Items.ROTTEN_FLESH, ModItems.V.HUMAN_HEART.get(), ModItems.LIVER.get());
            meat.forEach(item -> builder.addCriterion(BuiltInRegistries.ITEM.getKey(item).toString(), ConsumeItemTrigger.TriggerInstance.usedItem(items, item)));
            return builder;
        }
    }
}
