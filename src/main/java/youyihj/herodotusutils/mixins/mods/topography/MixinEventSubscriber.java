package youyihj.herodotusutils.mixins.mods.topography;

import com.bloodnbonesgaming.topography.event.EventSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author youyihj
 */
@Mixin(EventSubscriber.class)
public class MixinEventSubscriber {
    /**
     * @author youyihj
     * @reason disable crashed code
     */
    @Overwrite
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    }
}
