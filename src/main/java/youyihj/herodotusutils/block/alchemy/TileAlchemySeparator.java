package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import youyihj.herodotusutils.alchemy.IAlchemyModule;
import youyihj.herodotusutils.alchemy.IHasAlchemyFluid;
import youyihj.herodotusutils.recipe.AlchemyRecipes;
import youyihj.herodotusutils.util.Util;

/**
 * @author youyihj
 */
public class TileAlchemySeparator extends AbstractHasAlchemyFluidTileEntity implements IAlchemyModule {
    @Override
    public void work() {
        Fluid[] output = AlchemyRecipes.getSeparatingOutputFor(content);
        if (output != null) {
            EnumFacing[] tankFacings = new EnumFacing[output.length];
            for (int i = 0; i < output.length; i++) {
                for (EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
                    switch (checkNearbyTank(i, enumFacing)) {
                        case SUCCESS:
                            tankFacings[i] = enumFacing;
                            break;
                        case FILLED_TANK:
                            return;
                    }
                }
            }
            emptyFluid();
            for (int i = 0; i < tankFacings.length; i++) {
                EnumFacing facing = tankFacings[i];
                final int finalI = i;
                Util.getTileEntity(world, pos.offset(facing), TileAlchemySeparatorTank.class)
                        .ifPresent(te -> te.handleInput(output[finalI], null));
            }
        }
    }

    @Override
    public EnumFacing inputSide() {
        return EnumFacing.UP;
    }

    @Override
    public EnumFacing outputSide() {
        return null;
    }

    private TankCheckResult checkNearbyTank(int ordinal, EnumFacing facing) {
        BlockPos offset = pos.offset(facing);
        IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() != BlockAlchemySeparatorTank.INSTANCE) {
            return TankCheckResult.NOT_A_TANK;
        } else if (blockState.getValue(BlockAlchemySeparatorTank.NUMBER) != ordinal + 1) {
            return TankCheckResult.NOT_MATCHING_ORDINAL;
        } else {
            IHasAlchemyFluid hasAlchemyFluid = Util.getTileEntity(world, offset, IHasAlchemyFluid.class).orElseThrow(RuntimeException::new);
            if (hasAlchemyFluid.getContainedFluid() != null) {
                return TankCheckResult.FILLED_TANK;
            }
        }
        return TankCheckResult.SUCCESS;
    }

    private enum TankCheckResult {
        SUCCESS,
        NOT_A_TANK,
        FILLED_TANK,
        NOT_MATCHING_ORDINAL
    }
}
