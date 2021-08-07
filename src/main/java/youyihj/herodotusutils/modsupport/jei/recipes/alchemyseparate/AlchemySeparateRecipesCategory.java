package youyihj.herodotusutils.modsupport.jei.recipes.alchemyseparate;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.tuple.Pair;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.alchemy.BlockAlchemyCrafter;
import youyihj.herodotusutils.util.Util;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemySeparateRecipesCategory implements IRecipeCategory<AlchemySeparateRecipesWrapper> {

    private final IDrawable icon;
    private final IDrawable background;

    private final Pair<Integer, Integer> inputLayout = Pair.of(15, 15);
    private final List<Pair<Integer, Integer>> outputLayouts = Lists.newArrayList(
            Pair.of(59, 16),
            Pair.of(85, 16),
            Pair.of(59, 42),
            Pair.of(85, 42)
    );
    private final Pair<Integer, Pair<Integer, Integer>> arrowLayout = Pair.of(0, Pair.of(34, 16));

    public AlchemySeparateRecipesCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BlockAlchemyCrafter.INSTANCE));
        this.background = guiHelper.createBlankDrawable(160, 60);
    }

    @Override
    public String getUid() {
        return "AlchemySeparateJEI";
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("hdsutils.jei.separate_craft.title");
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
    public void setRecipe(IRecipeLayout recipeLayout, AlchemySeparateRecipesWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup group = recipeLayout.getFluidStacks();
        for (int i = 0; i < outputLayouts.size(); i++) {
            Pair<Integer, Integer> output = outputLayouts.get(i);
            group.init(i, false, output.getLeft(), output.getValue());
        }
        group.init(outputLayouts.size(), true, inputLayout.getLeft(), inputLayout.getRight());
        group.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        Util.renderItem(minecraft, inputLayout.getLeft(), inputLayout.getRight(), true);
        Util.renderArrow(minecraft, arrowLayout.getRight().getLeft(), arrowLayout.getRight().getRight(), arrowLayout.getLeft());
        outputLayouts.forEach(p -> Util.renderItem(minecraft, p.getLeft(), p.getValue(), true));
        GlStateManager.disableAlpha();
    }
}
