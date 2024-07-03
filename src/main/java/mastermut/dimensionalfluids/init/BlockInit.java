package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class BlockInit {
    public static final Block FLUID_PRODUCER = registerWithItem("fluid_producer", new Block(AbstractBlock.Settings.create()
            .strength(5f, 6f)
            .sounds(BlockSoundGroup.METAL)
            .requiresTool()));

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

    public static void init(){}
}