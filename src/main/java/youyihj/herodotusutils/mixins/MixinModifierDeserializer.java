package youyihj.herodotusutils.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hellfirepvp.modularmachinery.common.modifier.ModifierReplacement;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author youyihj
 */
@Mixin(value = ModifierReplacement.Deserializer.class, remap = false)
public abstract class MixinModifierDeserializer {
    @Inject(method = "deserialize", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    @SuppressWarnings("deprecation")
    public void injectDeserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<ModifierReplacement> cir, NBTTagCompound match, JsonObject part, BlockArray.BlockInformation blockInfo, JsonElement partElement, JsonElement elementModifiers, List<RecipeModifier> modifiers, String description) {
        cir.setReturnValue(new ModifierReplacement(blockInfo, modifiers, I18n.translateToLocal(description)));
    }
}
