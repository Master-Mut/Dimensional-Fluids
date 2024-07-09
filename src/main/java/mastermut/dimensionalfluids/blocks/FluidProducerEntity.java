package mastermut.dimensionalfluids.blocks;

import mastermut.dimensionalfluids.init.BlockInit;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import static net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants.BUCKET;

public class FluidProducerEntity extends BlockEntity {

    public static final FluidVariant DUPLICATION_FLUID = FluidVariant.of(Fluids.WATER);

    private final SingleVariantStorage<FluidVariant> duplicationFluidTank = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 16 * BUCKET;
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return variant.equals(DUPLICATION_FLUID);
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    private final SingleVariantStorage<FluidVariant> outputFluidTank = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 16 * BUCKET;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public FluidProducerEntity(BlockPos pos, BlockState state) {
        super(BlockInit.FLUID_PRODUCER_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.put("fluidVariant", ((FluidVariant)duplicationFluidTank.variant).toNbt());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
    }
}
