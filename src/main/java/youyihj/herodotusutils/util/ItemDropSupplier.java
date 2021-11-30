package youyihj.herodotusutils.util;

import net.minecraft.item.ItemStack;

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

    @Override
    public ItemStack get() {
        if (cachedStack == null || cachedStack.isEmpty()) {
            cachedStack = supplier.get();
            return cachedStack;
        } else {
            supplier = null;
            return cachedStack.copy();
        }
    }
}
