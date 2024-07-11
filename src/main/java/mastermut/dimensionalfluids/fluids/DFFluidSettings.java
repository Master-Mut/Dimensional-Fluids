package mastermut.dimensionalfluids.fluids;

import net.minecraft.util.Identifier;

public class DFFluidSettings {

    private Identifier flowingTexture = Identifier.of("dimensionalfluids:nope");
    private Identifier stillTexture = Identifier.of("dimensionalfluids:nope");

    public DFFluidSettings setFlowingTexture(Identifier flowingTexture) {
        this.flowingTexture = flowingTexture;
        return this;
    }

    public DFFluidSettings setStillTexture(Identifier stillTexture) {
        this.stillTexture = stillTexture;
        return this;
    }

    public Identifier getFlowingTexture() {
        return flowingTexture;
    }

    public Identifier getStillTexture() {
        return stillTexture;
    }

    private DFFluidSettings() {
    }

    public static DFFluidSettings create() {
        return new DFFluidSettings();
    }

}
