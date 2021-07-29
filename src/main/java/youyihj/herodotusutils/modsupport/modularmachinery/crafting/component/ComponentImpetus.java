package youyihj.herodotusutils.modsupport.modularmachinery.crafting.component;

import com.google.gson.JsonObject;
import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.requirement.RequirementImpetus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ComponentImpetus extends ComponentType<RequirementImpetus> {

    @Nonnull
    @Override
    public String getRegistryName() {
        return "impetus";
    }

    @Nullable
    @Override
    public String requiresModid() {
        return "thaumicaugmentation";
    }

    @Nonnull
    @Override
    public RequirementImpetus provideComponent(MachineComponent.IOType machineIOType, JsonObject jsonObject) {
        return new RequirementImpetus(machineIOType, new Impetus(jsonObject.get("amount").getAsInt()));
    }
}
