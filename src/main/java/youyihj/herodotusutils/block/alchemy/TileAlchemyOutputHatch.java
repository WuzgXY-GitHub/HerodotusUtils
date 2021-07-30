package youyihj.herodotusutils.block.alchemy;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import youyihj.herodotusutils.alchemy.IAlchemyExternalHatch;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileAlchemyOutputHatch extends AbstractHasAlchemyFluidTileEntity implements IAlchemyExternalHatch {
    private final CustomFluidHandler fluidHandler = new CustomFluidHandler();

    @Override
    public void work() {
        if (content != null) {
            IFluidHandler downFluidHandler = FluidUtil.getFluidHandler(world, pos.down(), EnumFacing.UP);
            if (downFluidHandler != null) {
                FluidStack fluidStack = new FluidStack(content, FLUID_UNIT);
                int testValue = downFluidHandler.fill(fluidStack, false);
                if (testValue == FLUID_UNIT) {
                    downFluidHandler.fill(fluidStack, true);
                    emptyFluid();
                }
            }
        }
    }

    @Override
    public EnumFacing inputSide() {
        return world.getBlockState(pos).getValue(BlockHorizontal.FACING);
    }

    @Override
    public EnumFacing outputSide() {
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.DOWN && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.DOWN && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        return super.getCapability(capability, facing);
    }

    private class CustomFluidHandler implements IFluidHandler, IFluidTankProperties {

        @Override
        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[]{this};
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            return 0;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource.getFluid() == resource.getFluid() && resource.amount >= FLUID_UNIT) {
                if (doDrain) {
                    emptyFluid();
                    TileAlchemyOutputHatch.this.markDirty();
                }
                return new FluidStack(content, FLUID_UNIT);
            }
            return null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (maxDrain >= FLUID_UNIT) {
                if (doDrain) {
                    emptyFluid();
                    TileAlchemyOutputHatch.this.markDirty();
                }
                return new FluidStack(content, FLUID_UNIT);
            }
            return null;
        }

        @Nullable
        @Override
        public FluidStack getContents() {
            return content == null ? null : new FluidStack(content, FLUID_UNIT);
        }

        @Override
        public int getCapacity() {
            return FLUID_UNIT;
        }

        @Override
        public boolean canFill() {
            return false;
        }

        @Override
        public boolean canDrain() {
            return true;
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
            return false;
        }

        @Override
        public boolean canDrainFluidType(FluidStack fluidStack) {
            return true;
        }
    }
}
