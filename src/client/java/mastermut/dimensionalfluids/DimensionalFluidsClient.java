package mastermut.dimensionalfluids;

import mastermut.dimensionalfluids.init.FluidInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class DimensionalFluidsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		DFFluidRenderManager.setupClient();

		for (FluidInit.DFFluids fluid : FluidInit.DFFluids.values()) {
			BlockRenderLayerMap.INSTANCE.putFluid(fluid.getFluid(), RenderLayer.getTranslucent());
			BlockRenderLayerMap.INSTANCE.putFluid(fluid.getFlowingFluid(), RenderLayer.getTranslucent());
		}

		ClientGuiType.FLUID_PRODUCER.toString();

	}
}