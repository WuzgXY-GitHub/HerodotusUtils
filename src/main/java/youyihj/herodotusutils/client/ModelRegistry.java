package youyihj.herodotusutils.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.FluidContainerColorer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.BlockCalculatorController;
import youyihj.herodotusutils.block.BlockComputingModule;
import youyihj.herodotusutils.block.BlockManaLiquidizer;
import youyihj.herodotusutils.block.PlainBlock;
import youyihj.herodotusutils.fluid.FluidMana;
import youyihj.herodotusutils.fluid.FluidMercury;
import youyihj.herodotusutils.item.RefinedBottle;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ModelRegistry {
    @SubscribeEvent
    public static void register(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(FluidMana.INSTANCE.getBlock(), new StateMapperBase() {
            @Override
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(HerodotusUtils.rl(FluidMana.INSTANCE.getName()), "defaults");
            }
        });
        ModelLoader.setCustomStateMapper(FluidMercury.INSTANCE.getBlock(), new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(HerodotusUtils.rl(FluidMercury.INSTANCE.getName()), "defaults");
            }
        });
        registerItemModel(BlockManaLiquidizer.ITEM_BLOCK);
        registerItemModel(RefinedBottle.INSTANCE);
        registerItemModel(PlainBlock.STRUCTURE_BLOCK_1_ITEM);
        registerItemModel(PlainBlock.STRUCTURE_BLOCK_2_ITEM);
        registerItemModel(PlainBlock.STRUCTURE_BLOCK_3_ITEM);
        registerItemModel(BlockCalculatorController.ITEM_BLOCK_1);
        registerItemModel(BlockCalculatorController.ITEM_BLOCK_2);
        registerItemModel(BlockCalculatorController.ITEM_BLOCK_3);
        registerItemModel(BlockComputingModule.ITEM_BLOCK);
    }

    @SubscribeEvent
    public static void itemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler(new FluidContainerColorer(), RefinedBottle.INSTANCE);
    }

    public static void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
