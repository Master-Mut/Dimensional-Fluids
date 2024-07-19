package mastermut.dimensionalfluids;

import mastermut.dimensionalfluids.init.BlockInit;
import mastermut.dimensionalfluids.init.FluidInit;
import mastermut.dimensionalfluids.init.GuiType;
import mastermut.dimensionalfluids.init.ItemInit;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionalFluids implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("DimensionalFluids");
	public static final String MOD_ID = "dimensionalfluids";

	@Override
	public void onInitialize() {

		LOGGER.info("Dimensional Fluids started initializing.");

		ItemInit.init();

		BlockInit.init();

		FluidInit.init();

		GuiType.FLUID_PRODUCER.identifier();
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path);
	}
}