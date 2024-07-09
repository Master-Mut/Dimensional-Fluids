package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import mastermut.dimensionalfluids.blocks.FluidProducer;
import mastermut.dimensionalfluids.blocks.FluidProducerEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class BlockInit {
    public static final Block FLUID_PRODUCER = registerWithItem("fluid_producer", new FluidProducer(AbstractBlock.Settings.create()
            .strength(5f, 6f)
            .sounds(BlockSoundGroup.METAL)
            .requiresTool()));

    public static final BlockEntityType<FluidProducerEntity> FLUID_PRODUCER_ENTITY = registerBlockEntity("fluid_producer_entity", FluidProducerEntity::new, FLUID_PRODUCER);

    public static <T extends Block> T register(String name, T block){
        return Registry.register(Registries.BLOCK, DimensionalFluids.id(name), block);
    }

    public static <T extends Block> T registerWithItem(String name, T block, Item.Settings settings){
        T registered = register(name, block);
        ItemInit.register(name, new BlockItem(registered, settings));
        return registered;
    }

    public static <T extends Block> T registerWithItem(String name, T block){
        return registerWithItem(name, block, new Item.Settings());
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType.BlockEntityFactory<T> blockEntityFactory, Block... block){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, DimensionalFluids.id(name), BlockEntityType.Builder.create(blockEntityFactory, block).build());
    }

    public static void init(){}

    static {

        FluidStorage.SIDED.registerForBlockEntity((machine, direction) -> switch (direction){
            // Only expose the input tank on the top
            case UP -> machine.exposedDuplicationFluidTank;
            // Only expose the output tank on the bottom
            case DOWN -> machine.exposedOutputFluidTank;
            // Expose all the tanks on the sides
            default -> machine.exposedTanks;
        }, FLUID_PRODUCER_ENTITY);
    }
}
