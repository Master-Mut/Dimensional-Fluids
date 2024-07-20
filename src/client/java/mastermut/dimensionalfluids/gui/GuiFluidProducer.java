package mastermut.dimensionalfluids.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mastermut.dimensionalfluids.blocks.FluidProducerEntity;
import mastermut.dimensionalfluids.init.GuiType;
import mastermut.dimensionalfluids.screens.BaseScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuiFluidProducer extends HandledScreen<BaseScreenHandler> {

    private final FluidProducerEntity blockEntity;

    public GuiFluidProducer(int syncId, PlayerEntity playerEntity, FluidProducerEntity fluidProducerEntity) {
        super(fluidProducerEntity.getScreenHandler(syncId, playerEntity.getInventory(), GuiType.FLUID_PRODUCER.screenHandlerType()),
                playerEntity.getInventory(),
                Text.translatable(fluidProducerEntity.getCachedState().getBlock().getTranslationKey()));
        this.blockEntity = fluidProducerEntity;
    }

    /*
    TODO:
    Make the gui actually look good
    Implement the slots
    */

    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/gui/container/dispenser.png");

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

    }
}
