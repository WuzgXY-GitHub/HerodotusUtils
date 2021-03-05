package youyihj.herodotusutils.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.crafting.ActiveMachineRecipe;
import hellfirepvp.modularmachinery.common.data.Config;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import youyihj.herodotusutils.core.ModularMachineryPatches;

import javax.annotation.Nullable;

@Mixin(value = TileMachineController.class, remap = false)
public abstract class MixinTileMachineController extends TileEntityRestrictedTick {

    @Shadow
    private DynamicMachine foundMachine;

    @Shadow
    private TaggedPositionBlockArray foundPattern;

    @Shadow
    private EnumFacing patternRotation;

    @Shadow
    private DynamicMachine.ModifierReplacementMap foundReplacements;

    @Shadow
    private ActiveMachineRecipe activeRecipe;

    @Shadow
    private TileMachineController.CraftingStatus craftingStatus;

    @Nullable
    @Shadow
    public abstract DynamicMachine getBlueprintMachine();

    @Nullable
    @Shadow
    protected abstract boolean matchesRotation(TaggedPositionBlockArray pattern, DynamicMachine machine);

    @Shadow
    protected abstract void distributeCasingColor();

    @Redirect(method = "doRestrictedTick", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/tiles/TileMachineController;checkStructure()V"))
    private void checkStructure(TileMachineController tileMachineController) {
        if (this.ticksExisted % 20 == 0) {
            if (this.foundMachine != null && this.foundPattern != null && this.patternRotation != null) {
                if (this.foundMachine.requiresBlueprint() && !this.foundMachine.equals(this.getBlueprintMachine())) {
                    this.activeRecipe = null;
                    this.foundMachine = null;
                    this.foundPattern = null;
                    this.patternRotation = null;
                    this.foundReplacements = null;
                    this.craftingStatus = ModularMachineryPatches.MISSING_STRUCTURE;
                    this.markForUpdate();
                } else if (!this.foundPattern.matches(this.getWorld(), this.getPos(), true, this.foundReplacements)) {
                    this.activeRecipe = null;
                    this.foundMachine = null;
                    this.foundPattern = null;
                    this.patternRotation = null;
                    this.foundReplacements = null;
                    this.craftingStatus = ModularMachineryPatches.MISSING_STRUCTURE;
                    this.markForUpdate();
                }
            }

            if (this.foundMachine == null || this.foundPattern == null || this.patternRotation == null || this.foundReplacements == null) {
                this.foundMachine = null;
                this.foundPattern = null;
                this.patternRotation = null;
                this.foundReplacements = null;
                DynamicMachine blueprint = this.getBlueprintMachine();
                if (blueprint != null) {
                    if (this.matchesRotation(blueprint.getPattern(), blueprint)) {
                        this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockController.FACING, this.patternRotation));
                        this.markForUpdate();
                        if (this.foundMachine.getMachineColor() != Config.machineColor) {
                            this.distributeCasingColor();
                        }
                    }
                } else {
                    for (DynamicMachine machine : MachineRegistry.getRegistry()) {
                        if (machine.requiresBlueprint()) continue;
                        if (matchesRotation(machine.getPattern(), machine)) {
                            this.world.setBlockState(pos, this.world.getBlockState(this.pos).withProperty(BlockController.FACING, this.patternRotation));
                            markForUpdate();

                            if (this.foundMachine.getMachineColor() != Config.machineColor) {
                                distributeCasingColor();
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
