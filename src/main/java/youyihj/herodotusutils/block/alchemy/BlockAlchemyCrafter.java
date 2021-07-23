package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockAlchemyCrafter extends AbstractPipeBlock {
    private BlockAlchemyCrafter() {
        super("alchemy_crafter");
    }

    public static final BlockAlchemyCrafter INSTANCE = new BlockAlchemyCrafter();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("alchemy_crafter");

    @Nonnull
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyCrafter();
    }
}
