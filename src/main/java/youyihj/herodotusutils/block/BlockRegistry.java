package youyihj.herodotusutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.fluid.FluidMana;
import youyihj.herodotusutils.fluid.FluidMercury;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class BlockRegistry {
    private static final Block FLUID_MANA_BLOCK = new BlockFluidClassic(FluidMana.INSTANCE, Material.WATER).setRegistryName("fluid_mana");
    private static final Block MERCURY_BLOCK = new BlockFluidClassic(FluidMercury.INSTANCE, Material.WATER).setRegistryName("mercury");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(FLUID_MANA_BLOCK);
        event.getRegistry().register(MERCURY_BLOCK);
        event.getRegistry().register(BlockManaLiquidizer.INSTANCE);
        GameRegistry.registerTileEntity(TileManaLiquidizer.class, HerodotusUtils.rl("mana_liquidizer"));
    }
}
