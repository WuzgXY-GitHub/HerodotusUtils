package youyihj.herodotusutils.block.alchemy;

import net.minecraft.util.EnumFacing;
import youyihj.herodotusutils.alchemy.IAlchemyModule;

/**
 * @author youyihj
 */
public class TileAlchemySeparatorTank extends AbstractHasAlchemyFluidTileEntity implements IAlchemyModule {
    @Override
    public void work() {
        IAlchemyModule.transferFluid(this, world, pos, EnumFacing.DOWN);
    }

    @Override
    public EnumFacing inputSide() {
        return null;
    }

    @Override
    public EnumFacing outputSide() {
        return EnumFacing.DOWN;
    }
}
