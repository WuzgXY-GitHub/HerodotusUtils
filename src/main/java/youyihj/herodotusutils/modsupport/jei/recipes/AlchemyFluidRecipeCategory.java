package youyihj.herodotusutils.modsupport.jei.recipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.alchemy.AlchemyEssenceStack;
import youyihj.herodotusutils.alchemy.IAlchemyExternalHatch;
import youyihj.herodotusutils.block.alchemy.BlockAlchemyController;
import youyihj.herodotusutils.modsupport.jei.ModIngredientTypes;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class AlchemyFluidRecipeCategory implements IRecipeCategory<AlchemyFluidRecipeWrapper> {
    private final IDrawable icon;
    private final IDrawable background;

    public AlchemyFluidRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockAlchemyController.ITEM_BLOCK));
        this.background = guiHelper.createBlankDrawable(120, 32);
    }

    @Override
    public String getUid() {
        return "alchemy_fluid";
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("hdsutils.jei.alchemy_fluid.title");
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public String getModName() {
        return HerodotusUtils.MOD_NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlchemyFluidRecipeWrapper recipeWrapper, IIngredients ingredients) {
        AlchemyEssenceStack[] essences = recipeWrapper.getAlchemyFluid().getEssenceStacks().toArray(new AlchemyEssenceStack[0]);
        int size = essences.length;
        IGuiFluidStackGroup fluidGroup = recipeLayout.getFluidStacks();
        IGuiIngredientGroup<AlchemyEssenceStack> essenceGroup = recipeLayout.getIngredientsGroup(ModIngredientTypes.ALCHEMY_ESSENCE);
        for (int i = 0; i < size; i++) {
            essenceGroup.init(i, true, 78 - 16 * (size - i), 8);
            essenceGroup.set(i, essences[i]);
        }
        fluidGroup.init(0, false, 104, 8);
        fluidGroup.set(0, new FluidStack(recipeWrapper.getFluid(), IAlchemyExternalHatch.FLUID_UNIT));
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        Util.renderArrow(minecraft, 78, 8, 0);
    }
}
