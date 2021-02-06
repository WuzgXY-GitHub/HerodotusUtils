package youyihj.herodotusutils.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import youyihj.herodotusutils.HerodotusUtils;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author youyihj
 */
public class StarlightStorageTiny extends Item {
    private StarlightStorageTiny() {
        this.setRegistryName("tiny_starlight_storage");
        this.setUnlocalizedName(HerodotusUtils.MOD_ID + ".tiny_starlight_storage");
        this.setCreativeTab(CreativeTabs.MISC);
        this.setMaxStackSize(1);
        this.hasSubtypes = true;
    }

    private static final String TAG_STARLIGHT = "star_light";
    public static final StarlightStorageTiny INSTANCE = new StarlightStorageTiny();

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        ItemStack item = entityItem.getItem();
        if (item.getMetadata() != 1) {
            if (!item.hasTagCompound()) {
                item.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound nbt = item.getTagCompound();
            int starlight = nbt.getInteger(TAG_STARLIGHT);
            if (!entityItem.world.isDaytime()) {
                nbt.setInteger(TAG_STARLIGHT, ++starlight);
            }
            if (starlight > 3000) {
                entityItem.setItem(new ItemStack(this, 1, 1));
            }
        }
        return false;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getMetadata() != 1) {
            return super.getUnlocalizedName(stack);
        } else {
            return super.getUnlocalizedName(stack) + "_full";
        }
    }
}
