package youyihj.herodotusutils.proxy;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import youyihj.herodotusutils.block.BlockOreBase;
import youyihj.herodotusutils.block.BlockRegistry;
import youyihj.herodotusutils.computing.ComputingUnitHandler;
import youyihj.herodotusutils.fluid.FluidMana;
import youyihj.herodotusutils.fluid.FluidMercury;
import youyihj.herodotusutils.modsupport.crafttweaker.CraftTweakerExtension;
import youyihj.herodotusutils.modsupport.modularmachinery.ModularMachineryPatches;

import java.io.IOException;

public class CommonProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        try {
            ModularMachineryPatches.loadAllCustomControllers();
        } catch (IOException e) {
            event.getModLog().error("failed to load custom controllers", e);
        }
        FluidRegistry.registerFluid(FluidMana.INSTANCE);
        FluidRegistry.registerFluid(FluidMercury.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMana.INSTANCE);
        FluidRegistry.addBucketForFluid(FluidMercury.INSTANCE);
        ComputingUnitHandler.register();
        CraftTweakerExtension.registerAllClasses();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        BlockRegistry.ORES.forEach(BlockOreBase::registerOreDict);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "youyihj.herodotusutils.modsupport.theoneprobe.TOPHandler");
    }
}
