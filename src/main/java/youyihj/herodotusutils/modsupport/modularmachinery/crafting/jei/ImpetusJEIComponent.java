package youyihj.herodotusutils.modsupport.modularmachinery.crafting.jei;

import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement;
import hellfirepvp.modularmachinery.common.integration.recipe.RecipeLayoutPart;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.requirement.RequirementImpetus;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * @author youyihj
 */
public class ImpetusJEIComponent extends ComponentRequirement.JEIComponent<Impetus> {
    private final RequirementImpetus requirementImpetus;

    public ImpetusJEIComponent(RequirementImpetus requirementImpetus) {
        this.requirementImpetus = requirementImpetus;
    }

    @Override
    public Class<Impetus> getJEIRequirementClass() {
        return Impetus.class;
    }

    @Override
    public List<Impetus> getJEIIORequirements() {
        return Collections.singletonList(requirementImpetus.getImpetus());
    }

    @Override
    public RecipeLayoutPart<Impetus> getLayoutPart(Point offset) {
        return new LayoutImpetus(offset);
    }

    @Override
    public void onJEIHoverTooltip(int slotIndex, boolean input, Impetus ingredient, List<String> tooltip) {

    }
}
