package youyihj.herodotusutils.alchemy;

import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * @author youyihj
 */
public interface IAlchemyInterface extends IHasAlchemyFluid {
    IFluidHandler getLinkedFluidHandler();

    void handleInteract();
}
