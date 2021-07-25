package youyihj.herodotusutils.entity;

import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import youyihj.herodotusutils.HerodotusUtils;

@EventBusSubscriber
public class EntityRegisterHandler {
    public static final EntityEntry ENTITY_RED_SLIME = EntityEntryBuilder.create().entity(EntitySlimeKing.class).
        id("slime_king", 0).name(HerodotusUtils.MOD_ID + ".slime_king").tracker(80, 3, true).build();


    @SubscribeEvent
    public static void onRegistry(Register<EntityEntry> event) {
        event.getRegistry().register(ENTITY_RED_SLIME);
    }
}
