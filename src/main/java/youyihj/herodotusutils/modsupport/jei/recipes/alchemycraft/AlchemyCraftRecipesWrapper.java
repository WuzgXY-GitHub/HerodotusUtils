package youyihj.herodotusutils.modsupport.jei.recipes.alchemycraft;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;
import youyihj.herodotusutils.recipe.AlchemyRecipes.Craft;
import youyihj.herodotusutils.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlchemyCraftRecipesWrapper implements IRecipeWrapper {

    private final Craft craft;

    public AlchemyCraftRecipesWrapper(Craft craft) {
        this.craft = craft;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<FluidStack> input = Arrays.stream(craft.getInput()).map(Util::getDefaultFluidStack).collect(Collectors.toList());

        ingredients.setInputs(VanillaTypes.FLUID, input);
        ingredients.setOutput(VanillaTypes.FLUID, Util.getDefaultFluidStack(craft.getOutput()));
    }
}
