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

    private static final int FUEL_DURATION = 20 * 60 * 2;
    private WolfsbaneDiffuserBlock.Type type = WolfsbaneDiffuserBlock.Type.NORMAL;
    private int id;
    private int r = 1;
    private boolean registered = false;
    private int fueled = 0;
    private int bootTimer;
    private int maxBootTimer;
    private boolean initiateBootTimer = false;

    public WolfsbaneDiffuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModTiles.WOLFSBANE_DIFFUSER.get(), pPos, pBlockState);
    }

    public float getBootProgress() {
        return bootTimer > 0 ? (1 - (bootTimer / (float) maxBootTimer)) : 1f;
    }

    public int getFuelTime() {
        return fueled;
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
        return bootTimer == 0;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    /**
     * @return If inside effective distance
     */
    public boolean isInRange(@NotNull BlockPos pos) {
        return new ChunkPos(this.getBlockPos()).getChessboardDistance(new ChunkPos(pos)) <= r;
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
        r = compound.getInt("radius");
        type = WolfsbaneDiffuserBlock.Type.valueOf(compound.getString("type"));
        bootTimer = compound.getInt("boot_timer");
        setFueledTime(compound.getInt("fueled"));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onDataPacket(Connection net, @NotNull ClientboundBlockEntityDataPacket pkt) {
        if (hasLevel()) {
            CompoundTag nbt = pkt.getTag();
            handleUpdateTag(nbt);
            if (isActive()) {
                register(); //Register in case we weren't active before. Shouldn't have an effect when already registered
            }
        }
    }

    public void onTouched(@NotNull Player player) {

    }

    public void onFueled() {
        setFueledTime(FUEL_DURATION);
        this.setChanged();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("radius", r);
        compound.putString("type", type.name());
        compound.putInt("fueled", fueled);
        compound.putInt("boot_timer", bootTimer);
    }

    public void initiateBootTimer() {
        this.initiateBootTimer = true;
    }

    public void setType(WolfsbaneDiffuserBlock.Type type) {
        this.type = type;
        switch (type) {
            case NORMAL -> r = 1;
            case IMPROVED -> r = 2;
            default -> throw new IllegalStateException("Unknown type " + type);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (hasLevel()) {
            BlockState state = level.getBlockState(worldPosition);
            this.level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        unregister();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, @NotNull WolfsbaneDiffuserBlockEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            if (blockEntity.initiateBootTimer) {
                blockEntity.initiateBootTimer = false;
                int bootTime = VampirismConfig.BALANCE.garlicDiffuserStartupTime.get() * 20;
                if (serverLevel.players().size() <= 1) {
                    bootTime >>= 2; // /4
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
        }
    }

    private void register() {
        if (registered || !hasLevel()) {
            return;
        }
        int baseX = (getBlockPos().getX() >> 4);
        int baseZ = (getBlockPos().getZ() >> 4);
        ChunkPos[] chunks = new ChunkPos[(2 * r + 1) * (2 * r + 1)];
        int i = 0;
        for (int x = -r; x <= r; x++) {
            for (int z = -r; z <= r; z++) {
                chunks[i++] = new ChunkPos(x + baseX, z + baseZ);
            }
        }
        id = WerewolvesWorld.getOpt(getLevel()).map(vw -> vw.registerWolfsbaneDiffuserBlock(chunks)).orElse(0);
        registered = i != 0;

    }

    private void setFueledTime(int time) {
        int old = fueled;
        fueled = time;
        if (time > 0 && old == 0 || time == 0 && old > 0) {
            if (!isRemoved()) {
                unregister();
                register();
            }
        }
    }

    private void unregister() {
        if (registered && hasLevel()) {
            WerewolvesWorld.getOpt(getLevel()).ifPresent(vw -> vw.removeWolfsbaneBlocksBlock(id));
            registered = false;
        }
    }

}
