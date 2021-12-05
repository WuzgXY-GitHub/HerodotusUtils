package youyihj.herodotusutils.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class ItemDropSupplier implements Supplier<ItemStack> {
    private Supplier<ItemStack> supplier;
    private ItemStack cachedStack;

    private ItemDropSupplier(Supplier<ItemStack> supplier) {
        this.supplier = supplier;
    }

    public static ItemDropSupplier of(Supplier<ItemStack> supplier) {
        return new ItemDropSupplier(supplier);
    }

    public static ItemDropSupplier ofItem(Item item) {
        return new ItemDropSupplier(() -> new ItemStack(item));
    }

    public static ItemDropSupplier ofOreDict(String oreDict) {
        return new ItemDropSupplier(() -> {
            NonNullList<ItemStack> ores = OreDictionary.getOres(oreDict);
            if (ores.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                return ores.get(0);
            }
        });
    }

    @Override
    public ItemStack get() {
        if (cachedStack == null || cachedStack.isEmpty()) {
            cachedStack = supplier.get();
            if (cachedStack == null) {
                return ItemStack.EMPTY;
            }
            return cachedStack;
        } else {
            supplier = null;
            return cachedStack.copy();
        }
    }
}
