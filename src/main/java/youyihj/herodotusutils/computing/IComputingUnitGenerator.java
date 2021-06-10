package youyihj.herodotusutils.computing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * @author youyihj
 */
public interface IComputingUnitGenerator extends IComputingUnitInteract {
    int generateAmount();

    default void generateToChunk(World world, BlockPos pos) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        chunk.getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null).generatePower(generateAmount(), pos, chunk);
    }
}
