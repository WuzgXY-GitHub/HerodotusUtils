package youyihj.herodotusutils.computing;

import net.minecraft.nbt.NBTBase;
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

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class ComputingUnitHandler {
    private static final ResourceLocation COMPUTING_UNIT_RL = HerodotusUtils.rl("computing_unit");

    @CapabilityInject(IComputingUnit.class)
    public static Capability<IComputingUnit> COMPUTING_UNIT_CAPABILITY = null;

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
    }
}
