package mastermut.dimensionalfluids.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class DFFluid extends FlowableFluid {

    private final boolean still;

    private final DFFluidSettings fluidSettings;
    private final Supplier<FluidBlock> fluidBlockSupplier;
    private final Supplier<BucketItem> bucketItemSupplier;
    private final Supplier<DFFluid> flowingSupplier;
    private final Supplier<DFFluid> stillSupplier;

    public DFFluid(boolean still, DFFluidSettings fluidSettings, Supplier<FluidBlock> fluidBlockSupplier, Supplier<BucketItem> bucketItemSupplier, Supplier<DFFluid> flowingSupplier, Supplier<DFFluid> stillSupplier){
        this.still = still;
        this.fluidSettings = fluidSettings;
        this.fluidBlockSupplier = fluidBlockSupplier;
        this.bucketItemSupplier = bucketItemSupplier;
        this.flowingSupplier = flowingSupplier;
        this.stillSupplier = stillSupplier;
    }

    public DFFluidSettings fluidSettings(){
        return fluidSettings;
    }

    @Override
    public Fluid getFlowing() {
        return flowingSupplier.get();
    }

    @Override
    public Fluid getStill() {
        return stillSupplier.get();
    }

    /**
     * @return whether the fluid can be created infinitely like water.
     */
    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    /**
     * Perform actions when the fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     * Have all the fluids perform like water.
     */
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getMaxFlowDistance(WorldView world) {
        return 4;
    }

    /**
     * water returns 1, lava is 2 in overworld and 1 in nether.
     */
    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    /**
     * The bucket item.
     */
    @Override
    public Item getBucketItem() {
        return bucketItemSupplier.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !this.matchesType(fluid);
    }

    /**
     * water is 5, lava is 30 in overworld and 10 in nether.
     */
    @Override
    public int getTickRate(WorldView world) {
        return 5;
    }

    /***
     * lava and water return 100.
     */
    @Override
    protected float getBlastResistance() {
        return 100f;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return fluidBlockSupplier.get().getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return getFlowing() == fluid || getStill() == fluid;
    }

    @Override
    public boolean isStill(FluidState state) {
        return still;
    }

    @Override
    public int getLevel(FluidState state) {
        return still ? 8 : state.get(LEVEL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
        super.appendProperties(builder);
        if (!still){
            builder.add(LEVEL);
        }
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }
}
