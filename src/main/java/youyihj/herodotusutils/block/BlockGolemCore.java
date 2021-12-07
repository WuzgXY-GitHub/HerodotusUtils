package youyihj.herodotusutils.block;

import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import youyihj.herodotusutils.entity.golem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author youyihj
 */
public class BlockGolemCore extends PlainBlock {
    public static final String NAME = "golem_core";
    public static final List<BlockGolemCore> BLOCKS = new ArrayList<>(9);
    public static final List<ItemBlock> ITEM_BLOCKS = new ArrayList<>(9);

    static {
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                if (color != Color.UNSET && shape != Shape.UNSET) {
                    BlockGolemCore block = new BlockGolemCore(color, shape);
                    BLOCKS.add(block);
                    ItemBlock itemBlock = new ItemBlock(block);
                    itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
                    ITEM_BLOCKS.add(itemBlock);
                }
            }
        }
    }

    private final Color color;
    private final Shape shape;
    private BlockPattern golemPattern;

    private BlockGolemCore(Color color, Shape shape) {
        super(Material.IRON, NAME + "_" + color.name().toLowerCase(Locale.ENGLISH) + "_" + shape.name().toLowerCase(Locale.ENGLISH));
        this.color = color;
        this.shape = shape;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (worldIn.isRemote) return;
        BlockPattern.PatternHelper match = getGolemPattern().match(worldIn, pos);
        if (match != null) {
            for (int j = 0; j < this.getGolemPattern().getPalmLength(); ++j) {
                for (int k = 0; k < this.getGolemPattern().getThumbLength(); ++k) {
                    worldIn.setBlockState(match.translateOffset(j, k, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }
            BlockPos pos1 = match.translateOffset(0, 2, 0).getPos();
            Entity golem;
            if (shape == Shape.RHOMBUS) {
                EntityExtraSnowman snowman = new EntityExtraSnowman(worldIn);
                snowman.setColor(color);
                snowman.setShape(shape);
                snowman.setLevel(2);
                golem = snowman;
            } else {
                EntityExtraIronGolem ironGolem = new EntityExtraIronGolem(worldIn);
                ironGolem.setPlayerCreated(true);
                ironGolem.setColor(color);
                ironGolem.setShape(shape);
                ironGolem.setLevel(2);
                golem = ironGolem;
            }
            golem.setLocationAndAngles((double) pos1.getX() + 0.5D, (double) pos1.getY() + 0.05D, (double) pos1.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntity(golem);
        }
    }

    private String getMetalBlockName(BlockWorldState worldState) {
        //noinspection deprecation
        ItemStack item = worldState.getBlockState().getBlock().getItem(worldState.world, worldState.getPos(), worldState.getBlockState());
        if (item.isEmpty()) return "";
        int[] oreIDs = OreDictionary.getOreIDs(item);
        if (oreIDs.length == 1) {
            String oreName = OreDictionary.getOreName(oreIDs[0]);
            if (oreName.startsWith("block")) {
                return oreName.substring("block".length());
            }
        }
        return "";
    }

    public BlockPattern getGolemPattern() {
        if (this.golemPattern == null) {
            Predicate<BlockWorldState> thisCore = (state) -> state.getBlockState().getBlock() == this;
            Predicate<BlockWorldState> metalBlock = (state) -> getMetalBlockName(state).equals(GolemDrops.getColoredShape(color, shape));
            if (shape == Shape.RHOMBUS) {
                this.golemPattern = FactoryBlockPattern.start().aisle("^", "#", "#")
                        .where('^', thisCore)
                        .where('#', metalBlock)
                        .build();
            } else {
                this.golemPattern = FactoryBlockPattern.start().aisle("~^~", "###", "~#~")
                        .where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
                        .where('^', thisCore)
                        .where('#', metalBlock)
                        .build();
            }
        }

        return this.golemPattern;
    }
}
