package youyihj.herodotusutils.block.modularmachine.tile;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileThaumcraft;

/**
 * @author ikexing
 */
public class TileAspectListProvider extends TileThaumcraft implements MachineComponentTile, IAspectSource, IEssentiaTransport {

    public static final int MAX_ASPECT = 6;
    public static final int MAX_AMOUNT = 250;

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
        if (this.aspects.size() > 6) {
            return this.aspects.getAmount(tag) > 250;
        }
        return true;
    }

    @Override
    public int addToContainer(Aspect tag, int amount) {
        if (amount != 0 && this.aspects.size() < MAX_ASPECT) {
            int currentAmount = this.aspects.getAmount(tag);
            if (currentAmount < MAX_AMOUNT) {
                int added = Math.min(amount, MAX_AMOUNT - currentAmount);
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
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return null;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing face) {
        return this.canOutputTo(face) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing face) {
        return this.canInputFrom(face) ? amount - this.addToContainer(aspect, amount) : 0;
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
    public MachineComponent provideComponent() {
        return new MachineComponentAspectListProvider(this, IOType.INPUT);
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return true;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return false;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }
}
