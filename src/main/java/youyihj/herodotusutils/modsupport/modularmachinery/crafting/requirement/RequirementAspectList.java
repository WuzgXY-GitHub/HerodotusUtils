package youyihj.herodotusutils.modsupport.modularmachinery.crafting.requirement;

import com.google.common.collect.Lists;
import fr.frinn.modularmagic.common.integration.jei.component.JEIComponentAspect;
import hellfirepvp.modularmachinery.common.crafting.ComponentType.Registry;
import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement;
import hellfirepvp.modularmachinery.common.crafting.helper.CraftCheck;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import hellfirepvp.modularmachinery.common.util.ResultChance;
import net.minecraft.util.math.MathHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import youyihj.herodotusutils.modsupport.modularmachinery.tile.TileAspectListProvider;
import youyihj.herodotusutils.util.IMixinJEIComponentAspect;

import javax.annotation.Nonnull;
import java.util.List;

public class RequirementAspectList extends ComponentRequirement<AspectList> {

    public int amount;
    public Aspect aspect;

    public RequirementAspectList(IOType actionType, int amount, Aspect aspect) {
        super(Registry.getComponent("aspectList"), actionType);
        this.amount = amount;
        this.aspect = aspect;
    }

    public boolean startCrafting(MachineComponent component, RecipeCraftingContext context, ResultChance chance) {
        if (!this.canStartCrafting(component, context, Lists.newArrayList()).isSuccess()) {
            return false;
        } else if (this.getActionType() == IOType.INPUT) {
            TileAspectListProvider provider = (TileAspectListProvider) component.getContainerProvider();
            return provider.takeFromContainer(this.aspect, this.amount);
        } else {
            return true;
        }
    }

    public boolean finishCrafting(MachineComponent component, RecipeCraftingContext context, ResultChance chance) {
        if (this.getActionType() == IOType.OUTPUT) {
            TileAspectListProvider provider = (TileAspectListProvider) component.getContainerProvider();
            provider.addToContainer(this.aspect, this.amount);
        }

        return true;
    }

    @Override
    public ComponentRequirement<AspectList> deepCopy() {
        return new RequirementAspectList(this.getActionType(), this.amount, this.aspect);
    }

    @Override
    public ComponentRequirement<AspectList> deepCopyModified(List<RecipeModifier> modifiers) {
        float newAmount = RecipeModifier.applyModifiers(modifiers, this, this.amount, false);
        return new RequirementAspectList(this.getActionType(), MathHelper.ceil(newAmount), this.aspect);
    }

    @Nonnull
    public CraftCheck canStartCrafting(MachineComponent component, RecipeCraftingContext context, List restrictions) {
        TileAspectListProvider provider = (TileAspectListProvider) component.getContainerProvider();
        if (provider.doesContainerContainAmount(this.aspect, this.amount)) {
            return CraftCheck.success();
        }
        return CraftCheck.failure("error.modularmagic.requirement.aspect.less");
    }


    public void startRequirementCheck(ResultChance contextChance, RecipeCraftingContext context) {
    }

    public void endRequirementCheck() {
    }

    @Override
    public JEIComponent<AspectList> provideJEIComponent() {
        JEIComponentAspect jeiComponentAspect = new JEIComponentAspect(null);
        ((IMixinJEIComponentAspect) jeiComponentAspect).setRequirementAspectList(this);
        return ((IMixinJEIComponentAspect) jeiComponentAspect).getJEIComponent();
    }
}

