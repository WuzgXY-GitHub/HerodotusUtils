package youyihj.herodotusutils.entity;

import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import youyihj.herodotusutils.HerodotusUtils;

import java.awt.*;

@EventBusSubscriber
public class EntityRegisterHandler {
    public static final EntityEntry ENTITY_RED_SLIME = EntityEntryBuilder.create()
            .entity(EntityRedSlime.class)
            .id("red_slime", 0)
            .name(HerodotusUtils.MOD_ID + ".red_slime")
            .tracker(80, 3, true)
            .egg(Color.RED.getRGB(), Color.MAGENTA.getRGB())
            .build();

    public static final EntityEntry ENTITY_EXTRA_GOLEM = EntityEntryBuilder.create()
            .entity(EntityExtraGolem.class)
            .id("alchemy_golem", 0)
            .name(HerodotusUtils.MOD_ID + ".alchemy_golem")
            .build();


    @SubscribeEvent
    public static void onRegistry(Register<EntityEntry> event) {
        event.getRegistry().registerAll(ENTITY_RED_SLIME, ENTITY_EXTRA_GOLEM);
    }
}
