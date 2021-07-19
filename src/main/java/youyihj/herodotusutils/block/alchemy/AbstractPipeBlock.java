package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.herodotusutils.alchemy.IPipe;
import youyihj.herodotusutils.block.PlainBlock;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
public abstract class AbstractPipeBlock extends PlainBlock {

    protected AbstractPipeBlock(String name) {
        super(Material.IRON, name);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Nullable
    @Override
    public abstract AbstractPipeTileEntity createTileEntity(World world, IBlockState state);

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        recalculatePipes(worldIn, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        recalculatePipes(worldIn, pos);
    }

    private void recalculatePipes(World world, BlockPos pos) {
        Optional.ofNullable(world.getTileEntity(pos))
                .filter(IPipe.class::isInstance)
                .map(IPipe.class::cast)
                .map(IPipe::getLinkedController)
                .ifPresent(TileAlchemyController::startScanPipes);
    }
}
