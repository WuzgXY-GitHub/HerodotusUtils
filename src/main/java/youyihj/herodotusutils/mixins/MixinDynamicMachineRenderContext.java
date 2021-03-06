package youyihj.herodotusutils.mixins;

import hellfirepvp.modularmachinery.client.util.BlockArrayRenderHelper;
import hellfirepvp.modularmachinery.client.util.DynamicMachineRenderContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import youyihj.herodotusutils.core.ModularMachineryPatches;

import java.util.Collections;

@Mixin(value = DynamicMachineRenderContext.class, remap = false)
public abstract class MixinDynamicMachineRenderContext {
    @Shadow
    @Final
    @Mutable
    private BlockArrayRenderHelper render;

    @Shadow
    @Final
    private Vec3i moveOffset;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injectInit(DynamicMachine machine, CallbackInfo ci) {
        BlockArray copy = new BlockArray(machine.getPattern(), this.moveOffset);
        copy.addBlock(new BlockPos(this.moveOffset), new BlockArray.BlockInformation(Collections.singletonList(new BlockArray.IBlockStateDescriptor(((ModularMachineryPatches.IDynamicMachinePatch) machine).getController().getDefaultState()))));
        this.render = ModularMachineryPatches.ClientStuff.createRenderHelper(copy);
    }
}
