package youyihj.herodotusutils.util.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class EntityContainmentCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final IEntityContainment entityContainment = new IEntityContainment.Impl();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Capabilities.ENTITY_CONTAINMENT;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Capabilities.ENTITY_CONTAINMENT) {
            return Capabilities.ENTITY_CONTAINMENT.cast(entityContainment);
        } else {
            return null;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("containsEntity", entityContainment.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        entityContainment.deserializeNBT(Util.getTag(nbt, "containsEntity", NBTTagString.class, null));
    }
}
