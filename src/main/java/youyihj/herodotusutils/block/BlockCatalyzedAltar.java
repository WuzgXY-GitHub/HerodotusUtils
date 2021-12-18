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

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (worldIn.getStrongPower(pos) == 0 && state.getValue(TYPE) != Type.TRANSFORM) {
            worldIn.setBlockState(pos, getDefaultState().withProperty(TYPE, Type.TRANSFORM));
        } else if (worldIn.getStrongPower(pos) > 0 || state.getValue(TYPE) != Type.GROW) {
            worldIn.setBlockState(pos, getDefaultState().withProperty(TYPE, Type.GROW));
        }
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

    public enum Type implements IStringSerializable {
        TRANSFORM,
        GROW;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
