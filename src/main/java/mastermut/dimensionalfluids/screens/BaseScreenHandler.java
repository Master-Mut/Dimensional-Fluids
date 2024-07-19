package mastermut.dimensionalfluids.screens;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public abstract class BaseScreenHandler  extends ScreenHandler {
    private final BlockEntity blockEntity;
    protected BaseScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, BlockEntity blockEntity) {
        super(type, syncId);
        this.blockEntity = blockEntity;
    }

    public BlockEntity getBlockEntity(){
        return blockEntity;
    }
}
