package youyihj.herodotusutils.modsupport.modularmachinery;

import hellfirepvp.modularmachinery.common.block.BlockController;

public interface IDynamicMachinePatch {
    void setController(BlockController controller);

    BlockController getController();
}
