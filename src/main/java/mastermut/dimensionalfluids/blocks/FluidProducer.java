package mastermut.dimensionalfluids.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemActionResult;
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

    @Override
    protected ItemActionResult onUseWithItem(ItemStack item, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FluidProducerEntity fluidProducerEntity) {
                boolean success = false;
                if (item.isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of("c", "buckets/empty")))) {
                    player.sendMessage(Text.literal("you clicked with a empty bucket"), false);
                    success = true;
                }

                if (success) {
                    return ItemActionResult.CONSUME;
                }

            }
        }
        return ItemActionResult.SUCCESS;
    }
}
