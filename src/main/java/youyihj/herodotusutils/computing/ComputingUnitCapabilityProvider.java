package youyihj.herodotusutils.computing;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ComputingUnitCapabilityProvider implements ICapabilityProvider {
    private IComputingUnit computingUnit = new IComputingUnit.Impl();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY) {
            return ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY.cast(computingUnit);
        } else {
            return null;
        }
    }
}
