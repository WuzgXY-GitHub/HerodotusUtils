package youyihj.herodotusutils.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;

/**
 * @author youyihj
 */
public interface IAlchemyModule extends IPipe {
    static void transformFluid(IHasAlchemyFluid from, IHasAlchemyFluid to, EnumFacing outputSide) {
        Fluid containedFluid = from.getContainedFluid();
        if (containedFluid != null && to.handleInput(containedFluid, outputSide.getOpposite())) {
            from.emptyFluid();
        }
    }

    void work();
}
