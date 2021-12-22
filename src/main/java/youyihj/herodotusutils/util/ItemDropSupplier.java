package youyihj.herodotusutils.util;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class ItemDropSupplier extends Lazy<ItemStack, ItemStack> {

    private ItemDropSupplier(Supplier<ItemStack> supplier) {
        super(supplier, ((Predicate<ItemStack>) ItemStack::isEmpty).negate(), ItemStack::copy);
    }

    public static ItemDropSupplier of(Supplier<ItemStack> supplier) {
        return new ItemDropSupplier(supplier);
    }

    @Override
    public ItemStack get() {
        return getOptional().orElse(ItemStack.EMPTY);
    }
}
