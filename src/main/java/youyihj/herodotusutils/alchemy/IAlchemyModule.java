package youyihj.herodotusutils.alchemy;

import net.minecraft.util.EnumFacing;

/**
 * @author youyihj
 */
public interface IAlchemyModule extends IPipe {
    void work(IHasAlchemyFluid input, IHasAlchemyFluid output);

    EnumFacing getInputSide();

    EnumFacing getOutputSide();
}
