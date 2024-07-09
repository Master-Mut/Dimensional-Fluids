package mastermut.dimensionalfluids.blocks;

import com.mojang.serialization.MapCodec;

public class FluidProducer extends DirectionalBlockWithEntity {

    public static final MapCodec<FluidProducer> CODEC = createCodec(FluidProducer::new);

    public FluidProducer(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<FluidProducer> getCodec() { return CODEC; }
}
