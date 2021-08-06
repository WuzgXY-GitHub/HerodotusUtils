package youyihj.herodotusutils.modsupport.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import youyihj.herodotusutils.modsupport.jei.helper.ImpetusHelper;
import youyihj.herodotusutils.modsupport.jei.render.ImpetusRender;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;

import java.util.Collections;

/**
 * @author youyihj
 */
@JEIPlugin
public class JeiPlugin implements IModPlugin {
    public static IJeiHelpers JEI_HELPER;

    @Override
    public void register(IModRegistry registry) {
        JEI_HELPER = registry.getJeiHelpers();
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        registry.register(() -> Impetus.class, Collections.emptyList(), new ImpetusHelper(), new ImpetusRender());
    }
}
