package youyihj.herodotusutils.alchemy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.text.ITextComponent;

/**
 * @author youyihj
 */
public interface IAdjustableBlock {
    IBlockState getAdjustedResult(IBlockState previous);

    ITextComponent getAdjustedMessage(IBlockState state);
}
