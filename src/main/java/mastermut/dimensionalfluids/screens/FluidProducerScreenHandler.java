package mastermut.dimensionalfluids.screens;

import mastermut.dimensionalfluids.DimensionalFluids;
import mastermut.dimensionalfluids.blocks.FluidProducerEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class FluidProducerScreenHandler extends BaseScreenHandler {

    public FluidProducerScreenHandler(int syncId, PlayerInventory playerInventory, @Nullable ScreenHandlerType<?> type, BlockEntity blockEntity) {
        super(type, syncId, blockEntity);

        int m;
        int l;
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
        DimensionalFluids.LOGGER.info("GUI slots have been made");
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public FluidProducerEntity getBlockEntity() {
        return (FluidProducerEntity) super.getBlockEntity();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return getBlockEntity().canPlayerUse(player);
    }
}
