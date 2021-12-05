package youyihj.herodotusutils.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;
import youyihj.herodotusutils.entity.golem.*;

/**
 * @author youyihj
 */
public class BlockGolemCore extends PlainBlock {
    public static final String NAME = "golem_core";
    public static final BlockGolemCore INSTANCE = new BlockGolemCore();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName(NAME);

    private BlockGolemCore() {
        super(Material.IRON, NAME);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) return;
        BlockPos down = pos.down();
        BlockPos down2 = down.down();
        IBlockState stateDown = worldIn.getBlockState(down);
        IBlockState stateDown2 = worldIn.getBlockState(down2);
        if (stateDown == stateDown2) {
            String name = getMetalBlockName(stateDown, worldIn, down);
            Pair<Color, Shape> coloredShape = GolemDrops.getColoredShape(name);
            int flag = 0; // 0 -> snowman 1 -> iron golem 2 -> fail
            if (coloredShape != null) {
                Color color = coloredShape.getLeft();
                Shape shape = coloredShape.getRight();
                if (shape != Shape.RHOMBUS) {
                    IBlockState east = worldIn.getBlockState(down.east());
                    IBlockState west = worldIn.getBlockState(down.west());
                    IBlockState north = worldIn.getBlockState(down.north());
                    IBlockState south = worldIn.getBlockState(down.south());
                    if (east == stateDown && west == stateDown) {
                        flag = 1;
                        worldIn.setBlockToAir(down.east());
                        worldIn.setBlockToAir(down.west());
                    } else if (north == stateDown && south == stateDown) {
                        flag = 1;
                        worldIn.setBlockToAir(down.south());
                        worldIn.setBlockToAir(down.north());
                    } else {
                        flag = 2;
                    }
                }
                if (flag != 2) {
                    worldIn.setBlockToAir(pos);
                    worldIn.setBlockToAir(down);
                    worldIn.setBlockToAir(down2);
                }
                switch (flag) {
                    case 0:
                        EntityExtraSnowman entity = new EntityExtraSnowman(worldIn);
                        entity.setLevel(2);
                        entity.setColor(color);
                        entity.setShape(shape);
                        entity.setPosition(down2.getX(), down2.getY(), down2.getZ());
                        worldIn.spawnEntity(entity);
                        break;
                    case 1:
                        EntityExtraIronGolem ironGolem = new EntityExtraIronGolem(worldIn);
                        ironGolem.setLevel(2);
                        ironGolem.setColor(color);
                        ironGolem.setShape(shape);
                        ironGolem.setPlayerCreated(true);
                        ironGolem.setPosition(down2.getX(), down2.getY(), down2.getZ());
                        worldIn.spawnEntity(ironGolem);
                        break;
                }
            }
        }
    }

    private String getMetalBlockName(IBlockState state, World world, BlockPos pos) {
        //noinspection deprecation
        ItemStack item = state.getBlock().getItem(world, pos, state);
        int[] oreIDs = OreDictionary.getOreIDs(item);
        if (oreIDs.length == 1) {
            String oreName = OreDictionary.getOreName(oreIDs[0]);
            if (oreName.startsWith("block")) {
                return oreName.substring("block".length());
            }
        }
        return "";
    }
}
