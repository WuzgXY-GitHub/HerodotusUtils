package youyihj.herodotusutils.mixins.mods.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import thaumcraft.common.entities.EntityFluxRift;

/**
 * @author youyihj
 */
@Pseudo
@Mixin(EntityFluxRift.class)
public abstract class MixinEntityFluxRift extends Entity {

    public MixinEntityFluxRift(World worldIn) {
        super(worldIn);
    }

    private static final DamageSource RIFT = new DamageSource("rift").setDamageBypassesArmor().setMagicDamage();

    @ModifyArg(method = "onUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    public DamageSource modifyDamageSource(DamageSource source) {
        return RIFT;
    }
}
