package mastermut.dimensionalfluids.datagen;

import mastermut.dimensionalfluids.datagen.loottables.BlockLootTableProvider;
import mastermut.dimensionalfluids.datagen.tags.FluidTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DimensionalFluidsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();

		pack.addProvider(FluidTagProvider::new);


		pack.addProvider(BlockLootTableProvider::new);
	}
}
