package youyihj.herodotusutils.block.alchemy;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import youyihj.herodotusutils.alchemy.IAlchemyModule;
import youyihj.herodotusutils.alchemy.IHasAlchemyFluid;
import youyihj.herodotusutils.recipe.AlchemyRecipes;
import youyihj.herodotusutils.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author youyihj
 */
public class TileAlchemyCrafter extends AbstractPipeTileEntity implements IAlchemyModule {
    @Override
    public void work() {
        List<IHasAlchemyFluid> nearPipes = new ArrayList<>();
        for (EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL) {
            BlockPos offset = pos.offset(enumFacing);
            Optional<IHasAlchemyFluid> tileEntity = Util.getTileEntity(world, offset, IHasAlchemyFluid.class);
            if (tileEntity.isPresent()) {
                IHasAlchemyFluid iHasAlchemyFluid = tileEntity.get();
                if (iHasAlchemyFluid.outputSide() == enumFacing.getOpposite()) {
                    nearPipes.add(iHasAlchemyFluid);
                }
            }
        }
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
