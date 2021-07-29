package youyihj.herodotusutils.block.modularmachine.crafting.component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import thaumcraft.api.aspects.Aspect;
import youyihj.herodotusutils.block.modularmachine.crafting.requirement.RequirementAspectList;

public class ComponentAspectList extends ComponentType<RequirementAspectList> {

    @Nonnull
    public String getRegistryName() {
        return "aspectList";
    }

    @Nullable
    public String requiresModid() {
        return "thaumcraft";
    }

    @Nonnull
    public RequirementAspectList provideComponent(IOType ioType, JsonObject requirement) {
        if (requirement.has("amount") && requirement.get("amount").isJsonPrimitive() && requirement.get("amount").getAsJsonPrimitive().isNumber()) {
            if (requirement.has("aspect") && requirement.get("aspect").isJsonPrimitive() && requirement.get("aspect").getAsJsonPrimitive().isString()) {
                int aspectRequired = requirement.getAsJsonPrimitive("amount").getAsInt();
                if (aspectRequired < 0) {
                    throw new JsonParseException("'amount' can not be less than 0");
                } else {
                    Aspect aspect = Aspect.getAspect(requirement.get("aspect").getAsString());
                    if (aspect != null) {
                        return new RequirementAspectList(ioType, aspectRequired, aspect);
                    } else {
                        throw new JsonParseException("Invalid aspect name : " + requirement.getAsJsonPrimitive("aspect").getAsString());
                    }
                }
            } else {
                throw new JsonParseException("The ComponentType '" + this.getRegistryName() + "' expects a 'aspect'-entry that defines the required/produced aspect");
            }
        } else {
            throw new JsonParseException("The ComponentType '" + this.getRegistryName() + "' expects a 'amount'-entry that defines the required/produced aspect amount");
        }
    }
}
