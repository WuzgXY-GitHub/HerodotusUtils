package youyihj.herodotusutils.modsupport.jei.render;

import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import youyihj.herodotusutils.alchemy.AlchemyEssenceStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author youyihj
 */
public class AlchemyEssenceRender implements IIngredientRenderer<AlchemyEssenceStack> {
    @Override
    public void render(Minecraft minecraft, int xPosition, int yPosition, @Nullable AlchemyEssenceStack ingredient) {
        // TODO: change it to texture
        if (ingredient != null) {
            FontRenderer fontRenderer = getFontRenderer(minecraft, ingredient);
            fontRenderer.drawString(ingredient.getDisplayName(), xPosition, yPosition, 0x0000ff);
        }
    }

    @Override
    public List<String> getTooltip(Minecraft minecraft, AlchemyEssenceStack ingredient, ITooltipFlag tooltipFlag) {
        return Collections.singletonList(ingredient.getDisplayName());
    }
}
