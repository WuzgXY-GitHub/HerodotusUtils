package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import youyihj.herodotusutils.alchemy.IAlchemyExternalHatch;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class BlockAlchemyInputHatch extends AbstractPipeBlock {
    private BlockAlchemyInputHatch() {
        super("alchemy_input_hatch");
    }

    public static final BlockAlchemyInputHatch INSTANCE = new BlockAlchemyInputHatch();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("alchemy_input_hatch");

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        IFluidHandler tileEntityFluidHandler = FluidUtil.getFluidHandler(worldIn, pos, EnumFacing.UP);
        if (tileEntityFluidHandler == null)
            return false;
        FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(heldItem, tileEntityFluidHandler, IAlchemyExternalHatch.FLUID_UNIT, playerIn, true);
        if (fluidActionResult.isSuccess()) {
            playerIn.setHeldItem(hand, fluidActionResult.getResult());
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyInputHatch();
    }
}
