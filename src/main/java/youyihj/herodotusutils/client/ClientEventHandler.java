package youyihj.herodotusutils.client;

import mezz.jei.startup.PlayerJoinedWorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.client.render.world.SkyblockRenderEvents;
import vazkii.botania.client.render.world.SkyblockSkyRenderer;
import youyihj.herodotusutils.client.render.RiftSkyRenderer;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    public static long ticks = 0;

    @SubscribeEvent
    public void onWorldTick(TickEvent.ClientTickEvent e) {
        if (e.phase != TickEvent.Phase.END || e.side != Side.CLIENT) {
            return;
        }
        ticks++;
    }

    @SubscribeEvent
    public void onRenderSky(RenderWorldLastEvent event) {
        World world = Minecraft.getMinecraft().world;
        if(world.provider.getDimension() == 0 && !(world.provider.getSkyRenderer() instanceof RiftSkyRenderer)) {
            world.provider.setSkyRenderer(new RiftSkyRenderer());
        }
    }
}
