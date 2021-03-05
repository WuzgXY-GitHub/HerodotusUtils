package youyihj.herodotusutils.core;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.util.math.BlockPos;
import youyihj.zenutils.util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

public class ModularMachineryPatches {
    public static TileMachineController.CraftingStatus MISSING_STRUCTURE;
    public static Field arrayPatternField;

    static {
        try {
            MISSING_STRUCTURE = ((TileMachineController.CraftingStatus) ReflectUtils.removePrivateFinal(TileMachineController.CraftingStatus.class, "MISSING_STRUCTURE").get(null));
            arrayPatternField = BlockArray.class.getDeclaredField("pattern");
            arrayPatternField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void setControllerBlock(BlockArray blockArray, BlockController controller) {
        try {
            Map<BlockPos, BlockArray.BlockInformation> pattern = ((Map<BlockPos, BlockArray.BlockInformation>) arrayPatternField.get(blockArray));
            pattern.put(BlockPos.ORIGIN, new BlockArray.BlockInformation(Collections.singletonList(new BlockArray.IBlockStateDescriptor(controller.getDefaultState()))));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
