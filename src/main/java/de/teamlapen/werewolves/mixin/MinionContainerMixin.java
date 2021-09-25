package de.teamlapen.werewolves.mixin;

import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.entity.minion.MinionEntity;
import de.teamlapen.vampirism.inventory.container.MinionContainer;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Predicate;

//TODO remove on vampirism 1.8.2
@Mixin(MinionContainer.class)
public abstract class MinionContainerMixin {
    @ModifyVariable(method = "createSelectors(Lde/teamlapen/vampirism/entity/minion/MinionEntity;I)[Lde/teamlapen/lib/lib/inventory/InventoryContainer$SelectorInfo;", at = @At("STORE"), ordinal = 0, remap = false, require = 0)
    private static Predicate<ItemStack> changePredicate(Predicate<ItemStack> predicate, MinionEntity<?> minionEntity, int extraSlots) {
        if (minionEntity.getFaction() != WReference.WEREWOLF_FACTION) return predicate;
        return itemStack -> ((itemStack.getItem() instanceof IFactionExclusiveItem) && ((IFactionExclusiveItem) itemStack.getItem()).getExclusiveFaction().equals(WReference.WEREWOLF_FACTION)) || itemStack.getUseAnimation() == UseAction.DRINK || itemStack.getUseAnimation() == UseAction.EAT;
    }
}
