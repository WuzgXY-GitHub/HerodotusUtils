package youyihj.herodotusutils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import youyihj.herodotusutils.computing.ComputingUnitHandler;
import youyihj.herodotusutils.fluid.FluidMana;
import youyihj.herodotusutils.fluid.FluidMercury;

@Mod(
        modid = HerodotusUtils.MOD_ID,
        name = HerodotusUtils.MOD_NAME,
        version = HerodotusUtils.VERSION
)
public class HerodotusUtils {

    public static final String MOD_ID = "hdsutils";
    public static final String MOD_NAME = "HerodotusUtils";
    public static final String VERSION = "1.0";

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static HerodotusUtils INSTANCE;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        FluidRegistry.enableUniversalBucket();
    }

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        FluidRegistry.registerFluid(FluidMana.INSTANCE);
        FluidRegistry.registerFluid(FluidMercury.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMana.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMercury.INSTANCE);
        ComputingUnitHandler.register();
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "youyihj.herodotusutils.theoneprobe.TOPHandler");
    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
