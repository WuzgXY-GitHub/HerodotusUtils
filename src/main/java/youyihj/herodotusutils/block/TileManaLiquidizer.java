package youyihj.herodotusutils.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import vazkii.botania.api.mana.IManaReceiver;
import youyihj.herodotusutils.fluid.FluidMana;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileManaLiquidizer extends TileEntity implements IManaReceiver {
    private static final int FLUID_CAPACITY = 10000;

    private final FluidTank fluidHandler = new FluidTank(FLUID_CAPACITY);

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(fluidHandler.writeToNBT(compound));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fluidHandler.readFromNBT(compound);
    }

    @Override
    public boolean isFull() {
        return fluidHandler.getFluidAmount() == FLUID_CAPACITY;
    }

    @Override
    public void recieveMana(int mana) {
        fluidHandler.fill(new FluidStack(FluidMana.INSTANCE, mana / 10), true);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return fluidHandler.getFluidAmount() * 10;
    }
}
