package youyihj.herodotusutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.Locale;

/**
 * @author youyihj
 */
public class BlockCatalyzedAltar extends PlainBlock {
    public static final IProperty<Type> TYPE = PropertyEnum.create("type", Type.class);
    public static final BlockCatalyzedAltar INSTANCE = new BlockCatalyzedAltar(Material.IRON, "catalyzed_altar");
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("catalyzed_altar");

    private BlockCatalyzedAltar(Material materialIn, String name) {
        super(materialIn, name);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        toggleTypeViaRedstone(state, worldIn, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        toggleTypeViaRedstone(state, worldIn, pos);
        // TODO
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    private void toggleTypeViaRedstone(IBlockState state, World world, BlockPos pos) {
        if (world.isRemote) return;
        boolean powered = world.isBlockPowered(pos);
        if (!powered && state.getValue(TYPE) != Type.TRANSFORM) {
            world.setBlockState(pos, getDefaultState().withProperty(TYPE, Type.TRANSFORM), Constants.BlockFlags.SEND_TO_CLIENTS);
        } else if (powered && state.getValue(TYPE) != Type.GROW) {
            world.setBlockState(pos, getDefaultState().withProperty(TYPE, Type.GROW), Constants.BlockFlags.SEND_TO_CLIENTS);
        }
    }

    public enum Type implements IStringSerializable {
        TRANSFORM,
        GROW;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
