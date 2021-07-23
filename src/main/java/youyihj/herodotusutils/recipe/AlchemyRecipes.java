package youyihj.herodotusutils.recipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.Fluid;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
@ZenClass("mods.hdsutils.Alchemy")
public class AlchemyRecipes {
    private static final List<Craft> craftRecipes = new ArrayList<>();

    @ZenMethod
    public static void addCraftingRecipe(ILiquidStack output, ILiquidStack[] input) {
        CraftTweakerAPI.apply(new AddCraftingRecipeAction(output, input));
    }

    @Nullable
    public static Fluid getOutputFor(Fluid[] input) {
        return craftRecipes.stream()
                .filter(recipe -> recipe.checkInput(input))
                .map(Craft::getOutput)
                .findFirst()
                .orElse(null);
    }

    public static class Craft {
        private final Fluid output;
        private final Fluid[] input;

        public Craft(Fluid output, Fluid[] input) {
            this.output = output;
            this.input = input;
        }

        public Fluid getOutput() {
            return output;
        }

        public Fluid[] getInput() {
            return input;
        }

        public boolean checkInput(Fluid[] toCheck) {
            if (input.length != toCheck.length)
                return false;
            for (Fluid fluid : input) {
                if (!ArrayUtils.contains(toCheck, fluid))
                    return false;
            }
            return true;
        }
    }

    public static class AddCraftingRecipeAction implements IAction {
        private final ILiquidStack output;
        private final ILiquidStack[] input;

        public AddCraftingRecipeAction(ILiquidStack output, ILiquidStack[] input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            if (input.length > 4) {
                throw new IllegalArgumentException();
            }
            Fluid mcOutput = CraftTweakerMC.getFluid(output.getDefinition());
            Fluid[] mcInput = Arrays.stream(input)
                    .map(ILiquidStack::getDefinition)
                    .map(CraftTweakerMC::getFluid)
                    .toArray(Fluid[]::new);
            craftRecipes.add(new Craft(mcOutput, mcInput));
        }

        @Override
        public String describe() {
            return "Adding a new crafting alchemy recipe, output: " + output.toCommandString() + "input: " + Arrays.stream(input).map(IIngredient::toCommandString).collect(Collectors.joining(", ", "[", "]"));
        }
    }
}
