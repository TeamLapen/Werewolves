package de.teamlapen.werewolves.blocks.entity;

import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.werewolves.blocks.WolfsbaneDiffuserBlock;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.world.WerewolvesWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WolfsbaneDiffuserBlockEntity extends BlockEntity {

    private static final int FUEL_DURATION = 20 * 60 * 10;
    private WolfsbaneDiffuserBlock.Type type = WolfsbaneDiffuserBlock.Type.NORMAL;
    private int id;
    private boolean registered = false;
    private int fueled = 0;
    private int bootTimer = -1;
    private int maxBootTimer;
    private boolean initiateBootTimer = false;

    public WolfsbaneDiffuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModTiles.WOLFSBANE_DIFFUSER.get(), pPos, pBlockState);
    }

    public float getBootProgress() {
        return this.bootTimer > 0 ? (1 - (this.bootTimer / (float) this.maxBootTimer)) : 1f;
    }

    public int getFuelTime() {
        return this.fueled;
    }

    public float getFueledState() {
        return this.fueled / (float) FUEL_DURATION;
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        if (isActive()) {
            register();
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean isActive() {
        return this.bootTimer == 0;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
        this.type = WolfsbaneDiffuserBlock.Type.valueOf(compound.getString("type"));
        this.bootTimer = compound.getInt("boot_timer");
        this.maxBootTimer = compound.getInt("max_boot_timer");
        this.setFueledTime(compound.getInt("fueled"));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onDataPacket(Connection net, @NotNull ClientboundBlockEntityDataPacket pkt) {
        if (hasLevel()) {
            CompoundTag nbt = pkt.getTag();
            this.handleUpdateTag(nbt);
            if (this.isActive()) {
                this.register(); //Register in case we weren't active before. Shouldn't have an effect when already registered
            }
        }
    }

    public void onTouched(@NotNull Player player) {

    }
    
    private int radius() {
        return this.type.range.get();
    }

    public void onFueled() {
        this.setFueledTime(this.type.fuelTime.get());
        this.initiateBootTimer();
        this.setChanged();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putString("type", type.name());
        compound.putInt("fueled", fueled);
        compound.putInt("boot_timer", bootTimer);
        compound.putInt("max_boot_timer", maxBootTimer);
    }

    public void initiateBootTimer() {
        if (this.bootTimer == -1) {
            this.initiateBootTimer = true;
        }
    }

    public void setType(WolfsbaneDiffuserBlock.Type type) {
        this.type = type;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.hasLevel()) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.unregister();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, @NotNull WolfsbaneDiffuserBlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            if (blockEntity.initiateBootTimer) {
                blockEntity.initiateBootTimer = false;
                int bootTime = VampirismConfig.BALANCE.garlicDiffuserStartupTime.get() * 20;
                if (serverLevel.players().size() <= 1) {
                    bootTime >>= 2; // /4
                }
                if (blockEntity.type == WolfsbaneDiffuserBlock.Type.LONG) {
                    bootTime *= 2;
                }
                blockEntity.bootTimer = bootTime;
                blockEntity.maxBootTimer = bootTime;
                blockEntity.setChanged();
            }
        }
        if (blockEntity.bootTimer > 0) {
            if (--blockEntity.bootTimer == 0) {
                blockEntity.setChanged();
                blockEntity.register();
            }
        } else if (blockEntity.fueled > 0) {
            if (blockEntity.fueled == 1) {
                blockEntity.setFueledTime(0);
                blockEntity.setChanged();
            } else {
                blockEntity.fueled--;
            }
        } else {
            blockEntity.bootTimer = -1;
        }
    }

    private void register() {
        if (this.registered || !hasLevel()) {
            return;
        }
        int baseX = (getBlockPos().getX() >> 4);
        int baseZ = (getBlockPos().getZ() >> 4);
        int radius = radius();
        ChunkPos[] chunks = new ChunkPos[(2 * radius + 1) * (2 * radius + 1)];
        int i = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                chunks[i++] = new ChunkPos(x + baseX, z + baseZ);
            }
        }
        //noinspection DataFlowIssue
        this.id = WerewolvesWorld.getOpt(getLevel()).map(vw -> vw.registerWolfsbaneDiffuserBlock(chunks)).orElse(0);
        this.registered = i != 0;

    }

    private void setFueledTime(int time) {
        int old = this.fueled;
        this.fueled = time;
        if (this.fueled > 0 && old == 0 && this.bootTimer == 0) {
            if (!isRemoved()) {
                register();
            }
        } else if(this.fueled == 0){
            unregister();
        }
    }

    private void unregister() {
        if (this.registered && hasLevel()) {
            //noinspection DataFlowIssue
            WerewolvesWorld.getOpt(getLevel()).ifPresent(vw -> vw.removeWolfsbaneBlocksBlock(id));
            this.registered = false;
        }
    }

}
