package mastermut.dimensionalfluids.blocks;

import mastermut.dimensionalfluids.DimensionalFluids;
import mastermut.dimensionalfluids.init.BlockInit;
import mastermut.dimensionalfluids.init.GuiType;
import mastermut.dimensionalfluids.screens.FluidProducerScreenHandler;
import mastermut.dimensionalfluids.screens.ScreenHandlerProvider;
import mastermut.dimensionalfluids.util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.FilteringStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants.BUCKET;

public class FluidProducerEntity extends BlockEntity implements ExtendedScreenHandlerFactory<GuiType.BaseScreenData>, ImplementedInventory, ScreenHandlerProvider {

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

    public final Storage<FluidVariant> exposedDuplicationFluidTank = FilteringStorage.insertOnlyOf(duplicationFluidTank);
    public final Storage<FluidVariant> exposedOutputFluidTank = FilteringStorage.extractOnlyOf(outputFluidTank);
    public final Storage<FluidVariant> exposedTanks = new CombinedStorage<>(List.of(exposedDuplicationFluidTank, exposedOutputFluidTank));

    public FluidProducerEntity(BlockPos pos, BlockState state) {
        super(BlockInit.FLUID_PRODUCER_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        SingleVariantStorage.writeNbt(duplicationFluidTank, FluidVariant.CODEC, nbt, registryLookup);
        SingleVariantStorage.writeNbt(outputFluidTank, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        SingleVariantStorage.readNbt(duplicationFluidTank, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
        SingleVariantStorage.writeNbt(outputFluidTank, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return getScreenHandler(syncId, playerInventory, GuiType.FLUID_PRODUCER.screenHandlerType());
    }


    @Override
    public GuiType.BaseScreenData getScreenOpeningData(ServerPlayerEntity player) {
        return new GuiType.BaseScreenData(pos);
    }

    @Override
    public FluidProducerScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerType<?> screenHandlerType) {
        return new FluidProducerScreenHandler(syncId, playerInventory, screenHandlerType, this);
    }
}
