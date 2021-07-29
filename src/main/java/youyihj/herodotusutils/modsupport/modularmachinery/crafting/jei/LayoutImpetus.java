package youyihj.herodotusutils.modsupport.modularmachinery.crafting.jei;

import hellfirepvp.modularmachinery.common.integration.recipe.RecipeLayoutPart;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import youyihj.herodotusutils.modsupport.jei.render.ImpetusRender;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.ingredient.Impetus;

import java.awt.*;

/**
 * @author youyihj
 */
public class LayoutImpetus extends RecipeLayoutPart<Impetus> {
    private final MachineComponent.IOType ioType;

    public LayoutImpetus(Point offset, MachineComponent.IOType ioType) {
        super(offset);
        this.ioType = ioType;
    }

    @Override
    public int getComponentWidth() {
        return 16;
    }

    @Override
    public int getComponentHeight() {
        return 16;
    }

    @Override
    public Class<Impetus> getLayoutTypeClass() {
        return Impetus.class;
    }

    @Override
    public IIngredientRenderer<Impetus> provideIngredientRenderer() {
        return new ImpetusRender();
    }

    @Override
    public int getRendererPaddingX() {
        return 0;
    }

    @Override
    public int getRendererPaddingY() {
        return 0;
    }

    @Override
    public int getMaxHorizontalCount() {
        return 1;
    }

    @Override
    public int getComponentHorizontalGap() {
        return 0;
    }

    @Override
    public int getComponentVerticalGap() {
        return 0;
    }

    @Override
    public int getComponentHorizontalSortingOrder() {
        return 0;
    }

    @Override
    public boolean canBeScaled() {
        return false;
    }

    @Override
    public void drawBackground(Minecraft mc) {

    }
}
