package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import mastermut.dimensionalfluids.blocks.FluidProducerEntity;
import mastermut.dimensionalfluids.network.BlockPosPayload;
import mastermut.dimensionalfluids.screens.BaseScreenHandler;
import mastermut.dimensionalfluids.screens.ScreenHandlerProvider;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record GuiType<T extends BlockEntity>(Identifier identifier, ScreenHandlerType<BaseScreenHandler> screenHandlerType) {

    public static final GuiType<FluidProducerEntity> FLUID_PRODUCER = register("fluid_producer");


    private static <T extends BlockEntity> GuiType<T> register(String path){
        var id = DimensionalFluids.id(path);
        var screenHandlerType = Registry.register(Registries.SCREEN_HANDLER, id, new ExtendedScreenHandlerType<>(getScreenHandlerFactory(id), BaseScreenData.PACKET_CODEC));
        return new GuiType<>(id, screenHandlerType);
    };

    private static ExtendedScreenHandlerType.ExtendedFactory<BaseScreenHandler, BaseScreenData> getScreenHandlerFactory(Identifier identifier) {
        return (syncId, playerInventory, payload) -> {
            final BlockEntity blockEntity = playerInventory.player.getWorld().getBlockEntity(payload.pos());

            if (!(blockEntity instanceof ScreenHandlerProvider)){
                DimensionalFluids.LOGGER.error("block entity is either null or not a instance of ScreenHandlerProvider");
                throw new RuntimeException("Instance of BlockEntity is null or does not implement ScreenHandlerProvider");
            }

            BaseScreenHandler screenHandler = ((ScreenHandlerProvider) blockEntity).getScreenHandler(syncId, playerInventory, Registries.SCREEN_HANDLER.get(identifier));
            return screenHandler;

        };
    }

    public record BaseScreenData(BlockPos pos) implements BlockPosPayload {
        public static final PacketCodec<RegistryByteBuf, BaseScreenData> PACKET_CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, BaseScreenData::pos, BaseScreenData::new);

    }


    public static void init(){}
}
