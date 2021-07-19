package youyihj.herodotusutils.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;

/**
 * @author youyihj
 */
public interface IHasAlchemyFluid extends IPipe {
    Fluid getContainedFluid();

    /**
     * @param input the input stack
     * @return if the input operator is success
     */
    boolean handleInput(Fluid input, EnumFacing inputSide);

    void emptyFluid();
}
