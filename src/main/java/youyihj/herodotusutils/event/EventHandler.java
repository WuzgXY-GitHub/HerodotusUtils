package youyihj.herodotusutils.event;

import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import youyihj.herodotusutils.block.BlockMercury;
import youyihj.herodotusutils.computing.event.ComputingUnitChangeEvent;
import youyihj.herodotusutils.item.RefinedBottle;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.component.ComponentAspectList;
import youyihj.herodotusutils.modsupport.modularmachinery.crafting.component.ComponentImpetus;
import youyihj.herodotusutils.potion.LithiumAmalgamInfected;
import youyihj.herodotusutils.util.Util;
import youyihj.zenutils.api.world.ZenUtilsWorld;
import youyihj.zenutils.impl.capability.ZenWorldCapabilityHandler;

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
            IItemHandler itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (world.getTotalWorldTime() % 40 == 0) {
                IData data = ZenUtilsWorld.getCustomChunkData(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(entity.getPosition()));
                Optional.ofNullable(data.memberGet(BlockMercury.TAG_POLLUTION))
                        .filter(IData::asBool)
                        .ifPresent(bool -> {
                            entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 1));
                        });

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
                    chunk.getCapability(ZenWorldCapabilityHandler.ZEN_WORLD_CAPABILITY, null).updateData(Util.createDataMap(BlockMercury.TAG_POLLUTION, new DataInt(0)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (event.getSource() == LithiumAmalgamInfected.DAMAGE_SOURCE) {
            event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5d, entity.posZ, OreDictionary.getOres("crystalLithium").get(0).copy()));
        }
    }

    @SubscribeEvent
    public static void onComputingUnitChange(ComputingUnitChangeEvent event) {
        event.getComputingUnit().removeInvalidEntry(event.getWorld());
    }

    @SubscribeEvent
    public static void onRegistryModularRequirements(ComponentType.ComponentRegistryEvent event) {
        ComponentType.Registry.register(new ComponentAspectList());
        ComponentType.Registry.register(new ComponentImpetus());
    }
}
