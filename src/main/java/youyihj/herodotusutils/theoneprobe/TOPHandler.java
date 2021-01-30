package youyihj.herodotusutils.theoneprobe;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.TileComputingModule;
import youyihj.herodotusutils.computing.*;

import java.util.function.Function;

/**
 * @author youyihj
 */
public class TOPHandler implements Function<ITheOneProbe, Void> {
    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        iTheOneProbe.registerProvider(new IProbeInfoProvider() {
            @Override
            public String getID() {
                return HerodotusUtils.MOD_ID;
            }

            @Override
            public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                TileEntity tileEntity = world.getTileEntity(data.getPos());
                if (tileEntity instanceof IComputingUnitInteract) {
                    IComputingUnit computingUnit = world.getChunkFromBlockCoords(data.getPos()).getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null);
                    probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.bar", computingUnit.totalConsumePower(), computingUnit.totalGeneratePower())).getUnformattedComponentText());
                }
                if (tileEntity instanceof IComputingUnitGenerator) {
                    probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.generate", ((IComputingUnitGenerator) tileEntity).generateAmount())).getUnformattedComponentText());
                }
                if (tileEntity instanceof IComputingUnitConsumer) {
                    probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.consume", ((IComputingUnitConsumer) tileEntity).consumeAmount())).getUnformattedComponentText());
                }
                if (tileEntity instanceof TileComputingModule) {
                    probeInfo.item(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).copy());
                }
            }
        });
        return null;
    }
}
