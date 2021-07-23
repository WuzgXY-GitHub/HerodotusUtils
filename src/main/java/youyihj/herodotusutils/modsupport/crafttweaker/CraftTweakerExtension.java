package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import youyihj.herodotusutils.recipe.AlchemyRecipes;
import youyihj.herodotusutils.recipe.ClipManager;

public class CraftTweakerExtension {
    public static void registerAllClasses() {
        CraftTweakerAPI.registerClass(CrTMachineRecipeCompleteEvent.class);
        CraftTweakerAPI.registerClass(CrTXPUtil.class);
        CraftTweakerAPI.registerClass(ExpandEventManger.class);
        CraftTweakerAPI.registerClass(MaterialPartOreExpansion.class);
        CraftTweakerAPI.registerClass(ClipManager.ClipInfoWriter.class);
        CraftTweakerAPI.registerClass(CrTMachineRecipeStartEvent.class);
        CraftTweakerAPI.registerClass(MagicPrimer.class);
        CraftTweakerAPI.registerClass(ExpandDisassembler.class);
        CraftTweakerAPI.registerClass(AlchemyRecipes.class);
    }
}
