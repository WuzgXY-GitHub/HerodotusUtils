package youyihj.herodotusutils.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.herodotusutils.core.ModularMachineryPatches;

@Mixin(value = TileMachineController.class, remap = false)
public abstract class MixinTileMachineController extends TileEntityRestrictedTick {

    @Shadow
    private DynamicMachine foundMachine;

    @Shadow
    private EnumFacing patternRotation;

    @Inject(method = "checkStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"), cancellable = true)
    private void injectCheckStructure(CallbackInfo ci) {
        ci.cancel();
        if (foundMachine != null) {
            world.setBlockState(pos, ((ModularMachineryPatches.IDynamicMachinePatch) foundMachine).getController().getDefaultState().withProperty(BlockController.FACING, patternRotation));
        }
    }

    @Inject(method = "matchesRotation", at = @At(value = "RETURN"), cancellable = true)
    private void injectMatchesRotation(TaggedPositionBlockArray pattern, DynamicMachine machine, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            BlockController controller = ((ModularMachineryPatches.IDynamicMachinePatch) machine).getController();
            if (controller != world.getBlockState(pos).getBlock()) {
                cir.setReturnValue(false);
            }
        }
    }
}
