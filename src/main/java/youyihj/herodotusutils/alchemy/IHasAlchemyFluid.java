package youyihj.herodotusutils.alchemy;

import net.minecraftforge.fluids.FluidStack;

/**
 * @author youyihj
 */
public interface IHasAlchemyFluid extends IPipe {
    FluidStack getContainedFluid();

    void handleInput(FluidStack input);

    FluidStack handleOutput();
}
