package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTables.class)
public class LootTableManagerMixin {

    @Inject(method = "<init>(Lnet/minecraft/world/level/storage/loot/PredicateManager;)V", at = @At("RETURN"))
    public void constructor(PredicateManager p_i225887_1_, CallbackInfo ci){
        WUtils.LOOT_TABLE_MANAGER = (LootTables)(Object) this;
    }
}
