package mastermut.dimensionalfluids;

import mastermut.dimensionalfluids.fluids.DFFluid;
import mastermut.dimensionalfluids.fluids.DFFluidSettings;
import mastermut.dimensionalfluids.init.FluidInit;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DFFluidRenderManager {
    public static void setupClient(){
        FluidInit.FLUIDS.forEach(DFFluidRenderManager::setupFluidRenderer);
    }

    private static void setupFluidRenderer(DFFluid fluid){

        DFFluidSettings fluidSettings = fluid.fluidSettings();
        //Sprite[] sprite = new Sprite[]{getSprite(fluidSettings.getStillTexture()), getSprite(fluidSettings.getFlowingTexture())};

        // This is the one to use. the one below is just for testing. probably
        //FluidRenderHandlerRegistry.INSTANCE.register(fluid, (extendedBlockView, blockPos, fluidState) -> sprite);
        FluidRenderHandlerRegistry.INSTANCE.register(fluid, new SimpleFluidRenderHandler(Identifier.of("minecraft:block/water_still"),
                Identifier.of("minecraft:block/water_flow"),
                0xFAFAFA));
    }

    private static Sprite getSprite(Identifier identifier) {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(identifier);
    }
}
