package youyihj.herodotusutils.mixins;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import youyihj.herodotusutils.core.ModularMachineryPatches;

import java.util.List;

@Mixin(value = DynamicMachine.MachineDeserializer.class, remap = false)
public abstract class MixinMachineDeserializer {

    @Shadow
    protected abstract List<BlockPos> buildPermutations(List<Integer> avX, List<Integer> avY, List<Integer> avZ);

    @Inject(method = "addDescriptorWithPattern", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectAddDescriptorWithPattern(TaggedPositionBlockArray pattern, BlockArray.BlockInformation information, JsonObject part, CallbackInfo ci, List<Integer> avX, List<Integer> avY, List<Integer> avZ) throws JsonParseException {
        buildPermutations(avX, avY, avZ).stream()
                .filter(pos -> pos.equals(BlockPos.ORIGIN))
                .findFirst()
                .ifPresent(pos -> {
                    IBlockState state = information.getSampleState();
                    if (state.getBlock() instanceof BlockController) {
                        ModularMachineryPatches.setControllerBlock(pattern, ((BlockController) state.getBlock()));
                    }
                });
    }
}
