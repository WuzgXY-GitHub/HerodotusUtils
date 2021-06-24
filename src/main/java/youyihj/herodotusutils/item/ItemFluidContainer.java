package youyihj.herodotusutils.item;

import hellfirepvp.modularmachinery.common.item.ItemDynamicColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.herodotusutils.HerodotusUtils;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
public abstract class ItemFluidContainer extends Item implements ItemDynamicColor {
    protected ItemFluidContainer(String name) {
        this.setRegistryName(name);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + name);
        this.addPropertyOverride(HerodotusUtils.rl("has_fluid"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return FluidUtil.getFluidContained(stack) == null ? 0.0f : 1.0f;
            }
        });
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, 1000);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return !isEmpty(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if (!this.hasContainerItem(itemStack)) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = this.getUnlocalizedName(stack) + ".name";
        IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(stack);
        return I18n.translateToLocalFormatted(unlocalizedName,
                Optional.ofNullable(fluidHandlerItem)
                        .map(fluidHandlerItem0 -> fluidHandlerItem0.getTankProperties()[0].getContents())
                        .map(this::getFluidName)
                        .orElse(I18n.translateToLocal("hdsutils.empty"))
        );
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if (tintIndex != 1) return -1;
        FluidStack fluidStack = FluidUtil.getFluidContained(stack);
        if (fluidStack == null) return -1;
        if (fluidStack.getFluid().getName().equals("water")) {
            return 0x2531AC;
        } else if (fluidStack.getFluid().getName().equals("lava")) {
            return 0xC94309;
        } else return fluidStack.getFluid().getColor();
    }

    public boolean isEmpty(ItemStack stack) {
        return FluidUtil.getFluidContained(stack) == null;
    }

    public boolean isHotFluid(Fluid fluid) {
        return fluid.getTemperature() >= 600;
    }

    public abstract String getFluidName(FluidStack fluidStack);


}
