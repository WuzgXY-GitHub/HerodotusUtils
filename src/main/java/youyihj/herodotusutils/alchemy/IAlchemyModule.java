package youyihj.herodotusutils.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import java.util.Optional;

/**
 * @author youyihj
 */
public interface IAlchemyModule extends IPipe {
    static void transferFluid(IHasAlchemyFluid from, IHasAlchemyFluid to, EnumFacing outputSide) {
        Fluid containedFluid = from.getContainedFluid();
        if (containedFluid != null && to.handleInput(containedFluid, outputSide.getOpposite())) {
            from.emptyFluid();
        }
    }

    static void transferFluid(IHasAlchemyFluid from, World world, BlockPos pos, EnumFacing outputSide) {
        Optional.ofNullable(world.getTileEntity(pos.offset(outputSide)))
                .filter(IHasAlchemyFluid.class::isInstance)
                .map(IHasAlchemyFluid.class::cast)
                .ifPresent(iHasAlchemyFluid -> transferFluid(from, iHasAlchemyFluid, outputSide));
    }

    void work();
}
