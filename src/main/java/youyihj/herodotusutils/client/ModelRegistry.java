package youyihj.herodotusutils.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.FluidContainerColorer;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.BlockManaLiquidizer;
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
        ModelLoader.setCustomModelResourceLocation(BlockManaLiquidizer.ITEM_BLOCK, 0,
                new ModelResourceLocation(HerodotusUtils.rl(BlockManaLiquidizer.NAME), "inventory")
        );
        ModelLoader.setCustomModelResourceLocation(RefinedBottle.INSTANCE, 0,
                new ModelResourceLocation(HerodotusUtils.rl(RefinedBottle.NAME), "inventory"));
    }

    @SubscribeEvent
    public static void itemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().registerItemColorHandler(new FluidContainerColorer(), RefinedBottle.INSTANCE);
    }
}
