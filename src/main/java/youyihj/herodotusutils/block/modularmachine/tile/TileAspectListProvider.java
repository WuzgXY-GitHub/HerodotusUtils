package youyihj.herodotusutils.block.modularmachine.tile;

import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileThaumcraft;

public abstract class TileAspectListProvider extends TileThaumcraft implements MachineComponentTile, IAspectSource, IEssentiaTransport, ITickable {

    private final Integer MAX_ASPECT = 6;
    private final Integer MAX_AMOUNT = 250;

    public AspectList aspects = new AspectList();
    public Aspect currentSuction = null;
    public Integer amount = 0;

    @Override
    public void update() {
        if (!super.world.isRemote) {
            Aspect aspect = aspects.getAspects()[0];
            if (aspects.getAspects().length != 0) {
                currentSuction = aspect;
                amount = aspects.getAmount(aspect);
            } else {
                amount = 0;
                currentSuction = null;
            }
            for (EnumFacing face : EnumFacing.VALUES)
                fill(face);
        }
    }

    private void fill(EnumFacing face) {
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos, face);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport) te;
            if (!ic.canOutputTo(face.getOpposite()))
                return;
            Aspect ta = null;
            if (this.currentSuction != null && this.amount > 0)
                ta = this.currentSuction;
            if (ic.getEssentiaAmount(face.getOpposite()) > 0 && ic.getSuctionAmount(face.getOpposite()) < this.getSuctionAmount(face) && this.getSuctionAmount(face) >= ic.getMinimumSuction())
                ta = ic.getEssentiaType(face.getOpposite());
            if (ta != null && ic.getSuctionAmount(face.getOpposite()) < this.getSuctionAmount(face))
                this.addToContainer(ta, ic.takeEssentia(ta, 1, face.getOpposite()));
        }
    }

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
        return this.currentSuction == null || tag.equals(this.currentSuction);
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
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return this.currentSuction;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return this.amount;
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
        return this.currentSuction;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return this.amount;
    }

    @Override
    public int getMinimumSuction() {
        return this.currentSuction != null ? 64 : 32;
    }

    @Nullable
    @Override
    public MachineComponent provideComponent() {
        return null;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    public static class Output extends TileAspectListProvider {

        @Nullable
        public MachineComponent provideComponent() {
            return new MachineComponentAspectListProvider(this, IOType.OUTPUT);
        }

        @Override
        public boolean canInputFrom(EnumFacing face) {
            return false;
        }

        @Override
        public boolean canOutputTo(EnumFacing face) {
            return true;
        }
    }

    public static class Input extends TileAspectListProvider {

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
    }
}
