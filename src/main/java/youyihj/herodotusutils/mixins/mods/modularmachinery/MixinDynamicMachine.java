package youyihj.herodotusutils.mixins.mods.modularmachinery;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import org.spongepowered.asm.mixin.Mixin;
import youyihj.herodotusutils.modsupport.modularmachinery.IDynamicMachinePatch;

@Mixin(value = DynamicMachine.class, remap = false)
public abstract class MixinDynamicMachine implements IDynamicMachinePatch {
    private BlockController controller;

    @Override
    public void setController(BlockController controller) {
        this.controller = controller;
    }

    @Override
    public BlockController getController() {
        return controller;
    }
}
