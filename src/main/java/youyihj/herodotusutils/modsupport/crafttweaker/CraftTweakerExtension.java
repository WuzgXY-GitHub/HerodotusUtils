package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import youyihj.herodotusutils.recipe.ClipManager;

public class CraftTweakerExtension {
    public static void registerAllClasses() {
        CraftTweakerAPI.registerClass(CrTMachineRecipeCompletedEvent.class);
        CraftTweakerAPI.registerClass(CrTXPUtil.class);
        CraftTweakerAPI.registerClass(ExpandEventManger.class);
        CraftTweakerAPI.registerClass(MaterialPartOreExpansion.class);
        CraftTweakerAPI.registerClass(ClipManager.ClipInfoWriter.class);
    }
}
