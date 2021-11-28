package youyihj.herodotusutils.modsupport.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
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
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
        registry.register(() -> Impetus.class, Collections.emptyList(), new ImpetusHelper(), new ImpetusRender());
    }
}
