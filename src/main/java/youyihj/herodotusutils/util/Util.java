package youyihj.herodotusutils.util;

import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

    public static <T> Optional<T> getTileEntity(World world, BlockPos pos, Class<T> tileEntityClass) {
        return Util.getTileEntity(world, pos)
                .filter(tileEntityClass::isInstance)
                .map(tileEntityClass::cast);
    }

    public static Optional<TileEntity> getTileEntity(World world, BlockPos pos) {
        return Optional.ofNullable(world.getTileEntity(pos));
    }

    public static <T> Optional<T> getCapability(World world, BlockPos pos, Capability<T> capability, @Nullable EnumFacing facing) {
        return getTileEntity(world, pos).map(te -> te.getCapability(capability, facing));
    }

    public static void onBreakContainer(World worldIn, BlockPos pos) {
        Util.getCapability(worldIn, pos, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                .ifPresent(itemHandler -> {
                    int slots = itemHandler.getSlots();
                    for (int i = 0; i < slots; i++) {
                        InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemHandler.getStackInSlot(slots).copy());
                    }
                });
    }
}
