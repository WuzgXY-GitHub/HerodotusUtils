package youyihj.herodotusutils.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.alchemy.IAdjustableBlock;

/**
 * @author youyihj
 */
public class ItemAlchemyPipeWrench extends Item {
    public static final String NAME = "alchemy_pipe_wrench";
    public static final ItemAlchemyPipeWrench INSTANCE = new ItemAlchemyPipeWrench();

    private ItemAlchemyPipeWrench() {
        this.setRegistryName(NAME);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + NAME);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        World world = player.world;
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof IAdjustableBlock) {
            IAdjustableBlock adjustableBlock = (IAdjustableBlock) block;
            IBlockState result = adjustableBlock.getAdjustedResult(blockState);
            world.setBlockState(pos, result);
            if (!world.isRemote) {
                player.sendStatusMessage(adjustableBlock.getAdjustedMessage(result), true);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
