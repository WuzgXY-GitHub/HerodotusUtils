package youyihj.herodotusutils.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.Optional;

/**
 * @author youyihj
 */
public class ItemCopperBucket extends ItemFluidContainer {
    private ItemCopperBucket() {
        super(NAME);
        this.setMaxStackSize(1);
    }

    public static final String NAME = "copper_bucket";
    public static final ItemCopperBucket INSTANCE = new ItemCopperBucket();

    @Override
    public String getFluidName(FluidStack fluidStack) {
        return fluidStack.getLocalizedName();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!worldIn.isRemote) {
            if (worldIn.rand.nextInt(15) == 0) {
                player.renderBrokenItemStack(stack);
                stack.shrink(1);
                return ActionResult.newResult(EnumActionResult.FAIL, stack);
            }
        }
        RayTraceResult rayTraceResult = this.rayTrace(worldIn, player, this.isEmpty(stack));
        if (rayTraceResult == null) {
            return ActionResult.newResult(EnumActionResult.FAIL, stack);
        }
        BlockPos pos = rayTraceResult.getBlockPos();
        EnumFacing facing = rayTraceResult.sideHit;
        if (pos == null || facing == null) {
            return ActionResult.newResult(EnumActionResult.FAIL, stack);
        }
        FluidActionResult fluidActionResult;
        if (this.isEmpty(stack)) {
            fluidActionResult = Optional.ofNullable(FluidUtil.getFluidHandler(worldIn, pos, facing))
                    .map(fluidHandler -> fluidHandler.drain(1000, false))
                    .filter(fluidStack -> fluidStack.amount >= 1000)
                    .map(fluidStack -> FluidUtil.tryPickUpFluid(stack, player, worldIn, pos, facing))
                    .orElse(FluidActionResult.FAILURE);
        } else {
            fluidActionResult = FluidUtil.tryPlaceFluid(player, worldIn, pos, stack, FluidUtil.getFluidContained(stack));
        }
        if (fluidActionResult.success) {
            if (!worldIn.isRemote) {
                player.setHeldItem(hand, fluidActionResult.result);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, fluidActionResult.result);
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }
}
