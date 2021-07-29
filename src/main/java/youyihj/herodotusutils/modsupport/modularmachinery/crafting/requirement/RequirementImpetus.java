package youyihj.herodotusutils.modsupport.modularmachinery.crafting.requirement;

import com.google.common.collect.Lists;
import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import hellfirepvp.modularmachinery.common.crafting.helper.ComponentOutputRestrictor;
import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement;
import hellfirepvp.modularmachinery.common.crafting.helper.CraftCheck;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import hellfirepvp.modularmachinery.common.util.ResultChance;
import net.minecraft.util.math.MathHelper;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.jei.ImpetusJEIComponent;
import youyihj.herodotusutils.modsupport.modularmachinery.tile.TileImpetusComponent;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author youyihj
 */
public class RequirementImpetus extends ComponentRequirement<Impetus> {
    private final Impetus impetus;

    public RequirementImpetus(MachineComponent.IOType actionType, Impetus impetus) {
        super(ComponentType.Registry.getComponent("impetus"), actionType);
        this.impetus = impetus;
    }

    @Override
    public boolean startCrafting(MachineComponent component, RecipeCraftingContext context, ResultChance chance) {
        if (!this.canStartCrafting(component, context, Lists.newArrayList()).isSuccess()) {
            return false;
        } else if (this.getActionType() == MachineComponent.IOType.INPUT) {
            TileImpetusComponent tileComponent = (TileImpetusComponent) component.getContainerProvider();
            tileComponent.consumeImpetus(this.impetus.getAmount());
        }
        return true;
    }

    @Override
    public boolean finishCrafting(MachineComponent component, RecipeCraftingContext context, ResultChance chance) {
        // TODO: output
        return true;
    }

    @Nonnull
    @Override
    public CraftCheck canStartCrafting(MachineComponent component, RecipeCraftingContext context, List<ComponentOutputRestrictor> restrictions) {
        TileImpetusComponent tileComponent = (TileImpetusComponent) component.getContainerProvider();
        if (tileComponent.hasEnoughImpetus(this.impetus.getAmount())) {
            return CraftCheck.success();
        } else {
            return CraftCheck.failure("hdsutils.error.less_impetus");
        }
    }

    @Override
    public ComponentRequirement<Impetus> deepCopy() {
        return new RequirementImpetus(getActionType(), new Impetus(this.impetus.getAmount()));
    }

    @Override
    public ComponentRequirement<Impetus> deepCopyModified(List<RecipeModifier> modifiers) {
        float newAmount = RecipeModifier.applyModifiers(modifiers, this, this.impetus.getAmount(), false);
        return new RequirementImpetus(getActionType(), new Impetus(MathHelper.ceil(newAmount)));
    }

    @Override
    public void startRequirementCheck(ResultChance contextChance, RecipeCraftingContext context) {

    }

    @Override
    public void endRequirementCheck() {

    }

    public Impetus getImpetus() {
        return impetus;
    }

    @Override
    public JEIComponent<Impetus> provideJEIComponent() {
        return new ImpetusJEIComponent(this);
    }
}
