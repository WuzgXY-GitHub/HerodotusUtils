package youyihj.herodotusutils.computing;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import youyihj.herodotusutils.util.capability.Capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ComputingUnitCapabilityProvider implements ICapabilityProvider {
    private final IComputingUnit computingUnit = new IComputingUnit.Impl();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Capabilities.COMPUTING_UNIT;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Capabilities.COMPUTING_UNIT) {
            return Capabilities.COMPUTING_UNIT.cast(computingUnit);
        } else {
            return null;
        }
    }
}
