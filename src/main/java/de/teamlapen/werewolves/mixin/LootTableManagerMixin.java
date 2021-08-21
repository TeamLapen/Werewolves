package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.loot.LootTableManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTableManager.class)
public class LootTableManagerMixin {

    @Inject(method = "<init>(Lnet/minecraft/loot/LootPredicateManager;)V", at = @At("RETURN"))
    public void constructor(LootPredicateManager p_i225887_1_, CallbackInfo ci){
        WUtils.LOOT_TABLE_MANAGER = (LootTableManager)(Object) this;
    }
}
