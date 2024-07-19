package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class Tags {

    //Fluid tags
    public static final TagKey<Fluid> NON_DUPLICATABLE = TagKey.of(RegistryKeys.FLUID, DimensionalFluids.id("non_duplicatable"));

}
