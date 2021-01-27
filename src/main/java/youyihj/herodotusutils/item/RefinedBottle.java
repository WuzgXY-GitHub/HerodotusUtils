package youyihj.herodotusutils.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import youyihj.herodotusutils.HerodotusUtils;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
public class RefinedBottle extends Item {
    private RefinedBottle() {
        this.setRegistryName(NAME);
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + "." + NAME);
        this.setMaxStackSize(16);
        this.addPropertyOverride(HerodotusUtils.rl("refined_bottle_predicate"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return FluidUtil.getFluidContained(stack) == null ? 0.0f : 1.0f;
            }
        });
    }

    public static final String NAME = "refined_bottle";
    public static final RefinedBottle INSTANCE = new RefinedBottle();

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerItemStack(stack, 1000);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack item = player.getHeldItem(hand);
            Optional.ofNullable(worldIn.getTileEntity(pos))
                    .map(tileEntity -> tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))
                    .ifPresent(fluidHandler -> {
                        FluidActionResult fluidActionResult = FluidUtil.tryFillContainer(item, fluidHandler, 1000, player, true);
                        if (item.getCount() == 1) {
                            player.setHeldItem(hand, fluidActionResult.result);
                        } else {
                            item.shrink(1);
                            ItemHandlerHelper.giveItemToPlayer(player, fluidActionResult.result);
                        }
                    });
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        World world = playerIn.getEntityWorld();
        ItemStack stackCopy = ItemHandlerHelper.copyStackWithSize(stack, 1);
        FluidStack fluidContained = FluidUtil.getFluidContained(stackCopy);
        if (fluidContained == null)
            return false;
        if (fluidContained.getFluid().getName().equals("mercury") && fluidContained.amount >= 500) {
            if (!world.isRemote) {
                FluidUtil.getFluidHandler(stackCopy).drain(500, true);
                target.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 400, 1));
                if (stack.getCount() == 1) {
                    playerIn.setHeldItem(hand, stackCopy);
                } else {
                    stack.shrink(1);
                    ItemHandlerHelper.giveItemToPlayer(playerIn, stackCopy);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = this.getUnlocalizedName(stack) + ".name";
        IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(stack);
        return I18n.translateToLocalFormatted(unlocalizedName,
                Optional.ofNullable(fluidHandlerItem)
                .map(fluidHandlerItem0 -> fluidHandlerItem0.getTankProperties()[0].getContents())
                .map(fluidStack -> fluidStack.getLocalizedName() + " * " + fluidStack.amount + " mB")
                .orElse(I18n.translateToLocal("hdsutils.empty"))
        );
    }
}
