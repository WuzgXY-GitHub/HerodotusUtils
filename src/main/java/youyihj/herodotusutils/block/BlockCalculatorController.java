package youyihj.herodotusutils.block;


import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class BlockCalculatorController extends PlainBlock {
    private BlockCalculatorController(int capacity) {
        super(Material.IRON, "calculator_controller_" + capacity);
        this.capacity = capacity;
    }

    private int capacity;

    public static final BlockCalculatorController CONTROLLER_1 = new BlockCalculatorController(4);
    public static final BlockCalculatorController CONTROLLER_2 = new BlockCalculatorController(36);
    public static final BlockCalculatorController CONTROLLER_3 = new BlockCalculatorController(240);
    public static final Item ITEM_BLOCK_1 = new ItemBlock(CONTROLLER_1).setRegistryName("calculator_controller_4");
    public static final Item ITEM_BLOCK_2 = new ItemBlock(CONTROLLER_2).setRegistryName("calculator_controller_36");
    public static final Item ITEM_BLOCK_3 = new ItemBlock(CONTROLLER_3).setRegistryName("calculator_controller_240");

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCalculatorController(this.capacity);
    }
}
