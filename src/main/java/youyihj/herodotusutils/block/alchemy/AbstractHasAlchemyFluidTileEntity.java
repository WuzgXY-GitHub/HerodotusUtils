package youyihj.herodotusutils.block.alchemy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import youyihj.herodotusutils.alchemy.IHasAlchemyFluid;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public abstract class AbstractHasAlchemyFluidTileEntity extends AbstractPipeTileEntity implements IHasAlchemyFluid {
    protected Fluid content;
    private Fluid cachedContent;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        content = FluidRegistry.getFluid(compound.getString("fluid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (content != null) {
            compound.setString("fluid", content.getName());
        }
        return compound;
    }

    @Override
    @Nullable
    public Fluid getContainedFluid() {
        return content;
    }

    @Override
    public boolean handleInput(Fluid input, EnumFacing inputSide) {
        if (content == null && inputSide == inputSide()) {
            cachedContent = input;
            return true;
        }
        return false;
    }

    @Override
    public void afterModuleMainWork() {
        if (cachedContent != null && content == null) {
            content = cachedContent;
            cachedContent = null;
        }
    }

    @Override
    public void emptyFluid() {
        content = null;
    }
}
