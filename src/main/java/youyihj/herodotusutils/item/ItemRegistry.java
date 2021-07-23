package youyihj.herodotusutils.item;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.herodotusutils.block.BlockManaLiquidizer;
import youyihj.herodotusutils.block.BlockOreBase;
import youyihj.herodotusutils.block.BlockRegistry;
import youyihj.herodotusutils.block.alchemy.*;
import youyihj.herodotusutils.block.computing.BlockCalculatorController;
import youyihj.herodotusutils.block.computing.BlockCalculatorStructure;
import youyihj.herodotusutils.block.computing.BlockComputingModule;
import youyihj.herodotusutils.block.computing.BlockTransporter;
import youyihj.herodotusutils.modsupport.modularmachinery.block.BlockMMController;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class ItemRegistry {
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                BlockManaLiquidizer.ITEM_BLOCK,
                RefinedBottle.INSTANCE,
                ItemCopperBucket.INSTANCE,
                BlockManaLiquidizer.ITEM_BLOCK,
                RefinedBottle.INSTANCE,
                ItemCopperBucket.INSTANCE,
                BlockCalculatorStructure.STRUCTURE_BLOCK_1_ITEM,
                BlockCalculatorStructure.STRUCTURE_BLOCK_2_ITEM,
                BlockCalculatorStructure.STRUCTURE_BLOCK_3_ITEM,
                BlockCalculatorController.ITEM_BLOCK_1,
                BlockCalculatorController.ITEM_BLOCK_2,
                BlockCalculatorController.ITEM_BLOCK_3,
                BlockComputingModule.ITEM_BLOCK,
                ItemLithiumAmalgam.INSTANCE,
                StarlightStorageTiny.INSTANCE,
                ItemOilAIOT.INSTANCE,
                BlockAlchemyController.ITEM_BLOCK,
                BlockPlainAlchemyTunnel.VERTICAL_ITEM,
                BlockPlainAlchemyTunnel.RIGHT_ANGLE_ITEM,
                BlockPlainAlchemyTunnel.STRAIGHT_ITEM,
                BlockAlchemyInputHatch.ITEM_BLOCK,
                BlockAlchemyOutputHatch.ITEM_BLOCK,
                BlockAlchemyRoundRobinTunnel.ITEM_BLOCK,
                BlockLazyAlchemyTunnel.ITEM_BLOCK
        );
        BlockRegistry.ORES.stream().map(BlockOreBase::getItem).forEach(registry::register);
        BlockTransporter.getItemBlockMap().values().forEach(registry::register);

        BlockMMController.CONTROLLER_ITEMS.forEach(registry::register);

    }
}
