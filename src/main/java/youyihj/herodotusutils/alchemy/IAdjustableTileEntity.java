package youyihj.herodotusutils.alchemy;

import crafttweaker.api.util.Position3f;
import net.minecraft.util.EnumFacing;

/**
 * @author youyihj
 */
public interface IAdjustableTileEntity {
    void adjust(EnumFacing facing, Position3f hitPosition);
}
