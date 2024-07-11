package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import mastermut.dimensionalfluids.fluids.DFFluid;
import mastermut.dimensionalfluids.fluids.DFFluidSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluidInit {

    public static final List<DFFluid> FLUIDS = new ArrayList<>();

    public static void init(){
        Arrays.stream(DFFluids.values()).forEach(DFFluids::register);
    }

    public enum DFFluids implements ItemConvertible{
        DUPLICATION_FLUID;

        private DFFluid stillFluid;
        private DFFluid flowingFluid;
        private FluidBlock fluidBlock;
        private BucketItem bucketItem;
        private final Identifier identifier;

        DFFluids(){
            this.identifier = DimensionalFluids.id(this.toString().toLowerCase(Locale.ROOT));

            DFFluidSettings fluidSettings = DFFluidSettings.create();

            fluidSettings.setStillTexture(DimensionalFluids.id("block/fluids/" + this.toString().toLowerCase(Locale.ROOT) + "_still"));
            fluidSettings.setFlowingTexture(DimensionalFluids.id("block/fluids/" + this.toString().toLowerCase(Locale.ROOT) + "_flowing"));

            stillFluid = new DFFluid(true, fluidSettings, () -> fluidBlock, () -> bucketItem, () -> flowingFluid, () -> stillFluid){};
            flowingFluid = new DFFluid(false, fluidSettings, () -> fluidBlock, () -> bucketItem, () -> flowingFluid, () -> stillFluid){};
            fluidBlock = new FluidBlock(stillFluid, AbstractBlock.Settings.copy(Blocks.WATER));
            bucketItem = new BucketItem(stillFluid, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        }

        public void register(){

            Registry.register(Registries.FLUID, identifier, stillFluid);
            Registry.register(Registries.FLUID, DimensionalFluids.id(identifier.getPath() + "_flowing"), flowingFluid);
            Registry.register(Registries.BLOCK, identifier, fluidBlock);
            Registry.register(Registries.ITEM, DimensionalFluids.id(identifier.getPath() + "_bucket"), bucketItem);

            FLUIDS.add(stillFluid);
            FLUIDS.add(flowingFluid);
        }

        public DFFluid getFluid(){
            return stillFluid;
        }

        public DFFluid getFlowingFluid(){
            return flowingFluid;
        }

        public FluidBlock getBlock() {
            return fluidBlock;
        }

        public Identifier getIdentifier() {
            return identifier;
        }

        public BucketItem getBucket() {
            return bucketItem;
        }

        @Override
        public Item asItem() {
            return getBucket();
        }
    }
}
