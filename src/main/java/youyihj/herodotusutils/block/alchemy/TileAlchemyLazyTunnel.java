package youyihj.herodotusutils.block.alchemy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author youyihj
 */
public class TileAlchemyLazyTunnel extends TileAlchemyTunnel {
    private static final int MAX_BOUND = 8;
    private static final int MIN_BOUND = 1;
    private int bound;
    private int counter;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("bound", bound);
        compound.setInteger("counter", counter);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.bound = compound.getInteger("bound");
        this.counter = compound.getInteger("counter");
    }

    @Override
    public void work() {
        if (content == null)
            return;
        counter++;
        if (bound == counter) {
            counter = 0;
            super.work();
        }
    }

    public void updateBound() {
        bound++;
        if (bound > MAX_BOUND) {
            bound = MIN_BOUND;
        }
    }

    public int getBound() {
        return bound;
    }

    public int getCounter() {
        return counter;
    }
}
