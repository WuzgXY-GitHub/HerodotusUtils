package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Loader;
import youyihj.herodotusutils.recipe.AlchemyRecipes;
import youyihj.herodotusutils.recipe.ClipManager;
import youyihj.herodotusutils.util.ITaint;

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
        CraftTweakerAPI.registerClass(HDSUtils.class);
        CraftTweakerAPI.registerClass(HDSUtilsPrimer.class);
        CraftTweakerAPI.registerClass(ExpandPlayer.class);
        CraftTweakerAPI.registerClass(ITaint.class);
        CraftTweakerAPI.registerClass(ArcaneRecipe.class);
        if (Loader.isModLoaded("modtweaker")) {
            CraftTweakerAPI.registerClass(ExpandArcaneWorkbench.class);
        }
    }
}
