package youyihj.herodotusutils.event;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import youyihj.herodotusutils.block.BlockMercury;
import youyihj.herodotusutils.computing.event.ComputingUnitChangeEvent;
import youyihj.herodotusutils.item.RefinedBottle;
import youyihj.zenutils.capability.ZenWorldCapabilityHandler;
import youyihj.zenutils.world.ZenUtilsWorld;

import java.util.Optional;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onEntityLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        World world = entity.world;
        if (!world.isRemote) {
            IData data = ZenUtilsWorld.getCustomChunkData(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(entity.getPosition()));
            Optional.ofNullable(data.memberGet(BlockMercury.TAG_POLLUTION))
                    .filter(IData::asBool)
                    .ifPresent(bool -> {
                        entity.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 200, 1));
                    });

            IItemHandler itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (world.getTotalWorldTime() % 40 == 0) {
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    ItemStack itemStack = itemHandler.getStackInSlot(i);
                    if (itemStack.getItem() == RefinedBottle.INSTANCE)
                        continue;
                    FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
                    if (fluidStack != null && fluidStack.getFluid().getName().equals("mercury")) {
                        entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 3));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        if (world instanceof WorldServer) {
            for (Chunk chunk : ((WorldServer) world).getChunkProvider().getLoadedChunks()) {
                if (world.rand.nextInt(5000) == 0) {
                    chunk.getCapability(ZenWorldCapabilityHandler.ZEN_WORLD_CAPABILITY, null).updateData(
                            new DataMap(Maps.asMap(Sets.newHashSet(BlockMercury.TAG_POLLUTION), (s) -> new DataInt(0)), true)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void onComputingUnitChange(ComputingUnitChangeEvent event) {
        event.getComputingUnit().removeInvalidEntry(event.getWorld());
    }
}
