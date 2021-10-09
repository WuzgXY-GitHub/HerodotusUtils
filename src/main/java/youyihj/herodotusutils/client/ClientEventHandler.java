package youyihj.herodotusutils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.herodotusutils.client.render.RiftSkyRenderer;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler {
    public static long ticks = 0;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.side != Side.CLIENT) {
            return;
        }
        ticks++;
    }

    @SubscribeEvent
    public static void onRenderSky(RenderWorldLastEvent event) {
        World world = Minecraft.getMinecraft().world;
        if (world.provider.getDimension() == 9 && !(world.provider.getSkyRenderer() instanceof RiftSkyRenderer)) {
            world.provider.setSkyRenderer(new RiftSkyRenderer());
        }
    }

}
