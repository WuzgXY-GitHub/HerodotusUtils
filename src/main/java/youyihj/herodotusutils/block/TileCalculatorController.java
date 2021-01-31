package youyihj.herodotusutils.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3i;
import youyihj.collision.multiblock.SimpleMultiblock;
import youyihj.collision.util.IBlockMatcher;
import youyihj.herodotusutils.computing.IComputingUnitGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
public class TileCalculatorController extends TileEntity implements ITickable, IComputingUnitGenerator {
    private static final SimpleMultiblock MULTIBLOCK;
    private static final List<Vec3i> MODULE_POSES = new ArrayList<>();

    static {
        MULTIBLOCK = new SimpleMultiblock((blockState) -> blockState.getBlock() instanceof BlockCalculatorController);
        MODULE_POSES.add(new Vec3i(2, 0, 0));
        MODULE_POSES.add(new Vec3i(-2, 0, 0));
        MODULE_POSES.add(new Vec3i(0, 0, 2));
        MODULE_POSES.add(new Vec3i(0, 0, -2));
        MODULE_POSES.add(new Vec3i(2, -4, 0));
        MODULE_POSES.add(new Vec3i(-2, -4, 0));
        MODULE_POSES.add(new Vec3i(0, -4, 2));
        MODULE_POSES.add(new Vec3i(0, -4, -2));
        MODULE_POSES.add(new Vec3i(2, -2, 2));
        MODULE_POSES.add(new Vec3i(-2, -2, 2));
        MODULE_POSES.add(new Vec3i(2, -2, -2));
        MODULE_POSES.add(new Vec3i(-2, -2, -2));
        IBlockMatcher matcher0 = IBlockMatcher.Impl.fromBlock(PlainBlock.STRUCTURE_BLOCK_1);
        IBlockMatcher matcher1 = matcher0.or(IBlockMatcher.Impl.fromBlock(BlockComputingModule.INSTANCE));
        IBlockMatcher matcher2 = IBlockMatcher.Impl.fromBlock(PlainBlock.STRUCTURE_BLOCK_2);
        IBlockMatcher matcher3 = IBlockMatcher.Impl.fromBlock(PlainBlock.STRUCTURE_BLOCK_3);

        MODULE_POSES.forEach(vec -> MULTIBLOCK.addElement(vec, matcher1));

        MULTIBLOCK.addElement(new Vec3i(1, 0, 1), matcher3)
                .addElement(new Vec3i(1, 0, -1), matcher3)
                .addElement(new Vec3i(-1, 0, 1), matcher3)
                .addElement(new Vec3i(-1, 0, -1), matcher3)
                .addElement(new Vec3i(1, 0, 0), matcher0)
                .addElement(new Vec3i(-1, 0, 0), matcher0)
                .addElement(new Vec3i(0, 0, 1), matcher0)
                .addElement(new Vec3i(0, 0, -1), matcher0)

                .addElement(new Vec3i(2, -1, 0), matcher0)
                .addElement(new Vec3i(-2, -1, 0), matcher0)
                .addElement(new Vec3i(0, -1, 2), matcher0)
                .addElement(new Vec3i(0, -1, -2), matcher0)
                .addElement(new Vec3i(2, -1, 1), matcher2)
                .addElement(new Vec3i(2, -1, -1), matcher2)
                .addElement(new Vec3i(-2, -1, 1), matcher2)
                .addElement(new Vec3i(-2, -1, -1), matcher2)
                .addElement(new Vec3i(-1, -1, 2), matcher2)
                .addElement(new Vec3i(1, -1, 2), matcher2)
                .addElement(new Vec3i(-1, -1, -2), matcher2)
                .addElement(new Vec3i(1, -1, -2), matcher2)

                .addElement(new Vec3i(2, -2, 0), matcher0)
                .addElement(new Vec3i(-2, -2, 0), matcher0)
                .addElement(new Vec3i(0, -2, 2), matcher0)
                .addElement(new Vec3i(0, -2, -2), matcher0)
                .addElement(new Vec3i(2, -2, 1), matcher0)
                .addElement(new Vec3i(2, -2, -1), matcher0)
                .addElement(new Vec3i(-2, -2, 1), matcher0)
                .addElement(new Vec3i(-2, -2, -1), matcher0)
                .addElement(new Vec3i(-1, -2, 2), matcher0)
                .addElement(new Vec3i(1, -2, 2), matcher0)
                .addElement(new Vec3i(-1, -2, -2), matcher0)
                .addElement(new Vec3i(1, -2, -2), matcher0)

                .addElement(new Vec3i(2, -3, 0), matcher0)
                .addElement(new Vec3i(-2, -3, 0), matcher0)
                .addElement(new Vec3i(0, -3, 2), matcher0)
                .addElement(new Vec3i(0, -3, -2), matcher0)
                .addElement(new Vec3i(2, -3, 1), matcher2)
                .addElement(new Vec3i(2, -3, -1), matcher2)
                .addElement(new Vec3i(-2, -3, 1), matcher2)
                .addElement(new Vec3i(-2, -3, -1), matcher2)
                .addElement(new Vec3i(-1, -3, 2), matcher2)
                .addElement(new Vec3i(1, -3, 2), matcher2)
                .addElement(new Vec3i(-1, -3, -2), matcher2)
                .addElement(new Vec3i(1, -3, -2), matcher2)

                .addElement(new Vec3i(1, -4, 1), matcher3)
                .addElement(new Vec3i(1, -4, -1), matcher3)
                .addElement(new Vec3i(-1, -4, 1), matcher3)
                .addElement(new Vec3i(-1, -4, -1), matcher3)
                .addElement(new Vec3i(1, -4, 0), matcher0)
                .addElement(new Vec3i(-1, -4, 0), matcher0)
                .addElement(new Vec3i(0, -4, 1), matcher0)
                .addElement(new Vec3i(0, -4, -1), matcher0);
    }

    private int capacity;

    public TileCalculatorController(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.capacity = compound.getInteger("capacity");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("capacity", this.capacity);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (world.getTotalWorldTime() % 20 == 0) {
            generateToChunk(world, pos);
        }
    }

    @Override
    public int generateAmount() {
        if (!MULTIBLOCK.match(world, pos)) {
            return 0;
        }
        return Math.min(capacity, MODULE_POSES.stream()
                .map(vec -> pos.add(vec))
                .map(posOffset -> world.getTileEntity(posOffset))
                .filter(TileComputingModule.class::isInstance)
                .map(TileComputingModule.class::cast)
                .map(TileComputingModule::getGeneratePower)
                .reduce(Integer::sum)
                .orElse(0));
    }
}
