package youyihj.herodotusutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.fluid.FluidMana;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class BlockRegistry {
    private static final Block FLUID_MANA_BLOCK = new BlockFluidClassic(FluidMana.INSTANCE, Material.WATER).setRegistryName("fluid_mana");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(FLUID_MANA_BLOCK);
        registry.register(BlockMercury.INSTANCE);
        registry.register(BlockManaLiquidizer.INSTANCE);
        registry.register(PlainBlock.STRUCTURE_BLOCK_1);
        registry.register(PlainBlock.STRUCTURE_BLOCK_2);
        registry.register(PlainBlock.STRUCTURE_BLOCK_3);
        registry.register(BlockCalculatorController.CONTROLLER_1);
        registry.register(BlockCalculatorController.CONTROLLER_2);
        registry.register(BlockCalculatorController.CONTROLLER_3);
        registry.register(BlockComputingModule.INSTANCE);
        GameRegistry.registerTileEntity(TileManaLiquidizer.class, HerodotusUtils.rl("mana_liquidizer"));
        GameRegistry.registerTileEntity(TileCalculatorController.class, HerodotusUtils.rl("calculator_controller"));
        GameRegistry.registerTileEntity(TileComputingModule.class, HerodotusUtils.rl("computing_module"));
    }
}
