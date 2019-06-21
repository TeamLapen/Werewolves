package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class ModVillages {
    @GameRegistry.ObjectHolder(REFERENCE.MODID + ":werewolf_expert")
    public static final VillagerRegistry.VillagerProfession profession_werewolf_expert = getNull();

    static void init() {
        registerTrades();
    }

    private static void registerTrades() {
        VillagerRegistry.VillagerCareer normal_hunter_expert = new VillagerRegistry.VillagerCareer(profession_werewolf_expert, REFERENCE.MODID + ".werewolf_expert");
        normal_hunter_expert.addTrade(1, new EntityVillager.EmeraldForItems(ModItems.vampire_fang, new EntityVillager.PriceInfo(20, 30)));
        normal_hunter_expert.addTrade(2, new EntityVillager.EmeraldForItems(ModItems.vampire_book, new EntityVillager.PriceInfo(1, 1)));
    }

    static void registerProfessions(IForgeRegistry<VillagerRegistry.VillagerProfession> registry) {
        registry.register(new VillagerRegistry.VillagerProfession(REFERENCE.MODID + ":werewolf_expert", "vampirism:textures/entity/villager_hunter_expert.png", "vampirism:textures/entity/villager_hunter_expert_zombie.png"));
    }
}
