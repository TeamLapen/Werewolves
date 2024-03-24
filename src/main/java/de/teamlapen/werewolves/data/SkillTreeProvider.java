package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.ISkillNode;
import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.entity.player.skills.SkillTreeConfiguration;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SkillTreeProvider extends de.teamlapen.vampirism.data.provider.parent.SkillTreeProvider {

    public SkillTreeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, REFERENCE.MODID);
    }

    @Override
    protected void buildSkillTrees(HolderLookup.Provider provider, @NotNull de.teamlapen.vampirism.data.provider.parent.SkillTreeProvider.SkillTreeOutput output) {
        HolderLookup.RegistryLookup<ISkillTree> trees = provider.lookupOrThrow(VampirismRegistries.Keys.SKILL_TREE);
        HolderLookup.RegistryLookup<ISkillNode> nodes = provider.lookupOrThrow(VampirismRegistries.Keys.SKILL_NODE);
        output.accept(modId("werewolf_level"), new SkillTreeConfiguration(trees.getOrThrow(ModSkills.Trees.LEVEL), nodes.getOrThrow(ModSkills.Nodes.LEVEL_ROOT),
                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SKILL1),
                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SKILL2),
                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST1),
                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST2),
                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST3),
                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST4),
                                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST5),
                                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST6)),
                                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST7))
                                                                )
                                                        )
                                                ),
                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.BEAST3_1))
                                        )
                                ),
                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL1),
                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL2),
                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL3),
                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL4),
                                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL5))
                                                        ),
                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.SURVIVAL31))
                                                )
                                        )
                                ),
                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.UTIL1),
                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.UTIL2),
                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.UTIL3),
                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER1),
                                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER2),
                                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER3))
                                                                )
                                                        ),
                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER4),
                                                                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER5),
                                                                        new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.OTHER6))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        ));
        output.accept(modId("werewolf_lord"), new SkillTreeConfiguration(trees.getOrThrow(ModSkills.Trees.LORD), nodes.getOrThrow(ModSkills.Nodes.LORD_ROOT),
                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.LORD_2)),
                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.LORD_3)),
                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.LORD_4)),
                new SkillTreeConfiguration.SkillTreeNodeConfiguration(nodes.getOrThrow(ModSkills.Nodes.LORD_5))
        ));
    }
}
