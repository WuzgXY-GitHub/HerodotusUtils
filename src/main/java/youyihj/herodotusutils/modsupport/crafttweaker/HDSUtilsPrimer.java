package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import hellfirepvp.modularmachinery.common.integration.crafttweaker.RecipePrimer;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.aspects.Aspect;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.requirement.RequirementAspectList;
import youyihj.herodotusutils.modsupport.modularmachinery.tile.TileAspectListProvider;

import java.util.Map;

@ZenExpansion("mods.modularmachinery.RecipePrimer")
public class HDSUtilsPrimer {

    @ZenMethod
    public static RecipePrimer addAspectsInput(RecipePrimer primer, Map<String, Integer> aspects) {
        if (aspects.size() > 6)
            CraftTweakerAPI.logError("Max Size was " + TileAspectListProvider.MAX_ASPECT);
        else
            aspects.forEach((k, v) -> {
                Aspect aspect = Aspect.getAspect(k);
                if (aspect != null)
                    primer.appendComponent(new RequirementAspectList(IOType.INPUT, v, aspect));
                else
                    CraftTweakerAPI.logError("Invalid aspect name : " + k);
            });
        return primer;
    }
}
