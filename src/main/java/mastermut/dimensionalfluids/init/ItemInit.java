package mastermut.dimensionalfluids.init;

import mastermut.dimensionalfluids.DimensionalFluids;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static final Item FLUID_ITEM = register("fluid_item", new Item(new Item.Settings()));

    public static <T extends Item> T register(String name, T item){
        return Registry.register(Registries.ITEM, DimensionalFluids.id(name), item);
    }

    public static void init(){}
}
