package mastermut.dimensionalfluids.datagen.tags;

import mastermut.dimensionalfluids.init.FluidInit;
import mastermut.dimensionalfluids.init.Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class FluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public FluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        Arrays.stream(FluidInit.DFFluids.values()).forEach((fluid) -> getOrCreateTagBuilder(FluidTags.WATER)
                .add(fluid.getFluid())
                .add(fluid.getFlowingFluid()));

        getOrCreateTagBuilder(Tags.NON_DUPLICATABLE)
                .add(FluidInit.DFFluids.DUPLICATION_FLUID.getFluid())
                .add(FluidInit.DFFluids.DUPLICATION_FLUID.getFlowingFluid());
    }
}
