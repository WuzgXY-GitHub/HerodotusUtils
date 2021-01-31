package youyihj.herodotusutils.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Optional;

/**
 * @author youyihj
 */
public class RefinedBottle extends ItemFluidContainer {

    public static final String NAME = "refined_bottle";
    public static final RefinedBottle INSTANCE = new RefinedBottle();

    private RefinedBottle() {
        super(NAME);
        this.setMaxStackSize(16);
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
    public String getFluidName(FluidStack fluidStack) {
        return fluidStack.getLocalizedName() + " * " + fluidStack.amount + " mB";
    }
}
