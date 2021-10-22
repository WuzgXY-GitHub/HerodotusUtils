package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import youyihj.herodotusutils.alchemy.IAdjustableBlock;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * @author youyihj
 */
public class BlockAlchemyController extends AbstractPipeBlock implements IAdjustableBlock {
    /* package-private */ static final PropertyEnum<WorkType> WORK_TYPE_PROPERTY = PropertyEnum.create("work_type", WorkType.class);

    private BlockAlchemyController() {
        super("alchemy_controller");
    }

    public static final BlockAlchemyController INSTANCE = new BlockAlchemyController();
    public static final Item ITEM_BLOCK = new ItemBlock(INSTANCE).setRegistryName("alchemy_controller");

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, WORK_TYPE_PROPERTY);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(WORK_TYPE_PROPERTY).ordinal();
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(WORK_TYPE_PROPERTY, WorkType.valueOf(meta));
    }

    @Nonnull
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyController();
    }

    @Override
    public IBlockState getAdjustedResult(IBlockState previous) {
        WorkType value = previous.getValue(WORK_TYPE_PROPERTY);
        return this.getDefaultState().withProperty(WORK_TYPE_PROPERTY, Util.getCycledNextElement(WorkType.values(), value));
    }

    @Override
    public ITextComponent getAdjustedMessage(IBlockState state) {
        WorkType value = state.getValue(WORK_TYPE_PROPERTY);
        return new TextComponentTranslation("hdsutils.alchemy.controller.status").appendSibling(value.getDisplayName());
    }

    public enum WorkType implements IStringSerializable {
        CLOSE(false),
        DEBUG((world, pos, thisTileEntity) -> {
            boolean hasRedstoneSignal = world.getStrongPower(pos) != 0;
            boolean pre = thisTileEntity.lastRedstoneSignal;
            thisTileEntity.lastRedstoneSignal = hasRedstoneSignal;
            return !pre && hasRedstoneSignal;
        }),
        WORKING((world, pos, thisTileEntity) -> world.getStrongPower(pos) == 0 && world.getTotalWorldTime() % 20 == 0);

        private final ITextComponent displayName;
        private final WorkCondition workCondition;

        WorkType(WorkCondition workCondition) {
            this.displayName = new TextComponentTranslation("hdsutils.alchemy.controller." + getName());
            this.workCondition = workCondition;
        }

        WorkType(boolean shouldWork) {
            this((world, pos, thisTileEntity) -> shouldWork);
        }

        public static WorkType valueOf(int index) {
            if (index >= values().length)
                index = 0;
            return values()[index];
        }

        public ITextComponent getDisplayName() {
            return displayName;
        }

        public boolean shouldWork(World world, BlockPos pos, TileAlchemyController thisTileEntity) {
            return workCondition.shouldWork(world, pos, thisTileEntity);
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }

    @FunctionalInterface
    private interface WorkCondition {
        boolean shouldWork(World world, BlockPos pos, TileAlchemyController thisTileEntity);
    }
}
