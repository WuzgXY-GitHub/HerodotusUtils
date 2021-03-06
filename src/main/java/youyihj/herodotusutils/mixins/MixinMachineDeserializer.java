package youyihj.herodotusutils.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.lib.BlocksMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.block.Block;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.herodotusutils.core.ModularMachineryPatches;

import java.lang.reflect.Type;

@Mixin(value = DynamicMachine.MachineDeserializer.class, remap = false)
public abstract class MixinMachineDeserializer {

    @Inject(method = "deserialize", at = @At("RETURN"))
    public void injectDeserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<DynamicMachine> cir) {
        String controllerID = JsonUtils.getString(json.getAsJsonObject(), "controller", BlocksMM.blockController.getRegistryName().toString());
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(controllerID));
        if (block instanceof BlockController) {
            DynamicMachine dynamicMachine = cir.getReturnValue();
            ((ModularMachineryPatches.IDynamicMachinePatch) dynamicMachine).setController(((BlockController) block));
        } else {
            throw new IllegalArgumentException(controllerID + "is not a MM controller!");
        }
    }
}
