package youyihj.herodotusutils.modsupport.crafttweaker;

import WayofTime.bloodmagic.soul.EnumDemonWillType;
import crafttweaker.CraftTweakerAPI;
import fr.frinn.modularmagic.common.crafting.requirement.*;
import hellfirepvp.astralsorcery.common.constellation.ConstellationRegistry;
import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.modularmachinery.common.integration.crafttweaker.RecipePrimer;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import thaumcraft.api.aspects.Aspect;

/**
 * Based on <a>https://github.com/Frinn38/Modular-Magic/blob/master/src/main/java/fr/frinn/modularmagic/common/integration/crafttweaker/MagicPrimer.java</a>
 * <p>
 * to implement the compatibility between modular magic 1.4.0 and craft tweaker.
 *
 * @author Frinn38
 */
@ZenExpansion("mods.modularmachinery.RecipePrimer")
public class MagicPrimer {

    @ZenMethod
    public static RecipePrimer addAspectInput(RecipePrimer primer, String aspectString, int amount) {
        Aspect aspect = Aspect.getAspect(aspectString);
        if (aspect != null)
            primer.appendComponent(new RequirementAspect(IOType.INPUT, amount, aspect));
        else
            CraftTweakerAPI.logError("Invalid aspect name : " + aspectString);

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addAspectOutput(RecipePrimer primer, String aspectString, int amount) {
        Aspect aspect = Aspect.getAspect(aspectString);
        if (aspect != null)
            primer.appendComponent(new RequirementAspect(IOType.OUTPUT, amount, aspect));
        else
            CraftTweakerAPI.logError("Invalid aspect name : " + aspectString);

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addConstellationInput(RecipePrimer primer, String constellationString) {
        IConstellation constellation = ConstellationRegistry.getConstellationByName("astralsorcery.constellation." + constellationString);
        if (constellation != null)
            primer.appendComponent(new RequirementConstellation(IOType.INPUT, constellation));
        else
            CraftTweakerAPI.logError("Invalid constellation : " + constellationString);

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addLifeEssenceInput(RecipePrimer primer, int amount, boolean perTick) {
        if (amount > 0)
            primer.appendComponent(new RequirementLifeEssence(IOType.INPUT, amount, perTick));
        else
            CraftTweakerAPI.logError("Invalid Life Essence amount : " + amount + " (need to be positive and not null)");

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addLifeEssenceOutput(RecipePrimer primer, int amount, boolean perTick) {
        if (amount > 0)
            primer.appendComponent(new RequirementLifeEssence(IOType.OUTPUT, amount, perTick));
        else
            CraftTweakerAPI.logError("Invalid Life Essence amount : " + amount + " (need to be positive and not null)");

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addStarlightInput(RecipePrimer primer, int amount) {
        if (amount > 0)
            primer.appendComponent(new RequirementStarlight(IOType.INPUT, amount));
        else
            CraftTweakerAPI.logError("Invalid Starlight amount : " + amount + " (need to be positive and not null)");

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addStarlightOutput(RecipePrimer primer, int amount) {
        if (amount > 0)
            primer.appendComponent(new RequirementStarlight(IOType.OUTPUT, amount));
        else
            CraftTweakerAPI.logError("Invalid Starlight amount : " + amount + " (need to be positive and not null)");

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addWillInput(RecipePrimer primer, String willTypeString, int amount) {
        EnumDemonWillType willType = EnumDemonWillType.valueOf(willTypeString);
        if (willType != null)
            primer.appendComponent(new RequirementWill(IOType.INPUT, amount, willType /* , Integer.MIN_VALUE, Integer.MAX_VALUE */));
        else
            CraftTweakerAPI.logError("Invalid demon will type : " + willTypeString);

        return primer;
    }

    @ZenMethod
    public static RecipePrimer addWillOutput(RecipePrimer primer, String willTypeString, int amount) {
        EnumDemonWillType willType = EnumDemonWillType.valueOf(willTypeString);
        if (willType != null)
            primer.appendComponent(new RequirementWill(IOType.OUTPUT, amount, willType /*, Integer.MIN_VALUE, Integer.MAX_VALUE */));
        else
            CraftTweakerAPI.logError("Invalid demon will type : " + willTypeString);

        return primer;
    }

//    @ZenMethod
//    public static RecipePrimer addWillInput(RecipePrimer primer, String willTypeString, int amount, int min, int max) {
//        EnumDemonWillType willType = EnumDemonWillType.valueOf(willTypeString);
//        if(willType != null)
//            primer.appendComponent(new RequirementWill(IOType.INPUT, amount, willType, min, max));
//        else
//            CraftTweakerAPI.logError("Invalid demon will type : " + willTypeString);
//
//        return primer;
//    }
//
//    @ZenMethod
//    public static RecipePrimer addWillOutput(RecipePrimer primer, String willTypeString, int amount, int min, int max) {
//        EnumDemonWillType willType = EnumDemonWillType.valueOf(willTypeString);
//        if(willType != null)
//            primer.appendComponent(new RequirementWill(IOType.OUTPUT, amount, willType, min, max));
//        else
//            CraftTweakerAPI.logError("Invalid demon will type : " + willTypeString);
//
//        return primer;
//    }
}
