package youyihj.herodotusutils.modsupport.modularmachinery.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.herodotusutils.event.BaseEvent;

/**
 * @author youyihj
 */
public class MachineRecipeStartEvent extends BaseEvent {
    private final DynamicMachine machine;
    private final MachineRecipe recipe;
    private final World world;
    private final BlockPos pos;
    private String failureMessage;

    public MachineRecipeStartEvent(DynamicMachine machine, MachineRecipe recipe, World world, BlockPos pos) {
        this.machine = machine;
        this.recipe = recipe;
        this.world = world;
        this.pos = pos;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public BlockPos getPos() {
        return pos;
    }

    public World getWorld() {
        return world;
    }

    public DynamicMachine getMachine() {
        return machine;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public boolean isFailed() {
        return failureMessage != null;
    }
}
