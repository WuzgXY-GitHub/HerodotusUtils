package youyihj.herodotusutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import youyihj.herodotusutils.HerodotusUtils;

/**
 * @author youyihj
 */
public class PlainBlock extends Block {
    public PlainBlock(Material materialIn, String name) {
        super(materialIn);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.MISC);
        this.blockHardness = 5.0f;
        this.blockResistance = 50.0f;
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + name);
    }

    public static final PlainBlock STRUCTURE_BLOCK_1 = new PlainBlock(Material.IRON, "structure_block_1");
    public static final PlainBlock STRUCTURE_BLOCK_2 = new PlainBlock(Material.IRON, "structure_block_2");
    public static final PlainBlock STRUCTURE_BLOCK_3 = new PlainBlock(Material.IRON, "structure_block_3");
    public static final Item STRUCTURE_BLOCK_1_ITEM = new ItemBlock(STRUCTURE_BLOCK_1).setRegistryName("structure_block_1");
    public static final Item STRUCTURE_BLOCK_2_ITEM = new ItemBlock(STRUCTURE_BLOCK_2).setRegistryName("structure_block_2");
    public static final Item STRUCTURE_BLOCK_3_ITEM = new ItemBlock(STRUCTURE_BLOCK_3).setRegistryName("structure_block_3");
}
