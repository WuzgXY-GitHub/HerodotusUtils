package youyihj.herodotusutils.mixins;

import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import youyihj.herodotusutils.util.FrozenKeyMap;

import java.util.Map;

@Mixin(value = BlockArray.class, remap = false)
public abstract class MixinBlockArray {

    @Shadow
    protected Map<BlockPos, BlockArray.BlockInformation> pattern;

    @Inject(method = "getPatternSlice", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void getPatternSlice(int slice, CallbackInfoReturnable<Map<BlockPos, BlockArray.BlockInformation>> cir) {
        if (slice == 0 && pattern.containsKey(BlockPos.ORIGIN)) {
            cir.setReturnValue(FrozenKeyMap.of(cir.getReturnValue()).putFrozenEntry(BlockPos.ORIGIN, pattern.get(BlockPos.ORIGIN)));
        }
    }
}
