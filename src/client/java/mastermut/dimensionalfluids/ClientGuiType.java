package mastermut.dimensionalfluids;

import mastermut.dimensionalfluids.blocks.FluidProducerEntity;
import mastermut.dimensionalfluids.gui.GuiFluidProducer;
import mastermut.dimensionalfluids.init.GuiType;
import mastermut.dimensionalfluids.screens.BaseScreenHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import java.util.Objects;

public record ClientGuiType<T extends BlockEntity>(GuiType<T> guiType, GuiFactory<T> guiFactory) {
    public static final ClientGuiType<FluidProducerEntity> FLUID_PRODUCER = register(GuiType.FLUID_PRODUCER, GuiFluidProducer::new);


    public static <T extends BlockEntity> ClientGuiType<T> register(GuiType<T> guiType, GuiFactory<T> guiFactory){
        return new ClientGuiType<>(guiType, guiFactory);
    }

    public ClientGuiType(GuiType<T> guiType, GuiFactory<T> guiFactory){
        this.guiType = Objects.requireNonNull(guiType);
        this.guiFactory = Objects.requireNonNull(guiFactory);

        HandledScreens.register(guiType.screenHandlerType(), guiFactory());
    }

}

interface GuiFactory<T extends BlockEntity> extends HandledScreens.Provider<BaseScreenHandler, HandledScreen<BaseScreenHandler>> {
    HandledScreen<BaseScreenHandler> create(int syncId, PlayerEntity playerEntity, T blockEntity);

    @Override
    default HandledScreen<BaseScreenHandler> create(BaseScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
        PlayerEntity playerEntity = playerInventory.player;
        //noinspection unchecked
        T blockEntity = (T) builtScreenHandler.getBlockEntity();
        return create(builtScreenHandler.syncId, playerEntity, blockEntity);
    }
}
