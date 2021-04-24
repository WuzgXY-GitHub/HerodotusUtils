package youyihj.herodotusutils.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.IFluidBlock;
import youyihj.herodotusutils.HerodotusUtils;

public class ItemOilAIOT extends ItemHoe {
    private static final ToolMaterial DUMMY_TOOL_MATERIAL = EnumHelper.addToolMaterial("dummmy", 2, 1000, 6.0f, 2.0f, 12);

    private ItemOilAIOT() {
        super(DUMMY_TOOL_MATERIAL);
        this.setHarvestLevel("pickaxe", 2);
        this.setHarvestLevel("axe", 2);
        this.setHarvestLevel("shovel", 2);
        this.setRegistryName("oil_aiot");
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + ".oil_aiot");
        this.setFull3D();
    }

    public static final ItemOilAIOT INSTANCE = new ItemOilAIOT();

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        World world = entityItem.world;
        if (world.isRemote)
            return false;
        BlockPos pos = entityItem.getPosition();
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof IFluidBlock) {
            IFluidBlock fluidBlock = (IFluidBlock) block;
            if (fluidBlock.getFluid().getName().equals("light_oil") && fluidBlock.getFilledPercentage(world, pos) == 1.0f) {
                world.setBlockToAir(pos);
                entityItem.getItem().setItemDamage(Math.max(0, entityItem.getItem().getItemDamage() - 200));
            }
        }
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return toolMaterial.getEfficiency();
        }
        return 1.0f;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (!worldIn.isRemote && (double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(1, entityLiving);
        }

        return true;
    }
}
