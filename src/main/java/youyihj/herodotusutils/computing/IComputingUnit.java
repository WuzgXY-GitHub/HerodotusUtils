package youyihj.herodotusutils.computing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.Chunk;
import youyihj.herodotusutils.computing.event.ComputingUnitChangeEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public interface IComputingUnit {
    int totalGeneratePower();

    int totalConsumePower();

    void generatePower(int value, BlockPos pos, Chunk chunk);

    void consumePower(int value, BlockPos pos, Chunk chunk);

    boolean canWork();

    void removeInvalidEntry(IBlockAccess world);

    class Impl implements IComputingUnit {
        private final Map<BlockPos, Integer> generateDevices = new HashMap<>();
        private final Map<BlockPos, Integer> consumeDevices = new HashMap<>();

        @Override
        public int totalGeneratePower() {
            return generateDevices.values().stream().reduce(Integer::sum).orElse(0);
        }

        @Override
        public int totalConsumePower() {
            return consumeDevices.values().stream().reduce(Integer::sum).orElse(0);
        }

        @Override
        public void generatePower(int value, BlockPos pos, Chunk chunk) {
            generateDevices.put(pos, value);
            new ComputingUnitChangeEvent(this, chunk).post();
        }

        @Override
        public void consumePower(int value, BlockPos pos, Chunk chunk) {
            consumeDevices.put(pos, value);
            new ComputingUnitChangeEvent(this, chunk).post();
        }

        @Override
        public boolean canWork() {
            return this.totalGeneratePower() >= this.totalConsumePower();
        }

        @Override
        public void removeInvalidEntry(IBlockAccess world) {
            generateDevices.keySet().removeIf(pos -> !(world.getTileEntity(pos) instanceof IComputingUnitGenerator));
            consumeDevices.keySet().removeIf(pos -> !(world.getTileEntity(pos) instanceof IComputingUnitConsumer));
        }
    }
}
