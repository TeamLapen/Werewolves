package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.faction.common.advancements.criterion.FactionCriterionTrigger;
import de.teamlapen.vampirism.common.core.ModAdvancements;
import de.teamlapen.vampirism.common.core.ModItems;
import de.teamlapen.vampirism.common.tags.ModEntityTags;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModFactions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Werewolves advancements that need Vampirism. They are attached to the advancement tree of the main mod.
 */
public class CompatAdvancementProvider extends AdvancementProvider {

    public CompatAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new Generator()));
    }

    private static class Generator implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            var entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);

            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder(WIdentifier.mod("little_pigs").toString()))
                    .display(ModItems.HUNTER_AXE_NORMAL.get(), Component.translatable("advancements.werewolves.little_red_riding_hood"), Component.translatable("advancements.werewolves.little_red_riding_hood.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("action", KilledTrigger.TriggerInstance.playerKilledEntity(new EntityPredicate.Builder().entityType(EntityTypePredicate.of(entityTypes, ModEntityTags.HUNTER))))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("little_red_riding_hood"));

            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder(WIdentifier.mod("standing_here_i_realize").toString()))
                    .display(ModItems.MOTHER_CORE.get(), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud"), Component.translatable("advancements.werewolves.bite_the_evil_in_the_bud.desc"), null, AdvancementType.CHALLENGE, true, true, false)
                    .addCriterion("killed", ModAdvancements.TRIGGER_MOTHER_WIN.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                    .addCriterion("faction", FactionCriterionTrigger.TriggerInstance.level(ModFactions.WEREWOLF, 1))
                    .save(saver, WIdentifier.mod("bite_the_evil_in_the_bud"));
        }
    }
}
