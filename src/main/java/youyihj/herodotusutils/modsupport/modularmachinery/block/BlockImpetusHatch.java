package youyihj.herodotusutils.modsupport.modularmachinery.block;

import hellfirepvp.modularmachinery.common.CommonProxy;
import hellfirepvp.modularmachinery.common.block.BlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thecodex6824.thaumicaugmentation.common.tile.trait.IBreakCallback;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.modsupport.modularmachinery.tile.TileImpetusComponent;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public abstract class BlockImpetusHatch extends BlockMachineComponent {
    private BlockImpetusHatch(String name) {
        super(Material.IRON);
        this.blockHardness = 3.0f;
        this.blockResistance = 50.0f;
        this.setHarvestLevel("pickaxe", 1);
        this.setRegistryName(name);
        this.setCreativeTab(CommonProxy.creativeTabModularMachinery);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + name);
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 15;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        Util.getTileEntity(worldIn, pos, IBreakCallback.class).ifPresent(IBreakCallback::onBlockBroken);
        super.breakBlock(worldIn, pos, state);
    }

    public static class Input extends BlockImpetusHatch {

        private Input() {
            super("impetus_input_hatch");
        }

        public static final Input INSTANCE = new Input();
        public static final Item ITEM_BLOCK = new ItemBlockMachineComponent(INSTANCE).setRegistryName("impetus_input_hatch");

        @Nullable
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {
            return new TileImpetusComponent.Input();
        }
    }

    public static class Output extends BlockImpetusHatch {

        private Output() {
            super("impetus_output_hatch");
        }

        public static final Output INSTANCE = new Output();
        public static final Item ITEM_BLOCK = new ItemBlockMachineComponent(INSTANCE).setRegistryName("impetus_output_hatch");

        @Nullable
        @Override
        public TileEntity createTileEntity(World world, IBlockState state) {
            return new TileImpetusComponent.Output();
        }
    }
}
