package mastermut.dimensionalfluids.network;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public interface BlockPosPayload {
    BlockPos pos();

    default boolean isWithinDistance(PlayerEntity player, double distance) {
        return player.getBlockPos().isWithinDistance(pos(), distance);
    }

    default boolean canUse(ServerPlayerEntity player, Predicate<ScreenHandler> screenHandlerPredicate) {
        ScreenHandler currentScreenHandler = player.currentScreenHandler;

        if (currentScreenHandler == null) {
            return false;
        }

        if (!screenHandlerPredicate.test(currentScreenHandler)) {
            return false;
        }

        return currentScreenHandler.canUse(player);
    }

    default <T extends BlockEntity> T getBlockEntity(BlockEntityType<T> type, PlayerEntity player) {

        BlockEntity blockEntity = getBlockEntity(player);

        if (type != blockEntity.getType()) {
            throw new IllegalStateException("Block entity is not of the correct type. Expected: " + Registries.BLOCK_ENTITY_TYPE.getId(type) + " but got: " + Registries.BLOCK_ENTITY_TYPE.getId(blockEntity.getType()));
        }

        //noinspection unchecked
        return (T) blockEntity;
    }

    default <T extends BlockEntity> T getBlockEntity(Class<T> baseClass, PlayerEntity player) {

        BlockEntity blockEntity = getBlockEntity(player);

        if (!baseClass.isInstance(blockEntity)) {
            throw new IllegalStateException("Block entity is not of the correct class");
        }

        //noinspection unchecked
        return (T) blockEntity;
    }

    default BlockEntity getBlockEntity(PlayerEntity player) {
        if (!isWithinDistance(player, 64)) {
            throw new IllegalStateException("Player cannot use this block entity as its too far away");
        }

        BlockEntity blockEntity = player.getWorld().getBlockEntity(pos());

        if (blockEntity == null) {
            throw new IllegalStateException("Block entity is null");
        }

        return blockEntity;
    }
}
