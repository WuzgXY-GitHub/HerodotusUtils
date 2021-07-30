package youyihj.herodotusutils.modsupport.modularmachinery.tile;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import hellfirepvp.modularmachinery.common.tiles.base.TileColorableMachineComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import thecodex6824.thaumicaugmentation.ThaumicAugmentation;
import thecodex6824.thaumicaugmentation.api.impetus.node.CapabilityImpetusNode;
import thecodex6824.thaumicaugmentation.api.impetus.node.ConsumeResult;
import thecodex6824.thaumicaugmentation.api.impetus.node.NodeHelper;
import thecodex6824.thaumicaugmentation.api.impetus.node.prefab.SimpleImpetusConsumer;
import thecodex6824.thaumicaugmentation.api.util.DimensionalBlockPos;
import thecodex6824.thaumicaugmentation.common.tile.trait.IBreakCallback;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public abstract class TileImpetusComponent extends TileColorableMachineComponent implements ITickable, IBreakCallback, MachineComponentTile {
    private static final int CAPACITY = 100;
    protected int impetus = 0;
    protected final SimpleImpetusConsumer node = new SimpleImpetusConsumer(1, 0);
    private boolean toSetNodePos = true;

    @Override
    public void readCustomNBT(NBTTagCompound compound) {
        super.readCustomNBT(compound);
        this.impetus = compound.getInteger("impetus");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound compound) {
        super.writeCustomNBT(compound);
        compound.setInteger("impetus", impetus);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityImpetusNode.IMPETUS_NODE) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityImpetusNode.IMPETUS_NODE) {
            return CapabilityImpetusNode.IMPETUS_NODE.cast(this.node);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void onBlockBroken() {
        if (!this.world.isRemote) {
            NodeHelper.syncDestroyedImpetusNode(this.node);
        }

        this.node.destroy();
        ThaumicAugmentation.proxy.deregisterRenderableImpetusNode(this.node);
    }

    public boolean hasEnoughImpetus(int amount) {
        return impetus >= amount;
    }

    public void consumeImpetus(int amount) {
        if (hasEnoughImpetus(amount)) {
            impetus -= amount;
        }
    }

    private void setNodePos() {
        if (toSetNodePos) {
            node.setLocation(new DimensionalBlockPos(pos, world.provider.getDimension()));
            toSetNodePos = false;
        }
    }

    @Override
    public void update() {
        setNodePos();
        customUpdate();
    }

    protected abstract void customUpdate();

    public static class Input extends TileImpetusComponent {

        @Override
        public void customUpdate() {
            if (world.isRemote || impetus >= CAPACITY)
                return;
            ConsumeResult result = node.consume(1, true);
            if (result.energyConsumed == 1)
                impetus++;
            this.markDirty();
        }

        @Nullable
        @Override
        public MachineComponent<?> provideComponent() {
            return new MachineComponentImpetus(MachineComponent.IOType.INPUT, this);
        }
    }
}
