package youyihj.herodotusutils.mixins.init;

import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

// loaded after JEID
@Mixin(value = Loader.class, remap = false, priority = 800)
public abstract class MixinLoader {
    @Inject(method = "loadMods", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/LoadController;transition(Lnet/minecraftforge/fml/common/LoaderState;Z)V", ordinal = 1), remap = false)
    private void initMixins(List<String> injectedModContainers, CallbackInfo ci) {
        LogManager.getLogger("hdsutils mixins").info("registering mod mixins...");
        Mixins.addConfiguration("mixins.hdsutils.json");
    }
}
