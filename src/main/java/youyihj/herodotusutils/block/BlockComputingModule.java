package youyihj.herodotusutils.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class BlockComputingModule extends BlockCalculatorStructure {
    public BlockComputingModule() {
        super("computing_module");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileComputingModule();
    }

    public static final BlockComputingModule INSTANCE = new BlockComputingModule();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("computing_module");

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }
}
