package youyihj.herodotusutils.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import youyihj.herodotusutils.computing.ComputingUnitHandler;
import youyihj.herodotusutils.computing.IComputingUnit;
import youyihj.herodotusutils.computing.IComputingUnitConsumer;

/**
 * @author youyihj
 */
public class TileTransporter extends TileEntity implements ITickable, IComputingUnitConsumer {
    public TileTransporter(int capacity) {
        this.capacity = capacity;
    }

    private int capacity;
    private boolean working;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.capacity = compound.getInteger("capacity");
        super.readFromNBT(compound);
        update0();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("capacity", this.capacity);
        return super.writeToNBT(compound);
    }

    private void update0() {
        this.consumeToChunk(world, pos);
        IComputingUnit computingUnit = world.getChunkFromBlockCoords(pos).getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null);

        if (computingUnit.canWork() != working) {
            this.working = computingUnit.canWork();
            world.setBlockState(pos, BlockTransporter.getBlockMap().get(capacity).getDefaultState().withProperty(BlockTransporter.ACTIVATED, working));
        }
    }

    @Override
    public int consumeAmount() {
        return capacity;
    }

    @Override
    public void update() {
        if (world.isRemote || world.getTotalWorldTime() % 80 != 14)
            return;
        update0();
    }
}
