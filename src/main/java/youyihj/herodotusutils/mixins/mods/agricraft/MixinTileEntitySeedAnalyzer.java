package youyihj.herodotusutils.mixins.mods.agricraft;

import com.infinityraider.agricraft.tiles.analyzer.TileEntitySeedAnalyzer;
import com.infinityraider.infinitylib.block.tile.TileEntityRotatableBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
@Mixin(TileEntitySeedAnalyzer.class)
@Pseudo
public abstract class MixinTileEntitySeedAnalyzer extends TileEntityRotatableBase {
    @Shadow(remap = false)
    @Nonnull
    private ItemStack specimen;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = {"setInventorySlotContents", "func_70299_a"},
            at = @At(
                    value = "FIELD",
                    target = "Lcom/infinityraider/agricraft/tiles/analyzer/TileEntitySeedAnalyzer;specimen:Lnet/minecraft/item/ItemStack;",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1,
                    shift = At.Shift.AFTER
            ),
            remap = false
    )
    private void mergeSpecimenItemTag(int slot, ItemStack stack, CallbackInfo ci) {
        NBTTagCompound originTag = stack.getTagCompound();
        NBTTagCompound newTag = specimen.getTagCompound();
        if (originTag != null && newTag != null) {
            originTag.merge(newTag);
            specimen.setTagCompound(originTag);
        }
    }
}
