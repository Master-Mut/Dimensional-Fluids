package mastermut.dimensionalfluids.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidProducer extends DirectionalBlockWithEntity {

    public static final MapCodec<FluidProducer> CODEC = createCodec(FluidProducer::new);

    public FluidProducer(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<FluidProducer> getCodec() { return CODEC; }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FluidProducerEntity fluidProducerEntity){
                for (StorageView<FluidVariant> fluidVariantStorageView : fluidProducerEntity.exposedTanks) {

                    player.sendMessage(Text.literal("Fluid in block: " + fluidVariantStorageView.getResource().toString() + " amount: " + fluidVariantStorageView.getAmount()), false);
                }

                player.sendMessage(Text.literal("you clicked a block entity congrats"), false);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
