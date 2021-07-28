package youyihj.herodotusutils.block.modularmachine.tile;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileAspectListProvider extends TileThaumcraft implements MachineComponentTile, IAspectContainer, IEssentiaTransport {

    private final Integer MAX_ASPECT = 6;
    private final Integer MAX_AMOUNT = 250;

    public Aspect currentSuction = null;
    public AspectList aspects = new AspectList();

    @Override
    public void readSyncNBT(NBTTagCompound nbt) {
        this.aspects.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeSyncNBT(NBTTagCompound nbt) {
        this.aspects.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public AspectList getAspects() {
        return this.aspects;
    }

    @Override
    public void setAspects(AspectList aspects) {
        this.aspects = aspects;
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return true;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        if (amount != 0 && this.aspects.size() < MAX_ASPECT) {
            int currentAmount = this.aspects.getAmount(tag);
            if (currentAmount < this.MAX_AMOUNT) {
                int added = Math.min(amount, 250 - currentAmount);
                this.aspects.add(tag, amount);
                amount -= added;
            }
            this.syncTile(false);
            this.markDirty();
        }
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect tag, int amount) {
        if (this.aspects.getAmount(tag) >= amount) {
            this.aspects.remove(tag, amount);
            this.syncTile(false);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tag, int amount) {
        return this.aspects.getAmount(tag) >= amount;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public int containerContains(Aspect tag) {
        return this.aspects.getAmount(tag);
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        return true;
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return true;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return true;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {
        this.currentSuction = aspect;
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return this.currentSuction;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return this.currentSuction != null ? 128 : 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing face) {
        return this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing face) {
        return amount - this.addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        return null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Nullable
    @Override
    public MachineComponent provideComponent() {
        return null;
    }

    public static class Output extends TileAspectListProvider {

        @Nullable
        public MachineComponent provideComponent() {
            return new MachineComponentAspectListProvider(this, IOType.OUTPUT);
        }
    }

    public static class Input extends TileAspectListProvider {

        @Nullable
        public MachineComponent provideComponent() {
            return new MachineComponentAspectListProvider(this, IOType.INPUT);
        }
    }
}
