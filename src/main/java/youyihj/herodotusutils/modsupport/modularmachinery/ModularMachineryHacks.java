package youyihj.herodotusutils.modsupport.modularmachinery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hellfirepvp.modularmachinery.client.util.BlockArrayRenderHelper;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

public class ModularMachineryHacks {
    public static Constructor<RecipeCraftingContext.CraftingCheckResult> craftingCheckResultConstructor;
    public static Method checkResultAddErrorMethod;
    public static Method checkResultSetValidityMethod;

    static {
        try {
            Class<RecipeCraftingContext.CraftingCheckResult> craftingCheckResultClass = RecipeCraftingContext.CraftingCheckResult.class;
            craftingCheckResultConstructor = craftingCheckResultClass.getDeclaredConstructor();
            craftingCheckResultConstructor.setAccessible(true);
            checkResultAddErrorMethod = craftingCheckResultClass.getDeclaredMethod("addError", String.class);
            checkResultAddErrorMethod.setAccessible(true);
            checkResultSetValidityMethod = craftingCheckResultClass.getDeclaredMethod("setValidity", float.class);
            checkResultSetValidityMethod.setAccessible(true);
        } catch (Exception e) {
            HerodotusUtils.logger.throwing(e);
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

    public static RecipeCraftingContext.CraftingCheckResult createErrorResult(String message, float validity) {
        try {
            RecipeCraftingContext.CraftingCheckResult result = craftingCheckResultConstructor.newInstance();
            checkResultAddErrorMethod.invoke(result, message);
            checkResultSetValidityMethod.invoke(result, validity);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException("failed to create such a crafting check result", e);
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
                HerodotusUtils.logger.throwing(e);
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
