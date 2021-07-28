package youyihj.herodotusutils.block.modularmachine;

import hellfirepvp.modularmachinery.common.CommonProxy;
import hellfirepvp.modularmachinery.common.block.BlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponent;
import javax.annotation.Nullable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import youyihj.herodotusutils.block.modularmachine.tile.TileAspectListProvider.Output;

public class BlockAspectListProviderOutput extends BlockMachineComponent {

    public static final BlockAspectListProviderOutput INSTANCE = new BlockAspectListProviderOutput();
    public static final Item ITEM_BLOCK = new ItemBlockMachineComponent(INSTANCE);

    public BlockAspectListProviderOutput() {
        super(Material.IRON);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
        this.setHarvestLevel("pickaxe", 1);
        this.setRegistryName("block_aspectlist_provider_output");
        this.setCreativeTab(CommonProxy.creativeTabModularMachinery);
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new Output();
    }

    @Nullable
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
