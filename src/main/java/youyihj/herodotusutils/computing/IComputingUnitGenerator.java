package youyihj.herodotusutils.computing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public interface IComputingUnitGenerator extends IComputingUnitInteract {
    int generateAmount();

    default void generateToChunk(World world, BlockPos pos) {
        world.getChunkFromBlockCoords(pos).getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null)
                .generatePower(generateAmount(), pos);
    }
}
