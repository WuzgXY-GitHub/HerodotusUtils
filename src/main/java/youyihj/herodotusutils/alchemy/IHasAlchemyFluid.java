package youyihj.herodotusutils.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public interface IHasAlchemyFluid extends IPipe {
    @Nullable
    Fluid getContainedFluid();

    /**
     * @param input the input stack
     * @return if the input operator is success
     */
    boolean handleInput(Fluid input, EnumFacing inputSide);

    void emptyFluid();
}
