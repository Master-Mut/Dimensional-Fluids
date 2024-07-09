package mastermut.dimensionalfluids.blocks;

import mastermut.dimensionalfluids.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class FluidProducerEntity extends BlockEntity {
    public FluidProducerEntity(BlockPos pos, BlockState state) {
        super(BlockInit.FLUID_PRODUCER_ENTITY, pos, state);
    }
}
