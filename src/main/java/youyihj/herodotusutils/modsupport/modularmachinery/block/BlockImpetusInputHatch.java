package youyihj.herodotusutils.modsupport.modularmachinery.block;

import hellfirepvp.modularmachinery.common.CommonProxy;
import hellfirepvp.modularmachinery.common.block.BlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponent;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.modsupport.modularmachinery.tile.TileImpetusComponent;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class BlockImpetusInputHatch extends BlockMachineComponent {
    private BlockImpetusInputHatch() {
        super(Material.IRON);
        this.blockHardness = 3.0f;
        this.blockResistance = 50.0f;
        this.setHarvestLevel("pickaxe", 1);
        this.setRegistryName("impetus_input_hatch");
        this.setCreativeTab(CommonProxy.creativeTabModularMachinery);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + "impetus_input_hatch");
    }

    public static final BlockImpetusInputHatch INSTANCE = new BlockImpetusInputHatch();
    public static final Item ITEM_BLOCK = new ItemBlockMachineComponent(INSTANCE).setRegistryName("impetus_input_hatch");


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileImpetusComponent.Input();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
