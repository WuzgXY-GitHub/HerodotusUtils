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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

/**
 * @author youyihj
 */
public class ItemCopperBucket extends ItemFluidContainer {
    private ItemCopperBucket() {
        super(NAME);
        this.setMaxStackSize(1);
        MinecraftForge.EVENT_BUS.register(this.getClass());
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
        FluidActionResult fluidActionResult = this.isEmpty(stack)
                ? FluidUtil.tryPickUpFluid(stack, player, worldIn, pos, facing)
                : FluidUtil.tryPlaceFluid(player, worldIn, pos.offset(facing), stack, FluidUtil.getFluidContained(stack));
        if (fluidActionResult.success) {
            if (!worldIn.isRemote) {
                player.setHeldItem(hand, fluidActionResult.result);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS, fluidActionResult.result);
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }
}
