package youyihj.herodotusutils.core;

import hellfirepvp.modularmachinery.client.util.BlockArrayRenderHelper;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.zenutils.util.ReflectUtils;

import java.lang.reflect.Constructor;

public class ModularMachineryPatches {
    public static TileMachineController.CraftingStatus MISSING_STRUCTURE;

    static {
        try {
            MISSING_STRUCTURE = ((TileMachineController.CraftingStatus) ReflectUtils.removePrivateFinal(TileMachineController.CraftingStatus.class, "MISSING_STRUCTURE").get(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IDynamicMachinePatch {
        void setController(BlockController controller);

        BlockController getController();
    }

    @SideOnly(Side.CLIENT)
    public static class ClientStuff {
        private static Constructor<BlockArrayRenderHelper> renderHelperConstructor;

        static {
            try {
                renderHelperConstructor = BlockArrayRenderHelper.class.getDeclaredConstructor(BlockArray.class);
                renderHelperConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static BlockArrayRenderHelper createRenderHelper(BlockArray blockArray) {
            try {
                return renderHelperConstructor.newInstance(blockArray);
            } catch (Exception e) {
                throw new RuntimeException("failed to create such a render helper instance", e);
            }
        }
    }
}
