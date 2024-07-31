package mastermut.dimensionalfluids.datagen.models;

import mastermut.dimensionalfluids.init.FluidInit;
import mastermut.dimensionalfluids.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    // Don't try to use this for machine blocks. It's not worth the trouble.


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ItemInit.FLUID_ITEM, Models.GENERATED);
        itemModelGenerator.register(FluidInit.DFFluids.DUPLICATION_FLUID.getBucket(), Models.GENERATED);

    }
}
