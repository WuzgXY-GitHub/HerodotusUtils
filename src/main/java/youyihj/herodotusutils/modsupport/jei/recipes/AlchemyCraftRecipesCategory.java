package youyihj.herodotusutils.modsupport.jei.recipes;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.tuple.Pair;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.alchemy.BlockAlchemyCrafter;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemyCraftRecipesCategory implements IRecipeCategory<AlchemyCraftRecipesWrapper> {

    private final IDrawable icon;
    private final IDrawable background;

    private final List<Pair<Integer, Integer>> inputLayouts = Lists.newArrayList(
            Pair.of(72, 0),
            Pair.of(72, 94),
            Pair.of(22, 45),
            Pair.of(122, 45));
    private final Pair<Integer, Integer> outputLayout = Pair.of(72, 45);

    public AlchemyCraftRecipesCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockAlchemyCrafter.INSTANCE));
        this.background = guiHelper.createBlankDrawable(160, 110);
    }

    @Override
    public String getUid() {
        return "AlchemyCraftJEI";
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("hdsutils.jei.alchemy_craft.title");
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
        for (int i = 0; i < inputLayouts.size(); i++) {
            Pair<Integer, Integer> input = inputLayouts.get(i);
            group.init(i, true, input.getLeft(), input.getValue());
        }
        group.init(inputLayouts.size(), false, outputLayout.getLeft(), outputLayout.getRight());
        group.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        Util.renderItem(minecraft, outputLayout.getLeft(), outputLayout.getRight(), false);
        inputLayouts.forEach(p -> Util.renderItem(minecraft, p.getLeft(), p.getValue(), true));
    }
}
