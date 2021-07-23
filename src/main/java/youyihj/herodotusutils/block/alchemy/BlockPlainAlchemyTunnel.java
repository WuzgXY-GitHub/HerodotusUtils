package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
public abstract class BlockPlainAlchemyTunnel extends AbstractPipeBlock {
    public static final BlockPlainAlchemyTunnel STRAIGHT = new BlockPlainAlchemyTunnel("straight_tunnel") {
        @Override
        protected TunnelType getTunnelType() {
            return TunnelType.STRAIGHT;
        }
    };
    public static final BlockPlainAlchemyTunnel HORIZONTAL_RIGHT_ANGLE = new BlockPlainAlchemyTunnel("right_angle_tunnel") {
        @Override
        protected TunnelType getTunnelType() {
            return TunnelType.HORIZONTAL_RIGHT_ANGLE;
        }
    };
    public static final BlockPlainAlchemyTunnel VERTICAL_RIGHT_ANGLE = new BlockPlainAlchemyTunnel("vertical_right_angle") {
        @Override
        protected TunnelType getTunnelType() {
            return TunnelType.VERTICAL_RIGHT_ANGLE;
        }
    };
    public static final Item STRAIGHT_ITEM = new ItemBlock(STRAIGHT).setRegistryName("straight_tunnel");
    public static final Item RIGHT_ANGLE_ITEM = new ItemBlock(HORIZONTAL_RIGHT_ANGLE).setRegistryName("right_angle_tunnel");
    public static final Item VERTICAL_ITEM = new ItemBlock(VERTICAL_RIGHT_ANGLE).setRegistryName("vertical_right_angle");

    private IProperty<TransferDirection> property;

    protected BlockPlainAlchemyTunnel(String name) {
        super(name);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.blockState.getValidStates().indexOf(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getValidStates().get(meta);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.blockState.getBaseState().withProperty(property, getTunnelType().directionSupplierFromPlacement.get(placer.getHorizontalFacing(), hitX, hitZ));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        property = createProperty();
        return new BlockStateContainer(this, property);
    }

    private IProperty<TransferDirection> createProperty() {
        return PropertyEnum.create("direction", TransferDirection.class, getTunnelType().directionFilter::test);
    }

    protected abstract TunnelType getTunnelType();

    @Nonnull
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyTunnel();
    }

    public TransferDirection getDirection(IBlockState state) {
        return state.getValue(this.property);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.isSneaking() || !playerIn.getHeldItem(hand).isEmpty())
            return false;
        if (!worldIn.isRemote) {
            int meta = this.getMetaFromState(state);
            meta++;
            if (meta >= this.blockState.getValidStates().size())
                meta = 0;
            worldIn.setBlockState(pos, this.getStateFromMeta(meta));
        }
        return true;
    }

    @SuppressWarnings("unused")
    public enum TransferDirection implements IStringSerializable {
        // straight
        N2S(EnumFacing.NORTH, EnumFacing.SOUTH),
        S2N(EnumFacing.SOUTH, EnumFacing.NORTH),
        W2E(EnumFacing.WEST, EnumFacing.EAST),
        E2W(EnumFacing.EAST, EnumFacing.WEST),
        // horizontal right angle
        N2W(EnumFacing.NORTH, EnumFacing.WEST),
        N2E(EnumFacing.NORTH, EnumFacing.EAST),
        S2W(EnumFacing.SOUTH, EnumFacing.WEST),
        S2E(EnumFacing.SOUTH, EnumFacing.EAST),
        W2N(EnumFacing.WEST, EnumFacing.NORTH),
        W2S(EnumFacing.WEST, EnumFacing.SOUTH),
        E2N(EnumFacing.WEST, EnumFacing.NORTH),
        E2S(EnumFacing.EAST, EnumFacing.SOUTH),
        // vertical right angle
        U2N(EnumFacing.UP, EnumFacing.NORTH),
        U2S(EnumFacing.UP, EnumFacing.SOUTH),
        U2W(EnumFacing.UP, EnumFacing.WEST),
        U2E(EnumFacing.UP, EnumFacing.EAST);

        private final EnumFacing inputSide;
        private final EnumFacing outputSide;

        TransferDirection(EnumFacing inputSide, EnumFacing outputSide) {
            this.inputSide = inputSide;
            this.outputSide = outputSide;
        }

        public static TransferDirection find(EnumFacing inputSide, EnumFacing outputSide) {
            for (TransferDirection value : values()) {
                if (value.inputSide == inputSide && value.outputSide == outputSide) {
                    return value;
                }
            }
            throw new NoSuchElementException("No such transfer direction for input: " + inputSide.name() + " output: " + outputSide.name());
        }

        public EnumFacing getInputSide() {
            return inputSide;
        }

        public EnumFacing getOutputSide() {
            return outputSide;
        }

        public boolean isStraight() {
            return inputSide.getOpposite() == outputSide;
        }

        public boolean isVerticalRightAngle() {
            return inputSide == EnumFacing.UP;
        }

        public boolean isHorizontalRightAngle() {
            return !isStraight() && !isVerticalRightAngle();
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }

    public enum TunnelType {
        STRAIGHT(TransferDirection::isStraight, (mainFacing, hitX, hitZ) -> {
            switch (mainFacing) {
                case NORTH:
                    return TransferDirection.S2N;
                case SOUTH:
                    return TransferDirection.N2S;
                case WEST:
                    return TransferDirection.E2W;
                case EAST:
                    return TransferDirection.W2E;
                default:
                    throw new IllegalArgumentException(mainFacing.name());
            }
        }),
        HORIZONTAL_RIGHT_ANGLE(TransferDirection::isHorizontalRightAngle, (mainFacing, hitX, hitZ) -> {
            EnumFacing outputSide;
            if (mainFacing.getAxis() == EnumFacing.Axis.X) {
                outputSide = hitZ > 0.5f ? EnumFacing.SOUTH : EnumFacing.NORTH;
            } else {
                outputSide = hitX > 0.5f ? EnumFacing.EAST : EnumFacing.WEST;
            }
            return TransferDirection.find(mainFacing, outputSide);
        }),
        VERTICAL_RIGHT_ANGLE(TransferDirection::isVerticalRightAngle, (mainFacing, hitX, hitZ) ->
                (hitX + hitZ < 1.0f) ?
                        (hitX > hitZ) ? TransferDirection.U2N : TransferDirection.U2W
                        :
                        (hitX > hitZ) ? TransferDirection.U2E : TransferDirection.U2S
        );

        private final Predicate<TransferDirection> directionFilter;
        private final IDirectionSupplierFromPlacement directionSupplierFromPlacement;

        TunnelType(Predicate<TransferDirection> directionFilter, IDirectionSupplierFromPlacement directionSupplierFromPlacement) {
            this.directionFilter = directionFilter;
            this.directionSupplierFromPlacement = directionSupplierFromPlacement;
        }
    }

    @FunctionalInterface
    private interface IDirectionSupplierFromPlacement {
        TransferDirection get(EnumFacing mainFacing, float hitX, float hitZ);
    }
}
