package youyihj.herodotusutils.util;

import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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

    public static <T extends NBTBase> T getTag(NBTTagCompound nbtTagCompound, String key, Class<T> tagClass, @Nullable Supplier<T> defaultSupplier) {
        if (defaultSupplier == null)
            defaultSupplier = castNullSupplier();
        return Optional.ofNullable(nbtTagCompound.getTag(key))
                .filter(tagClass::isInstance)
                .map(tagClass::cast)
                .orElseGet(defaultSupplier);
    }

    private static final Supplier<?> NULL_SUPPLIER = () -> null;

    @SuppressWarnings("unchecked")
    private static <T> Supplier<T> castNullSupplier() {
        return ((Supplier<T>) NULL_SUPPLIER);
    }
}
