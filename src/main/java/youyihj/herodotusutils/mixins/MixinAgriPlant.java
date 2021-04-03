package youyihj.herodotusutils.mixins;

import com.agricraft.agricore.plant.AgriPlant;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@Mixin(value = AgriPlant.class, remap = false)
public abstract class MixinAgriPlant {
    @Shadow
    @Final
    private String seed_name;

    @Shadow
    @Final
    private String plant_name;

    @Inject(method = "getSeedName", at = @At(value = "RETURN"), cancellable = true)
    public void injectGetSeedName(CallbackInfoReturnable<String> ci) {
        String translationKey = Arrays.stream(this.seed_name.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.joining("_", "hdsutils.agricraft.", ""));
        if (I18n.canTranslate(translationKey)) {
            ci.setReturnValue(I18n.translateToLocal(translationKey));
        }
    }

    @Inject(method = "getPlantName", at = @At(value = "RETURN"), cancellable = true)
    public void injectGetPlantName(CallbackInfoReturnable<String> ci) {
        String translationKey = Arrays.stream(this.plant_name.split(" "))
                .map(String::toLowerCase)
                .collect(Collectors.joining("_", "hdsutils.agricraft.", ""));
        if (I18n.canTranslate(translationKey)) {
            ci.setReturnValue(I18n.translateToLocal(translationKey));
        }
    }
}
