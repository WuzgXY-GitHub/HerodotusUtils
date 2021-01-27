package youyihj.herodotusutils.fluid;

import net.minecraftforge.fluids.Fluid;
import youyihj.herodotusutils.HerodotusUtils;

import java.awt.*;

/**
 * @author youyihj
 */
public class FluidMercury extends Fluid {
    private FluidMercury() {
        super("mercury", HerodotusUtils.rl("fluids/liquid"), HerodotusUtils.rl("fluids/liquid_flow"), new Color(0xdddddd));
    }

    public static final FluidMercury INSTANCE = new FluidMercury();
}
