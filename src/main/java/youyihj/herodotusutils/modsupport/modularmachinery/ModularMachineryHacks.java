package youyihj.herodotusutils.modsupport.modularmachinery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hellfirepvp.modularmachinery.client.util.BlockArrayRenderHelper;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.modsupport.modularmachinery.block.BlockMMController;
import youyihj.zenutils.impl.util.ReflectUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Objects;

public class ModularMachineryHacks {
    public static TileMachineController.CraftingStatus MISSING_STRUCTURE;

    static {
        try {
            MISSING_STRUCTURE = ((TileMachineController.CraftingStatus) ReflectUtils.removePrivateFinal(TileMachineController.CraftingStatus.class, "MISSING_STRUCTURE").get(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAllCustomControllers() throws IOException {
        File machineryDir = new File("config/modularmachinery/machinery");
        if (machineryDir.exists() && machineryDir.isDirectory()) {
            for (File file : Objects.requireNonNull(machineryDir.listFiles())) {
                if (file.getName().endsWith(".json")) {
                    FileReader reader = new FileReader(file);
                    Gson gson = new GsonBuilder().registerTypeAdapter(BlockMMController.class, MachineJsonPreReader.INSTANCE).create();
                    gson.fromJson(reader, BlockMMController.class);
                }
            }
        }
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

        public static void writeAllCustomControllerModels() throws IOException {
            IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
            for (BlockMMController controller : BlockMMController.CONTROLLERS) {
                IResource blockStateResource = resourceManager.getResource(HerodotusUtils.rl("blockstates/mm_controller.json"));
                File blockStateFile = new File("resources/hdsutils/blockstates/" + controller.getRegistryName().getResourcePath() + ".json");
                if (!blockStateFile.exists()) {
                    IOUtils.copy(blockStateResource.getInputStream(), FileUtils.openOutputStream(blockStateFile));
                }
            }
        }
    }
}
