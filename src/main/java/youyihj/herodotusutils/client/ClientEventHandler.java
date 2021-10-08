package youyihj.herodotusutils.client;

import com.google.gson.JsonSyntaxException;
import mezz.jei.startup.PlayerJoinedWorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.client.render.world.SkyblockRenderEvents;
import vazkii.botania.client.render.world.SkyblockSkyRenderer;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.client.render.RiftSkyRenderer;

import java.io.IOException;

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
        if(world.provider.getDimension() == 0 && !(world.provider.getSkyRenderer() instanceof RiftSkyRenderer)) {
            world.provider.setSkyRenderer(new RiftSkyRenderer());
        }
    }

}
