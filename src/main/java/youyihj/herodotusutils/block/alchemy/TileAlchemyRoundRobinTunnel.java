package youyihj.herodotusutils.block.alchemy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import youyihj.herodotusutils.alchemy.IAlchemyModule;
import youyihj.herodotusutils.alchemy.IHasAlchemyFluidModule;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author youyihj
 */
public class TileAlchemyRoundRobinTunnel extends AbstractHasAlchemyFluidTileEntity implements IHasAlchemyFluidModule {
    /* package-private */ final EnumFacing[] facingQuery = new EnumFacing[]{null, null, null, null};
    private byte nextIndex = 0;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("nextIndex", nextIndex);
        byte[] temp = new byte[facingQuery.length];
        for (int i = 0; i < facingQuery.length; i++) {
            EnumFacing enumFacing = facingQuery[i];
            temp[i] = (byte) ((enumFacing == null) ? -1 : enumFacing.getHorizontalIndex());
        }
        compound.setByteArray("facingQuery", temp);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        nextIndex = compound.getByte("nextIndex");
        byte[] facingQuerySource = compound.getByteArray("facingQuery");
        for (int i = 0; i < 4; i++) {
            byte b = facingQuerySource[i];
            if (b != -1) {
                facingQuery[i] = EnumFacing.getHorizontal(b);
            }
        }
    }

    @Override
    public void work() {
        if (content == null)
            return;
        EnumFacing nextOutputSide = getNextOutputSide(true);
        if (nextOutputSide != null) {
            IAlchemyModule.transferFluid(this, world, pos, nextOutputSide);
        }
    }

    @Override
    protected EnumFacing allowInputSide() {
        return EnumFacing.UP;
    }

    public void putFacing(EnumFacing facing) {
        int firstNullIndex = -1;
        for (int i = 0; i < facingQuery.length; i++) {
            if (facingQuery[i] == null && firstNullIndex == -1) {
                firstNullIndex = i;
            }
            if (facingQuery[i] == facing) {
                facingQuery[i] = null;
                return;
            }
        }
        facingQuery[firstNullIndex] = facing;
    }

    public EnumFacing getNextOutputSide(boolean next) {
        if (Arrays.stream(facingQuery).allMatch(Objects::isNull))
            return null;
        do {
            if (nextIndex >= facingQuery.length)
                nextIndex = 0;
        } while (facingQuery[nextIndex++] == null);
        EnumFacing result = facingQuery[--nextIndex];
        if (next)
            nextIndex++;
        return result;
    }
}
