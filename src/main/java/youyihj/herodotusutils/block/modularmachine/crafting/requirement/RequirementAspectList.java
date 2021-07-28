package youyihj.herodotusutils.block.modularmachine.crafting.requirement;

import com.google.common.collect.Lists;
import hellfirepvp.modularmachinery.common.crafting.ComponentType.Registry;
import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement;
import hellfirepvp.modularmachinery.common.crafting.helper.CraftCheck;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.machine.MachineComponent.IOType;
import hellfirepvp.modularmachinery.common.util.ResultChance;
import java.util.List;
import javax.annotation.Nonnull;
import thaumcraft.api.aspects.Aspect;
import youyihj.herodotusutils.block.modularmachine.tile.TileAspectListProvider;

public class RequirementAspectList extends ComponentRequirement {

    public int amount;
    public Aspect aspect;

    public RequirementAspectList(IOType actionType, int amount, Aspect aspect) {
        super(Registry.getComponent("aspect"), actionType);
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

    @Nonnull
    public CraftCheck canStartCrafting(MachineComponent component, RecipeCraftingContext context, List restrictions) {
        TileAspectListProvider provider = (TileAspectListProvider) component.getContainerProvider();
        switch (this.getActionType()) {
            case INPUT:
                if (provider.doesContainerContainAmount(this.aspect, this.amount)) {
                    return CraftCheck.success();
                }

                return CraftCheck.failure("error.modularmagic.requirement.aspect.less");
            case OUTPUT:
                if (provider.doesContainerAccept(this.aspect)) {
                    return CraftCheck.success();
                }

                return CraftCheck.failure("error.modularmagic.requirement.aspect.out");
            default:
                return CraftCheck.skipComponent();
        }
    }

    public ComponentRequirement deepCopy() {
        return this;
    }

    public ComponentRequirement deepCopyModified(List list) {
        return this;
    }

    public void startRequirementCheck(ResultChance contextChance, RecipeCraftingContext context) {
    }

    public void endRequirementCheck() {
    }

    public JEIComponent provideJEIComponent() {
        return null;
    }
}

