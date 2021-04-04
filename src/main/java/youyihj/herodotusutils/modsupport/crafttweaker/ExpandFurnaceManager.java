package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.recipes.IFurnaceManager;
import crafttweaker.mc1120.actions.ActionFurnaceRemoveRecipe;
import crafttweaker.mc1120.actions.IActionFurnaceRemoval;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenExpansion("crafttweaker.recipes.IFurnaceManager")
public class ExpandFurnaceManager {
    public static List<IActionFurnaceRemoval> recipesToRemove = new ArrayList<>();

    @ZenMethod
    public static void lateRemove(IFurnaceManager manager, IIngredient output, @Optional IIngredient input) {
        recipesToRemove.add(new ActionFurnaceRemoveRecipe(output, input));
    }
}
