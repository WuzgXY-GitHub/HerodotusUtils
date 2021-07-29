package youyihj.herodotusutils.modsupport.modularmachinery.tile;

import hellfirepvp.modularmachinery.common.crafting.ComponentType;
import hellfirepvp.modularmachinery.common.machine.MachineComponent;

/**
 * @author youyihj
 */
public class MachineComponentImpetus extends MachineComponent<TileImpetusComponent> {
    private final TileImpetusComponent provider;

    public MachineComponentImpetus(IOType ioType, TileImpetusComponent provider) {
        super(ioType);
        this.provider = provider;
    }

    @Override
    public ComponentType<?> getComponentType() {
        return ComponentType.Registry.getComponent("impetus");
    }

    @Override
    public TileImpetusComponent getContainerProvider() {
        return provider;
    }
}
