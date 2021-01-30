package youyihj.herodotusutils.computing;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public interface IComputingUnit {
    int totalGeneratePower();

    int totalConsumePower();

    void generatePower(int value, BlockPos pos);

    void consumePower(int value, BlockPos pos);

    boolean canWork();

    void removeInvalidEntry(IBlockAccess world);

    final class Impl implements IComputingUnit {
        private Map<BlockPos, Integer> generateDevices = new HashMap<>();
        private Map<BlockPos, Integer> consumeDevices = new HashMap<>();

        @Override
        public int totalGeneratePower() {
            return generateDevices.values().stream().reduce(Integer::sum).orElse(0);
        }

        @Override
        public int totalConsumePower() {
            return consumeDevices.values().stream().reduce(Integer::sum).orElse(0);
        }

        @Override
        public void generatePower(int value, BlockPos pos) {
            generateDevices.put(pos, value);
        }

        @Override
        public void consumePower(int value, BlockPos pos) {
            consumeDevices.put(pos, value);
        }

        @Override
        public boolean canWork() {
            return this.totalGeneratePower() > this.totalConsumePower();
        }

        @Override
        public void removeInvalidEntry(IBlockAccess world) {
            for (BlockPos pos : generateDevices.keySet()) {
                if (!(world.getTileEntity(pos) instanceof IComputingUnitGenerator)) {
                    generateDevices.remove(pos);
                }
            }
            for (BlockPos pos : consumeDevices.keySet()) {
                if (!((world.getTileEntity(pos)) instanceof IComputingUnitConsumer)) {
                    consumeDevices.remove(pos);
                }
            }
        }
    }
}
