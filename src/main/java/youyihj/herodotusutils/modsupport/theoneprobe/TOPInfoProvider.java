package youyihj.herodotusutils.modsupport.theoneprobe;

import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.CapabilityItemHandler;
import youyihj.herodotusutils.HerodotusUtils;
import youyihj.herodotusutils.block.computing.TileComputingModule;
import youyihj.herodotusutils.computing.*;
import youyihj.herodotusutils.computing.event.ComputingUnitChangeEvent;

/**
 * @author youyihj
 */
public enum TOPInfoProvider implements IProbeInfoProvider {
    INSTANCE;

    @Override
    public String getID() {
        return HerodotusUtils.MOD_ID;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity tileEntity = world.getTileEntity(data.getPos());
        if (tileEntity instanceof IComputingUnitInteract) {
            Chunk chunk = world.getChunkFromBlockCoords(data.getPos());
            IComputingUnit computingUnit = chunk.getCapability(ComputingUnitHandler.COMPUTING_UNIT_CAPABILITY, null);
            new ComputingUnitChangeEvent(computingUnit, chunk).post();
            probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.bar", computingUnit.totalConsumePower(), computingUnit.totalGeneratePower())).getUnformattedComponentText());
        }
        if (tileEntity instanceof IComputingUnitGenerator) {
            probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.generate", ((IComputingUnitGenerator) tileEntity).generateAmount())).getUnformattedComponentText());
        }
        if (tileEntity instanceof IComputingUnitConsumer) {
            probeInfo.text(TextStyleClass.INFO + (new TextComponentTranslation("hdsutils.computing_unit.consume", ((IComputingUnitConsumer) tileEntity).consumeAmount())).getUnformattedComponentText());
        }
        if (tileEntity instanceof TileComputingModule) {
            ItemStack itemStack = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH).getStackInSlot(0).copy();
            if (!itemStack.isEmpty()) {
                probeInfo.element(new ElementItemWithName(itemStack));
            }
        }
    }
}
