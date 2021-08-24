package youyihj.herodotusutils.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public interface IExtraGolemInfo {
    Map<String, IExtraGolemInfo> REGISTRY = new HashMap<>();

    String getName();

    ItemStack getDroppingItem(EntityExtraGolem entity);

    double getMaxHealth();

    ResourceLocation getEntityTexture(EntityExtraGolem entity);
}
