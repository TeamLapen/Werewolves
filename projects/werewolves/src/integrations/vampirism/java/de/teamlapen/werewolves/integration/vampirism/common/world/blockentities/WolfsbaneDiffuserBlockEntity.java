package de.teamlapen.werewolves.integration.vampirism.common.world.blockentities;

import de.teamlapen.library.api.world.EnumStrength;
import de.teamlapen.vampirism.api.VampirismFactions;
import de.teamlapen.vampirism.common.world.blockentity.diffuser.DiffuserBlockEntity;
import de.teamlapen.vampirism.common.world.inventory.DiffuserMenu;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import de.teamlapen.werewolves.common.world.effects.WolfsbaneEffect;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlockEntities;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatDataMaps;
import de.teamlapen.werewolves.integration.vampirism.common.datamaps.WolfsbaneDiffuserFuel;
import de.teamlapen.werewolves.integration.vampirism.common.world.inventory.WolfsbaneDiffuserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

public class WolfsbaneDiffuserBlockEntity extends DiffuserBlockEntity {

    private EnumStrength type = EnumStrength.WEAK;
    private int radius;
    private int emitterId;

    public WolfsbaneDiffuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(pPos, pBlockState, EnumStrength.WEAK, 0);
    }

    public WolfsbaneDiffuserBlockEntity(BlockPos pPos, BlockState pBlockState, EnumStrength type, int radius) {
        super(CompatBlockEntities.WOLFSBANE_DIFFUSER.get(), pPos, pBlockState);
        this.type = type;
        this.radius = radius;
    }

    /**
     * @return whether the given position is in a chunk affected by this diffuser
     */
    public boolean isInRange(BlockPos pos) {
        return Math.abs((pos.getX() >> 4) - (getBlockPos().getX() >> 4)) <= this.radius && Math.abs((pos.getZ() >> 4) - (getBlockPos().getZ() >> 4)) <= this.radius;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("strength", EnumStrength.CODEC, this.type);
        output.putInt("radius", this.radius);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.type = input.read("strength", EnumStrength.CODEC).orElse(EnumStrength.WEAK);
        this.radius = input.getIntOr("radius", 0);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.werewolves.wolfsbane_diffuser");
    }

    @Override
    protected DiffuserMenu createMenu(int pContainerId, Inventory pInventory, LockDataHolder lockData) {
        return new WolfsbaneDiffuserMenu(pContainerId, pInventory, this, this.dataAccess, lockData);
    }

    @Override
    protected int getBurnDuration(ItemStack itemStack) {
        WolfsbaneDiffuserFuel data = itemStack.typeHolder().getData(CompatDataMaps.WOLFSBANE_DIFFUSER_FUEL_MAP);
        return data != null ? data.burnDuration() : 0;
    }

    @Override
    public boolean canOpen(Player pPlayer) {
        return tryAccess(pPlayer, VampirismFactions.HUNTER, getName()) && super.canOpen(pPlayer);
    }


    @Override
    public void onTouched(Player player) {
        if (Helper.isWerewolf(player)) {
            player.addEffect(WolfsbaneEffect.createWolfsbaneEffect(player, 20 * 20, this.type.getStrength() - 1));
        }
    }

    @Override
    protected void activateEffect(Level level, BlockPos blockPos, BlockState blockState) {
        if (emitterId == 0) {
            int baseX = (getBlockPos().getX() >> 4);
            int baseZ = (getBlockPos().getZ() >> 4);
            ChunkPos[] chunks = new ChunkPos[(2 * this.radius + 1) * (2 * this.radius + 1)];
            int i = 0;
            for (int x = -this.radius; x <= this.radius; x++) {
                for (int z = -this.radius; z <= this.radius; z++) {
                    chunks[i++] = new ChunkPos(x + baseX, z + baseZ);
                }
            }
            this.emitterId = LevelWolfsbane.get(level).registerWolfsbaneBlock(this.type, List.of(chunks));
        }
    }

    @Override
    public void deactivateEffect(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.emitterId != 0) {
            LevelWolfsbane.get(level).removeWolfsbaneBlock(this.emitterId);
            this.emitterId = 0;
        }
    }
}
