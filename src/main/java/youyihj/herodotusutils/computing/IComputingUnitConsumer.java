package youyihj.herodotusutils.computing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public interface IComputingUnitConsumer extends IComputingUnitInteract {
    int consumeAmount();

    default boolean consumeToChunk(World world, BlockPos pos) {
        IComputingUnit computingUnit = world.getChunkFromBlockCoords(pos).getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null);
        computingUnit.consumePower(consumeAmount(), pos);
        return computingUnit.canWork();
    }
}
