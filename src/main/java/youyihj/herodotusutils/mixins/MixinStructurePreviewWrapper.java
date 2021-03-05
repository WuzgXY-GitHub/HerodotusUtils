package youyihj.herodotusutils.mixins;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import hellfirepvp.modularmachinery.client.util.DynamicMachineRenderContext;
import hellfirepvp.modularmachinery.client.util.RenderingUtils;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.integration.preview.StructurePreviewWrapper;
import hellfirepvp.modularmachinery.common.lib.BlocksMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.gui.recipes.RecipeLayout;
import mezz.jei.gui.recipes.RecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;
import java.util.List;

@Mixin(value = StructurePreviewWrapper.class, remap = false)
public abstract class MixinStructurePreviewWrapper {

    @Shadow
    @Final
    private static Field layouts;

    @Shadow
    private DynamicMachineRenderContext dynamnicContext;

    @Final
    @Shadow
    private IDrawable drawable3DDisabled, drawable3DHover, drawable3DActive;

    @Shadow
    @Final
    private IDrawable drawable2DDisabled, drawable2DHover, drawable2DActive;

    @Shadow
    @Final
    private IDrawable drawablePopOutDisabled, drawablePopOutHover;

    @Shadow
    @Final
    private IDrawable drawableContentsDisabled, drawableContentsHover;

    @Shadow
    @Final
    private IDrawable drawableArrowDown, drawableArrowUp;

    @Shadow
    @Final
    private DynamicMachine machine;

    /**
     * @author youyihj
     * @reason to change items
     */
    @Overwrite
    private void drawButtons(Minecraft minecraft, int mouseX, int mouseY, int guiLeft, int guiTop, int recipeWidth, int recipeHeight) {
        if (dynamnicContext == null) { //Didn't even render machine yet...
            return;
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        minecraft.getTextureManager().bindTexture(StructurePreviewWrapper.TEXTURE_BACKGROUND);

        boolean drawPopoutInfo = false, drawContents = false;

        IDrawable drawable = drawable3DDisabled;
        if (!dynamnicContext.doesRenderIn3D()) {
            if (mouseX >= guiLeft + 132 && mouseX <= guiLeft + 132 + 16 &&
                    mouseY >= guiTop + 106 && mouseY < guiTop + 106 + 16) {
                drawable = drawable3DHover;
            }
        } else {
            drawable = drawable3DActive;
        }
        drawable.draw(minecraft, guiLeft + 132, guiTop + 106);

        drawable = drawablePopOutDisabled;
        if (mouseX >= guiLeft + 116 && mouseX <= guiLeft + 116 + 16 &&
                mouseY >= guiTop + 106 && mouseY < guiTop + 106 + 16) {
            if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                drawable = drawablePopOutHover;
            }
            drawPopoutInfo = true;
        }
        drawable.draw(minecraft, guiLeft + 116, guiTop + 106);

        drawable = drawable2DDisabled;
        if (dynamnicContext.doesRenderIn3D()) {
            if (mouseX >= guiLeft + 132 && mouseX <= guiLeft + 132 + 16 &&
                    mouseY >= guiTop + 122 && mouseY <= guiTop + 122 + 16) {
                drawable = drawable2DHover;
            }
        } else {
            drawable = drawable2DActive;
        }
        drawable.draw(minecraft, guiLeft + 132, guiTop + 122);

        //Show amount
        drawable = drawableContentsDisabled;
        if (mouseX >= guiLeft + 116 && mouseX <= guiLeft + 116 + 16 &&
                mouseY >= guiTop + 122 && mouseY <= guiTop + 122 + 16) {
            drawable = drawableContentsHover;
            drawContents = true;
        }
        drawable.draw(minecraft, guiLeft + 116, guiTop + 122);

        if (dynamnicContext.doesRenderIn3D()) {
            GlStateManager.color(0.3F, 0.3F, 0.3F, 1.0F);
        } else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        if (dynamnicContext.hasSliceUp()) {
            if (!dynamnicContext.doesRenderIn3D() && mouseX >= guiLeft + 150 && mouseX <= guiLeft + 150 + 16 &&
                    mouseY >= guiTop + 102 && mouseY <= guiTop + 102 + 16) {
                GlStateManager.color(0.7F, 0.7F, 1.0F, 1.0F);
            }
            drawableArrowUp.draw(minecraft, guiLeft + 150, guiTop + 102);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (dynamnicContext.hasSliceDown()) {
            if (!dynamnicContext.doesRenderIn3D() && mouseX >= guiLeft + 150 && mouseX <= guiLeft + 150 + 16 &&
                    mouseY >= guiTop + 124 && mouseY <= guiTop + 124 + 16) {
                GlStateManager.color(0.7F, 0.7F, 1.0F, 1.0F);
            }
            drawableArrowDown.draw(minecraft, guiLeft + 150, guiTop + 124);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int width = minecraft.fontRenderer.getStringWidth(String.valueOf(dynamnicContext.getRenderSlice()));
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5, 0, 0); //Don't ask.
        minecraft.fontRenderer.drawString(String.valueOf(dynamnicContext.getRenderSlice()), guiLeft + 158 - (width / 2), guiTop + 118, 0x222222);
        if (drawPopoutInfo) {
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            java.util.List<String> out = minecraft.fontRenderer.listFormattedStringToWidth(
                    I18n.format("gui.blueprint.popout.info"),
                    Math.min(res.getScaledWidth() - mouseX, 200));
            RenderingUtils.renderBlueTooltip(mouseX, mouseY, out, minecraft.fontRenderer);
        }
        if (drawContents) {
            java.util.List<ItemStack> contents = dynamnicContext.getDescriptiveStacks();
            java.util.List<Tuple<ItemStack, String>> contentMap = Lists.newArrayList();
            ItemStack ctrl;
            if (machine.getPattern().hasBlockAt(BlockPos.ORIGIN)) {
                ctrl = new ItemStack(machine.getPattern().getPattern().get(BlockPos.ORIGIN).getSampleState().getBlock());
            } else {
                ctrl = new ItemStack(BlocksMM.blockController);
            }
            contentMap.add(new Tuple<>(ctrl, "1x " + Iterables.getFirst(ctrl.getTooltip(Minecraft.getMinecraft().player,
                    Minecraft.getMinecraft().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL), "")));
            for (ItemStack stack : contents) {
                if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof BlockController) {
                    continue;
                }
                contentMap.add(new Tuple<>(stack, stack.getCount() + "x " + Iterables.getFirst(stack.getTooltip(Minecraft.getMinecraft().player,
                        Minecraft.getMinecraft().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL), "")));
            }

            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int srWidth = scaledresolution.getScaledWidth();
            int srHeight = scaledresolution.getScaledHeight();
            int rMouseX = Mouse.getX() * srWidth / Minecraft.getMinecraft().displayWidth;
            int rMouseY = srHeight - Mouse.getY() * srHeight / Minecraft.getMinecraft().displayHeight - 1;

            RecipesGui current = (RecipesGui) Minecraft.getMinecraft().currentScreen;
            RecipeLayout currentLayout = null;
            try {
                List<RecipeLayout> layoutList = (List<RecipeLayout>) layouts.get(current);
                for (RecipeLayout layout : layoutList) {
                    if (layout.isMouseOver(rMouseX, rMouseY)) {
                        currentLayout = layout;
                        break;
                    }
                }
            } catch (IllegalAccessException ignored) {
            }

            if (currentLayout != null) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(-currentLayout.getPosX(), -currentLayout.getPosY(), 0);
                RenderingUtils.renderBlueStackTooltip(currentLayout.getPosX() + mouseX, currentLayout.getPosY() + mouseY,
                        contentMap,
                        minecraft.fontRenderer,
                        minecraft.getRenderItem());
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
