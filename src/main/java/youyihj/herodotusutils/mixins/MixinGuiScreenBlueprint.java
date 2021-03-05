package youyihj.herodotusutils.mixins;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import hellfirepvp.modularmachinery.client.gui.GuiScreenBlueprint;
import hellfirepvp.modularmachinery.client.util.DynamicMachineRenderContext;
import hellfirepvp.modularmachinery.client.util.RenderingUtils;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.lib.BlocksMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = GuiScreenBlueprint.class, remap = false)
public abstract class MixinGuiScreenBlueprint extends GuiScreen {
    @Shadow
    @Final
    private DynamicMachine machine;

    @Shadow
    protected int guiLeft;
    @Shadow
    protected int guiTop;

    @Shadow
    @Final
    private DynamicMachineRenderContext renderContext;

    /**
     * @reason to totally change item
     * @author youyihj
     */
    @Overwrite
    private void drawButtons(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiScreenBlueprint.TEXTURE_BACKGROUND);

        boolean drawPopoutInfo = false, drawContents = false;

        //3D view
        int add = 0;
        if (!renderContext.doesRenderIn3D()) {
            if (mouseX >= this.guiLeft + 132 && mouseX <= this.guiLeft + 132 + 16 &&
                    mouseY >= this.guiTop + 106 && mouseY < this.guiTop + 106 + 16) {
                add = 16;
            }
        } else {
            add = 32;
        }
        this.drawTexturedModalRect(guiLeft + 132, guiTop + 106, 176 + add, 16, 16, 16);

        //Pop out
        add = 0;
        if (mouseX >= this.guiLeft + 116 && mouseX <= this.guiLeft + 116 + 16 &&
                mouseY >= this.guiTop + 106 && mouseY < this.guiTop + 106 + 16) {
            if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                add = 16;
            }
            drawPopoutInfo = true;
        }
        this.drawTexturedModalRect(guiLeft + 116, guiTop + 106, 176 + add, 48, 16, 16);

        //2D view
        add = 0;
        if (renderContext.doesRenderIn3D()) {
            if (mouseX >= this.guiLeft + 132 && mouseX <= this.guiLeft + 132 + 16 &&
                    mouseY >= this.guiTop + 122 && mouseY <= this.guiTop + 122 + 16) {
                add = 16;
            }
        } else {
            add = 32;
        }
        this.drawTexturedModalRect(guiLeft + 132, guiTop + 122, 176 + add, 32, 16, 16);

        //Show amount
        add = 0;
        if (mouseX >= this.guiLeft + 116 && mouseX <= this.guiLeft + 116 + 16 &&
                mouseY >= this.guiTop + 122 && mouseY <= this.guiTop + 122 + 16) {
            add = 16;
            drawContents = true;
        }
        this.drawTexturedModalRect(guiLeft + 116, guiTop + 122, 176 + add, 64, 16, 16);

        if (renderContext.doesRenderIn3D()) {
            GlStateManager.color(0.3F, 0.3F, 0.3F, 1.0F);
        } else {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        if (renderContext.hasSliceUp()) {
            if (!renderContext.doesRenderIn3D() && mouseX >= this.guiLeft + 150 && mouseX <= this.guiLeft + 150 + 16 &&
                    mouseY >= this.guiTop + 102 && mouseY <= this.guiTop + 102 + 16) {
                GlStateManager.color(0.7F, 0.7F, 1.0F, 1.0F);
            }
            this.drawTexturedModalRect(guiLeft + 150, guiTop + 102, 192, 0, 16, 16);
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
        if (renderContext.hasSliceDown()) {
            if (!renderContext.doesRenderIn3D() && mouseX >= this.guiLeft + 150 && mouseX <= this.guiLeft + 150 + 16 &&
                    mouseY >= this.guiTop + 124 && mouseY <= this.guiTop + 124 + 16) {
                GlStateManager.color(0.7F, 0.7F, 1.0F, 1.0F);
            }
            this.drawTexturedModalRect(guiLeft + 150, guiTop + 124, 176, 0, 16, 16);
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int width = fontRenderer.getStringWidth(String.valueOf(renderContext.getRenderSlice()));
        fontRenderer.drawString(String.valueOf(renderContext.getRenderSlice()), guiLeft + 159 - (width / 2), guiTop + 118, 0x222222);
        if (drawPopoutInfo) {
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            List<String> out = fontRenderer.listFormattedStringToWidth(
                    I18n.format("gui.blueprint.popout.info"),
                    Math.min(res.getScaledWidth() - mouseX, 200));
            RenderingUtils.renderBlueTooltip(mouseX, mouseY, out, fontRenderer);
        }
        if (drawContents) {
            List<ItemStack> contents = this.renderContext.getDescriptiveStacks();
            List<Tuple<ItemStack, String>> contentMap = Lists.newArrayList();
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
            RenderingUtils.renderBlueStackTooltip(mouseX, mouseY,
                    contentMap,
                    fontRenderer, Minecraft.getMinecraft().getRenderItem());
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
