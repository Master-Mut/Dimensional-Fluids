package mastermut.dimensionalfluids.screens;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public interface ScreenHandlerProvider {
    BaseScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerType<?> screenHandlerType);
}
