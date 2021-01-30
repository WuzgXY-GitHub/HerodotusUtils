package youyihj.herodotusutils.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.herodotusutils.block.BlockCalculatorController;
import youyihj.herodotusutils.block.BlockComputingModule;
import youyihj.herodotusutils.block.BlockManaLiquidizer;
import youyihj.herodotusutils.block.PlainBlock;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class ItemRegistry {
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(BlockManaLiquidizer.ITEM_BLOCK);
        registry.register(RefinedBottle.INSTANCE);
        registry.register(PlainBlock.STRUCTURE_BLOCK_1_ITEM);
        registry.register(PlainBlock.STRUCTURE_BLOCK_2_ITEM);
        registry.register(PlainBlock.STRUCTURE_BLOCK_3_ITEM);
        registry.register(BlockCalculatorController.ITEM_BLOCK_1);
        registry.register(BlockCalculatorController.ITEM_BLOCK_2);
        registry.register(BlockCalculatorController.ITEM_BLOCK_3);
        registry.register(BlockComputingModule.ITEM_BLOCK);
    }
}
