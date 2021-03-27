package youyihj.herodotusutils.modsupport.modularmachinery.block;

import hellfirepvp.modularmachinery.common.block.BlockController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockMMController extends BlockController {

    private final int color;

    public static final List<BlockMMController> CONTROLLERS = new ArrayList<>();
    public static final List<Item> CONTROLLER_ITEMS = new ArrayList<>();

    public BlockMMController(String machineRegistryName, String machineUnlocalizedName, int color) {
        this.setRegistryName(machineRegistryName + "_controller");
        this.setUnlocalizedName(machineUnlocalizedName);
        this.color = color;
        CONTROLLERS.add(this);
        CONTROLLER_ITEMS.add(new ItemBlock(this) {
            @Override
            public String getItemStackDisplayName(ItemStack stack) {
                return BlockMMController.this.getLocalizedName();
            }
        }.setRegistryName(machineRegistryName + "_controller"));
    }

    @Override
    public int getColorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return color;
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getLocalizedName() {
        return I18n.translateToLocalFormatted("hdsutils.controller", I18n.translateToLocal(this.getUnlocalizedName().substring("tile.".length())));
    }
}
