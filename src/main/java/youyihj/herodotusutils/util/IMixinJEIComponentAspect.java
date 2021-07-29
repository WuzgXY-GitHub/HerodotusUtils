package youyihj.herodotusutils.util;

import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement.JEIComponent;
import thaumcraft.api.aspects.AspectList;
import youyihj.herodotusutils.block.modularmachine.crafting.requirement.RequirementAspectList;

public interface IMixinJEIComponentAspect {
    void setRequirementAspectList(RequirementAspectList requirementAspectList);

    JEIComponent<AspectList> getJEIComponent();
}
