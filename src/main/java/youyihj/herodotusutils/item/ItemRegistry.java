package youyihj.herodotusutils.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.herodotusutils.block.*;
import youyihj.herodotusutils.modsupport.modularmachinery.block.BlockMMController;

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
        registry.register(ItemCopperBucket.INSTANCE);
        registry.register(BlockCalculatorStructure.STRUCTURE_BLOCK_1_ITEM);
        registry.register(BlockCalculatorStructure.STRUCTURE_BLOCK_2_ITEM);
        registry.register(BlockCalculatorStructure.STRUCTURE_BLOCK_3_ITEM);
        registry.register(BlockCalculatorController.ITEM_BLOCK_1);
        registry.register(BlockCalculatorController.ITEM_BLOCK_2);
        registry.register(BlockCalculatorController.ITEM_BLOCK_3);
        BlockRegistry.ORES.stream().map(BlockOreBase::getItem).forEach(registry::register);
        BlockTransporter.getItemBlockMap().values().forEach(registry::register);
        registry.register(BlockComputingModule.ITEM_BLOCK);
        BlockMMController.CONTROLLER_ITEMS.forEach(registry::register);
        registry.register(ItemLithiumAmalgam.INSTANCE);
        registry.register(StarlightStorageTiny.INSTANCE);
        registry.register(ItemOilAIOT.INSTANCE);
    }
}
