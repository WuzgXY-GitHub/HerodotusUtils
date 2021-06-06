package youyihj.herodotusutils.util.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.computing.ComputingUnitCapabilityProvider;
import youyihj.herodotusutils.computing.IComputingUnit;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public final class Capabilities {
    private Capabilities() {
    }

    private static final ResourceLocation COMPUTING_UNIT_RL = HerodotusUtils.rl("computing_unit");
    private static final ResourceLocation ENTITY_CONTAINMENT_RL = HerodotusUtils.rl("entity_containment");

    @CapabilityInject(IComputingUnit.class)
    public static Capability<IComputingUnit> COMPUTING_UNIT = null;

    @CapabilityInject(IEntityContainment.class)
    public static Capability<IEntityContainment> ENTITY_CONTAINMENT = null;

    @SubscribeEvent
    public static void attachToChunk(AttachCapabilitiesEvent<Chunk> event) {
        event.addCapability(COMPUTING_UNIT_RL, new ComputingUnitCapabilityProvider());
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IComputingUnit.class, new Capability.IStorage<IComputingUnit>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IComputingUnit> capability, IComputingUnit instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IComputingUnit> capability, IComputingUnit instance, EnumFacing side, NBTBase nbt) {

            }
        }, IComputingUnit.Impl::new);

        CapabilityManager.INSTANCE.register(IEntityContainment.class, new Capability.IStorage<IEntityContainment>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IEntityContainment> capability, IEntityContainment instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IEntityContainment> capability, IEntityContainment instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT(((NBTTagString) nbt));
            }
        }, IEntityContainment.Impl::new);
    }

    @Mod.EventBusSubscriber
    public static class Attach {
        @SubscribeEvent
        public static void attachToChunk(AttachCapabilitiesEvent<Chunk> event) {
            event.addCapability(COMPUTING_UNIT_RL, new ComputingUnitCapabilityProvider());
        }

        @SubscribeEvent
        public static void attachToEntity(AttachCapabilitiesEvent<Entity> event) {
            event.addCapability(ENTITY_CONTAINMENT_RL, new EntityContainmentCapabilityProvider());
        }
    }
}
