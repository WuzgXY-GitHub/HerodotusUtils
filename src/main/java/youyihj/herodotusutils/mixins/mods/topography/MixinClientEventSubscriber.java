package youyihj.herodotusutils.mixins.mods.topography;

import com.bloodnbonesgaming.topography.event.ClientEventSubscriber;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author youyihj
 */
@Mixin(value = ClientEventSubscriber.class, remap = false)
public abstract class MixinClientEventSubscriber {
    /**
     * @author youyihj
     * @reason disable custom world selection gui of topography.
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    @Overwrite
    public void onOpenGui(GuiOpenEvent event) {
    }
}
