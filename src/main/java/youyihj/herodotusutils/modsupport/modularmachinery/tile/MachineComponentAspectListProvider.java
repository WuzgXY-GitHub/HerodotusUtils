package youyihj.herodotusutils.modsupport.modularmachinery.tile;

import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import hellfirepvp.modularmachinery.common.crafting.ComponentType.Registry;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;

/**
 * @author ikexing
 */
public class MachineComponentAspectListProvider extends MachineComponent<TileAspectListProvider> {

    private final TileAspectListProvider aspectListProvider;

    public MachineComponentAspectListProvider(TileAspectListProvider aspectListProvider, IOType ioType) {
        super(ioType);
        this.aspectListProvider = aspectListProvider;
    }

    @Override
    public ComponentType getComponentType() {
        return Registry.getComponent("aspectList");
    }

    @Override
    public TileAspectListProvider getContainerProvider() {
        return this.aspectListProvider;
    }
}
