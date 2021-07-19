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

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * @author youyihj
 */
public abstract class BlockPlainAlchemyTunnel extends AbstractPipeBlock {
    public static final BlockPlainAlchemyTunnel STRAIGHT = new BlockPlainAlchemyTunnel("straight_tunnel") {
        @Override
        protected IProperty<TransportDirection> createProperty() {
            return PropertyEnum.create("direction", TransportDirection.class, TransportDirection::isStraight);
        }
    };
    public static final BlockPlainAlchemyTunnel RIGHT_ANGLE = new BlockPlainAlchemyTunnel("right_angle_tunnel") {
        @Override
        protected IProperty<TransportDirection> createProperty() {
            return PropertyEnum.create("direction", TransportDirection.class, TransportDirection::isRightAngle);
        }
    };
    public static final Item STRAIGHT_ITEM = new ItemBlock(STRAIGHT).setRegistryName("straight_tunnel");
    public static final Item RIGHT_ANGLE_ITEM = new ItemBlock(RIGHT_ANGLE).setRegistryName("right_angle_tunnel");
    private IProperty<TransportDirection> property;

    private BlockPlainAlchemyTunnel(String name) {
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
        EnumFacing inputSide = placer.getHorizontalFacing();
        EnumFacing outputSide;
        if (this == STRAIGHT) {
            outputSide = inputSide.getOpposite();
        } else if (inputSide.getAxis() == EnumFacing.Axis.X) {
            outputSide = hitZ > 0.5f ? EnumFacing.SOUTH : EnumFacing.NORTH;
        } else {
            outputSide = hitX > 0.5f ? EnumFacing.EAST : EnumFacing.WEST;
        }
        return this.blockState.getBaseState().withProperty(property,
                property.getAllowedValues().stream()
                        .filter(direction -> direction.inputSide == inputSide)
                        .filter(direction -> direction.outputSide == outputSide)
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new)
        );
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, createProperty());
    }

    protected abstract IProperty<TransportDirection> createProperty();

    @Nullable
    @Override
    public AbstractPipeTileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyTunnel();
    }

    public TransportDirection getDirection(IBlockState state) {
        return state.getValue(this.property);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && playerIn.isSneaking()) {
            int meta = this.getMetaFromState(state);
            meta++;
            if (meta >= this.blockState.getValidStates().size())
                meta = 0;
            worldIn.setBlockState(pos, this.getStateFromMeta(meta));
        }
        return true;
    }

    public enum TransportDirection implements IStringSerializable {
        N2S(EnumFacing.NORTH, EnumFacing.SOUTH),
        S2N(EnumFacing.SOUTH, EnumFacing.NORTH),
        W2E(EnumFacing.WEST, EnumFacing.EAST),
        E2W(EnumFacing.EAST, EnumFacing.WEST),
        N2W(EnumFacing.NORTH, EnumFacing.WEST),
        N2E(EnumFacing.NORTH, EnumFacing.EAST),
        S2W(EnumFacing.SOUTH, EnumFacing.WEST),
        S2E(EnumFacing.SOUTH, EnumFacing.EAST),
        W2N(EnumFacing.WEST, EnumFacing.NORTH),
        W2S(EnumFacing.WEST, EnumFacing.SOUTH),
        E2N(EnumFacing.WEST, EnumFacing.NORTH),
        E2S(EnumFacing.EAST, EnumFacing.SOUTH);

        private final EnumFacing inputSide;
        private final EnumFacing outputSide;

        TransportDirection(EnumFacing inputSide, EnumFacing outputSide) {
            this.inputSide = inputSide;
            this.outputSide = outputSide;
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

        public boolean isRightAngle() {
            return !isStraight();
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}
