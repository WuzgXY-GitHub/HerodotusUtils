package youyihj.herodotusutils.proxy;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.BlockOreBase;
import youyihj.herodotusutils.block.BlockRegistry;
import youyihj.herodotusutils.fluid.FluidMana;
import youyihj.herodotusutils.fluid.FluidMercury;
import youyihj.herodotusutils.modsupport.crafttweaker.CraftTweakerExtension;
import youyihj.herodotusutils.network.GuiHandler;
import youyihj.herodotusutils.util.Capabilities;

public class CommonProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        FluidRegistry.registerFluid(FluidMana.INSTANCE);
        FluidRegistry.registerFluid(FluidMercury.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMana.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMercury.INSTANCE);
        Capabilities.register();
        CraftTweakerExtension.registerAllClasses();
        NetworkRegistry.INSTANCE.registerGuiHandler(HerodotusUtils.MOD_ID, GuiHandler.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BlockRegistry.ORES.forEach(BlockOreBase::registerOreDict);
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "youyihj.herodotusutils.modsupport.theoneprobe.TOPHandler");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
}
