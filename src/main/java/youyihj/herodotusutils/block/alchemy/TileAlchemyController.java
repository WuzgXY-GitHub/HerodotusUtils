package youyihj.herodotusutils.block.alchemy;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import youyihj.herodotusutils.alchemy.IAlchemyModule;
import youyihj.herodotusutils.alchemy.IPipe;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
public class TileAlchemyController extends AbstractPipeTileEntity implements ITickable {
    /* package-private */ boolean lastRedstoneSignal = false;
    private final Map<BlockPos, IPipe> pipes = new HashMap<>();

    {
        startScanPipes();
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;
        work();
    }

    @Nullable
    @Override
    public TileAlchemyController getLinkedController() {
        return this;
    }

    @Override
    public void setLinkedController(TileAlchemyController tileAlchemyController) {
        // no-op
    }

    public void startScanPipes() {
        pipes.values().forEach(IPipe::unlinkController);
        pipes.clear();
        scanPipes(pos);
    }

    private void scanPipes(BlockPos pos) {
        addPipe(pos);
        Arrays.stream(EnumFacing.values())
                .map(pos::offset)
                .filter(((Predicate<BlockPos>) pipes::containsKey).negate())
                .forEach(this::scanPipes);
    }

    private void addPipe(BlockPos pipePos) {
        TileEntity tileEntity = world.getTileEntity(pipePos);
        if (tileEntity instanceof IPipe) {
            IPipe pipe = (IPipe) tileEntity;
            pipes.put(pipePos, pipe);
            pipe.setLinkedController(this);
        }
    }

    private void work() {
        if (world.getBlockState(pos).getValue(BlockAlchemyController.WORK_TYPE_PROPERTY).shouldWork(world, pos, this)) {
            pipes.forEach((pos, pipe) -> {
                if (pipe instanceof IAlchemyModule) {
                    ((IAlchemyModule) pipe).work();
                }
            });
            pipes.values().forEach(IPipe::afterModuleMainWork);
        }
    }
}
