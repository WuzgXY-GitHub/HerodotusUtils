package youyihj.herodotusutils.util;

import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public final class Util {
    private Util() {
    }

    public static IData createDataMap(String key, IData value) {
        Map<String, IData> temp = new HashMap<>();
        temp.put(key, value);
        return new DataMap(temp, true);
    }

    public static int sumFastIntCollection(IntCollection intCollection) {
        IntIterator iterator = intCollection.iterator();
        int s = 0;
        while (iterator.hasNext()) {
            s += iterator.nextInt();
        }
        return s;
    }

    public static <T> Optional<T> getTileEntity(World world, BlockPos pos, Class<T> tileEntityClass) {
        return Util.getTileEntity(world, pos)
                .filter(tileEntityClass::isInstance)
                .map(tileEntityClass::cast);
    }

    public static Optional<TileEntity> getTileEntity(World world, BlockPos pos) {
        return Optional.ofNullable(world.getTileEntity(pos));
    }

    public static List<FluidStack> getDefaultFluidStacks(Fluid[] fluid) {
        return Arrays.stream(fluid).map(Util::getDefaultFluidStack).collect(Collectors.toList());
    }

    public static FluidStack getDefaultFluidStack(Fluid fluid) {
        return new FluidStack(fluid, 1000);
    }
}
