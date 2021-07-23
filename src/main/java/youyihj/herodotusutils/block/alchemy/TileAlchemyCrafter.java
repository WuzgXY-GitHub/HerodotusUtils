package youyihj.herodotusutils.block.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import youyihj.herodotusutils.alchemy.IAlchemyModule;
import youyihj.herodotusutils.alchemy.IHasAlchemyFluid;
import youyihj.herodotusutils.recipe.AlchemyRecipes;
import youyihj.herodotusutils.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class TileAlchemyCrafter extends AbstractPipeTileEntity implements IAlchemyModule {
    @Override
    public void work() {
        List<IHasAlchemyFluid> nearPipes = Arrays.stream(EnumFacing.Plane.HORIZONTAL.facings())
                .map(pos::offset)
                .map(offset -> Util.getTileEntity(world, offset, IHasAlchemyFluid.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        Fluid[] input = nearPipes.stream()
                .map(IHasAlchemyFluid::getContainedFluid)
                .filter(Objects::nonNull)
                .toArray(Fluid[]::new);
        Fluid output = AlchemyRecipes.getOutputFor(input);
        if (output != null) {
            Util.getTileEntity(world, pos.down(), IHasAlchemyFluid.class)
                    .ifPresent(pipe -> pipe.handleInput(output, EnumFacing.UP));
        }
        nearPipes.forEach(IHasAlchemyFluid::emptyFluid);
    }
}
