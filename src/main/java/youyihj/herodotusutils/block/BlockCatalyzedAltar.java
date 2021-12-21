package youyihj.herodotusutils.block;

import com.infinityraider.agricraft.api.v1.AgriApi;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v1.seed.AgriSeed;
import com.infinityraider.agricraft.tiles.TileEntityCrop;
import hellfirepvp.modularmachinery.common.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import thaumcraft.api.aspects.Aspect;
import youyihj.herodotusutils.util.Util;

import java.util.*;

/**
 * @author youyihj
 */
public class BlockCatalyzedAltar extends PlainBlock {
    public static final IProperty<Type> TYPE = PropertyEnum.create("type", Type.class);
    public static final BlockCatalyzedAltar INSTANCE = new BlockCatalyzedAltar(Material.IRON, "catalyzed_altar");
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("catalyzed_altar");
    public static final Map<Aspect, TransformRule> TRANSFORM_RULES;

    static {
        TRANSFORM_RULES = Collections.emptyMap();
        // TODO RULES
    }

    private BlockCatalyzedAltar(Material materialIn, String name) {
        super(materialIn, name);
    }

    public static Optional<Aspect> getAspectPlant(World world, BlockPos pos) {
        return Util.getTileEntity(world, pos, TileEntityCrop.class)
                .filter(TileEntityCrop::canBeHarvested)
                .map(TileEntityCrop::getSeed)
                .map(AgriSeed::getPlant)
                .map(IAgriPlant::getId)
                .filter(it -> it.endsWith("_vis_plant"))
                .map(it -> it.substring(0, it.indexOf('_')))
                .map(Aspect::getAspect);
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
        IBlockState currentState = toggleTypeViaRedstone(state, worldIn, pos);
        if (worldIn.isRemote) return;
        if (currentState.getValue(TYPE) == Type.TRANSFORM) {
            for (TransformRule transformRule : TRANSFORM_RULES.values()) {
                if (transformRule.matches(worldIn, pos)) {
                    Optional<IAgriPlant> plant = AgriApi.getPlantRegistry().get(transformRule.getResult().getTag() + "_vis_plant");
                    Optional<TileEntityCrop> crop = Util.getTileEntity(worldIn, pos.up(), TileEntityCrop.class);
                    crop.filter(TileEntityCrop::hasSeed).ifPresent(te ->
                            plant.map(pl -> new AgriSeed(pl, te.getSeed().getStat())).ifPresent(te::setSeed)
                    );
                }
            }
        } else {
            getAspectPlant(worldIn, pos.up())
                    .map(TRANSFORM_RULES::get)
                    .filter(rule -> rule.matches(worldIn, pos))
                    .flatMap(rule -> Util.getTileEntity(worldIn, pos.up(), TileEntityCrop.class))
                    .ifPresent(TileEntityCrop::applyGrowthTick);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Type.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    private IBlockState toggleTypeViaRedstone(IBlockState state, World world, BlockPos pos) {
        if (world.isRemote) return state;
        boolean powered = world.isBlockPowered(pos);
        IBlockState newState = state;
        if (!powered && state.getValue(TYPE) != Type.TRANSFORM) {
            newState = state.withProperty(TYPE, Type.TRANSFORM);
        } else if (powered && state.getValue(TYPE) != Type.GROW) {
            newState = state.withProperty(TYPE, Type.GROW);
        }
        return newState;
    }

    public enum Type implements IStringSerializable {
        TRANSFORM,
        GROW;

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }

    public static class TransformRule {
        private final Aspect result;
        private final List<Pair<BlockPos, Aspect>> in = new ArrayList<>();

        public TransformRule(Aspect result, Aspect... in) {
            this.result = result;
            if (in.length != 8) throw new IllegalArgumentException();
            for (int i = 0; i < in.length; i++) {
                int j = i > 3 ? i + 1 : i;
                this.in.add(Pair.of(new BlockPos(j % 3 - 1, 0, j / 3 - 1), in[i]));
            }
        }

        public Aspect getResult() {
            return result;
        }

        public boolean matches(World world, BlockPos pos) {
            EnumFacing facing = EnumFacing.NORTH;
            do {
                BitSet bitSet = new BitSet(8);
                for (int i = 0; i < 8; i++) {
                    Pair<BlockPos, Aspect> pair = in.get(i);
                    BlockPos offset = pos.add(MiscUtils.rotateYCCWNorthUntil(pair.getLeft(), facing));
                    boolean matches = getAspectPlant(world, offset).filter(pair.getValue()::equals).isPresent();
                    bitSet.set(i, matches);
                }
                if (bitSet.cardinality() == bitSet.length()) {
                    return true;
                }
                facing = facing.rotateYCCW();
            } while (facing != EnumFacing.NORTH);
            return false;
        }


    }
}
