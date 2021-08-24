package youyihj.herodotusutils.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import youyihj.herodotusutils.HerodotusUtils;

import java.util.function.Supplier;

/**
 * @author youyihj
 */
public enum EnumExtraGolemInfo implements IExtraGolemInfo {
    GOLD(null, 40.0, "gold_golem"),
    IRON(null, 20.0, "iron_golem");

    private Supplier<ItemStack> stackSupplier;
    private final double maxHealth;
    private final ResourceLocation entityTexture;

    EnumExtraGolemInfo(double maxHealth, ResourceLocation entityTexture) {
        IExtraGolemInfo.REGISTRY.put(getName(), this);
        this.maxHealth = maxHealth;
        this.entityTexture = entityTexture;
    }

    EnumExtraGolemInfo(Supplier<ItemStack> stackSupplier, double maxHealth, ResourceLocation entityTexture) {
        this(maxHealth, entityTexture);
        this.stackSupplier = stackSupplier;
    }

    EnumExtraGolemInfo(Supplier<ItemStack> stackSupplier, double maxHealth, String entityTexture) {
        this(stackSupplier, maxHealth, HerodotusUtils.rl("textures/entity/" + entityTexture + ".png"));
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    @Override
    public ItemStack getDroppingItem(EntityExtraGolem entity) {
        return stackSupplier == null ? ItemStack.EMPTY : stackSupplier.get();
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityExtraGolem entity) {
        return entityTexture;
    }
}
