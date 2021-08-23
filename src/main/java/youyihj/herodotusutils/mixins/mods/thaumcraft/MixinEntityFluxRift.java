package youyihj.herodotusutils.mixins.mods.thaumcraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.entities.EntityFluxRift;
import youyihj.herodotusutils.util.Capabilities;
import youyihj.herodotusutils.util.ITaint;

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

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    public boolean injectOnUpdate(Entity entity, DamageSource source, float amount) {
        boolean flag = false;
        boolean attackResult = false;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = ((EntityLivingBase) entity);
            ITaint taint = living.getCapability(Capabilities.TAINT_CAPABILITY, null);
            if (taint != null && taint.moreThanScale(0.5f)) {
                amount *= 2;
                flag = true;
            }
            attackResult = living.attackEntityFrom(RIFT, amount);
            if (!flag && attackResult) {
                IAttributeInstance maxHealth = living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
                maxHealth.applyModifier(new AttributeModifier("Rift Minus", -amount, Constants.AttributeModifierOperation.ADD));
            }
        }
        return attackResult;
    }
}
