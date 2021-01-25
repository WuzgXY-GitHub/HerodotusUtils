package youyihj.herodotusutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import youyihj.herodotusutils.HerodotusUtils;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class BlockManaLiquidizer extends Block {

    private BlockManaLiquidizer() {
        super(Material.ROCK);
        this.setRegistryName(NAME);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + NAME);
        this.setCreativeTab(CreativeTabs.MISC);
        this.setHardness(3.0f);
        this.setResistance(50.0f);
        this.setHarvestLevel("pickaxe", 1);
    }

    public static final BlockManaLiquidizer INSTANCE = new BlockManaLiquidizer();
    public static final String NAME = "mana_liquidizer";
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName(NAME);

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileManaLiquidizer();
    }
}
