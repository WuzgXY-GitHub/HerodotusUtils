package youyihj.herodotusutils.modsupport.jei.recipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.alchemy.BlockAlchemyCrafter;

import javax.annotation.Nullable;

public class AlchemyCraftRecipesCategory implements IRecipeCategory<AlchemyCraftRecipesWrapper> {

    private final IDrawable icon;
    private final IDrawable background;

    public AlchemyCraftRecipesCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockAlchemyCrafter.INSTANCE));
        this.background = guiHelper.createBlankDrawable(200, 200);
    }

    @Override
    public String getUid() {
        return "AlchemyCraftJEI";
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("hdsutils.jei.alchemy_craft_title");
    }

    @Override
    public String getModName() {
        return HerodotusUtils.MOD_ID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlchemyCraftRecipesWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup group = recipeLayout.getFluidStacks();

        // todo layout
        group.init(0, true, 20, 20);
        group.init(1, true, 40, 40);
        group.init(2, true, 60, 60);
        group.init(3, true, 80, 80);
        group.init(4, false, 100, 100);
        group.set(ingredients);
    }
}
