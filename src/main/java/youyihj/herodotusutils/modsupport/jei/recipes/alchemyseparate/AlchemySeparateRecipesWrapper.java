package youyihj.herodotusutils.modsupport.jei.recipes.alchemyseparate;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import youyihj.herodotusutils.recipe.AlchemyRecipes.Separate;
import youyihj.herodotusutils.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlchemySeparateRecipesWrapper implements IRecipeWrapper {

    private final Separate separate;

    public AlchemySeparateRecipesWrapper(Separate separate) {
        this.separate = separate;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<FluidStack> output = Arrays.stream(separate.getOutput()).map(Util::getDefaultFluidStack).collect(Collectors.toList());

        ingredients.setOutputs(VanillaTypes.FLUID, output);
        ingredients.setInput(VanillaTypes.FLUID, Util.getDefaultFluidStack(separate.getInput()));
    }
}
