package youyihj.herodotusutils.modsupport.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import youyihj.herodotusutils.block.BlockRegistry;

public class AspectHandler {
    private static final Aspect HDS =
            new Aspect("Herodotus", 0XFF9D00,
            new Aspect[]{Aspect.LIGHT, Aspect.ORDER},
            new ResourceLocation("hdsutils:textures/icon.png"), 1);
    private static boolean registered = false;
    private static AspectHandler INSTANCE = new AspectHandler();

    public static void register() {
        if (!registered) {
            MinecraftForge.EVENT_BUS.register(INSTANCE);
            registered = true;
        }
    }

    @SubscribeEvent
    public void aspectRegisterEvent(AspectRegistryEvent event) {
        event.register.registerObjectTag(new ItemStack(BlockRegistry.RED_ORE),
                new AspectList().add(Aspect.EARTH, 5).add(HDS, 5));
        event.register.registerObjectTag(new ItemStack(BlockRegistry.BLUE_ORE),
                new AspectList().add(Aspect.EARTH, 5).add(HDS, 5));
        event.register.registerObjectTag(new ItemStack(BlockRegistry.YELLOW_ORE),
                new AspectList().add(Aspect.EARTH, 5).add(HDS, 5));
    }
}

