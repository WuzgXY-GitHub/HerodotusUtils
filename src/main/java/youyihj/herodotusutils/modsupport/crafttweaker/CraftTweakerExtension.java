package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.CraftTweakerAPI;

public class CraftTweakerExtension {
    public static void registerAllClasses() {
        CraftTweakerAPI.registerClass(CrTMachineRecipeCompletedEvent.class);
        CraftTweakerAPI.registerClass(CrTXPUtil.class);
        CraftTweakerAPI.registerClass(ExpandEventManger.class);
        CraftTweakerAPI.registerClass(MaterialPartOreExpansion.class);
    }
}
