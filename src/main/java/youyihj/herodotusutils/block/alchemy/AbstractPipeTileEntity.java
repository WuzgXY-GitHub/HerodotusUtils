package youyihj.herodotusutils.block.alchemy;

import net.minecraft.tileentity.TileEntity;
import youyihj.herodotusutils.alchemy.IPipe;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public abstract class AbstractPipeTileEntity extends TileEntity implements IPipe {
    protected TileAlchemyController controller;

    @Nullable
    @Override
    public TileAlchemyController getLinkedController() {
        return controller;
    }

    @Override
    public void setLinkedController(TileAlchemyController tileAlchemyController) {
        controller = tileAlchemyController;
    }
}
