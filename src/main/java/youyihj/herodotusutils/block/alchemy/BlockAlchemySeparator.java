package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockAlchemySeparator extends AbstractPipeBlock {
    private BlockAlchemySeparator() {
        super("alchemy_separator");
    }

    public static final BlockAlchemySeparator INSTANCE = new BlockAlchemySeparator();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("alchemy_separator");

    @Nonnull
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemySeparator();
    }
}
