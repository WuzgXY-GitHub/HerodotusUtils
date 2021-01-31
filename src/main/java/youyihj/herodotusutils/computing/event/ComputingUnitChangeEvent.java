package youyihj.herodotusutils.computing.event;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import youyihj.herodotusutils.computing.IComputingUnit;

/**
 * @author youyihj
 */
public class ComputingUnitChangeEvent extends Event {
    private final IComputingUnit computingUnit;
    private final Chunk chunk;

    public ComputingUnitChangeEvent(IComputingUnit computingUnit, Chunk chunk) {
        this.computingUnit = computingUnit;
        this.chunk = chunk;
    }

    public IComputingUnit getComputingUnit() {
        return computingUnit;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public World getWorld() {
        return chunk.getWorld();
    }

    public void post() {
        MinecraftForge.EVENT_BUS.post(this);
    }
}
