package youyihj.herodotusutils.modsupport.jei.helper;

import mezz.jei.api.ingredients.IIngredientHelper;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ImpetusHelper implements IIngredientHelper<Impetus> {
    @Nullable
    @Override
    public Impetus getMatch(Iterable<Impetus> ingredients, Impetus ingredientToMatch) {
        for (Impetus ingredient : ingredients) {
            if (ingredient.getAmount() == ingredientToMatch.getAmount()) {
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public String getDisplayName(Impetus ingredient) {
        return "Impetus";
    }

    @Override
    public String getUniqueId(Impetus ingredient) {
        return "impetus";
    }

    @Override
    public String getWildcardId(Impetus ingredient) {
        return "impetus";
    }

    @Override
    public String getModId(Impetus ingredient) {
        return "hdsutils";
    }

    @Override
    public String getResourceId(Impetus ingredient) {
        return "impetus";
    }

    @Override
    public Impetus copyIngredient(Impetus ingredient) {
        return new Impetus(ingredient.getAmount());
    }

    @Override
    public String getErrorInfo(@Nullable Impetus ingredient) {
        return null;
    }
}
