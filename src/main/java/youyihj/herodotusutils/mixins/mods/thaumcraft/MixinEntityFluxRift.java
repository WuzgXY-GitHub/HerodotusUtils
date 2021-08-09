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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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
    private static final UUID RIFT_MINUS_MAX_HEALTH_MODIFIER = UUID.nameUUIDFromBytes("rift".getBytes(StandardCharsets.UTF_8));

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
                AttributeModifier modifier = maxHealth.getModifier(RIFT_MINUS_MAX_HEALTH_MODIFIER);
                double decrease = -amount;
                if (modifier != null) {
                    decrease += modifier.getAmount();
                    maxHealth.removeModifier(RIFT_MINUS_MAX_HEALTH_MODIFIER);
                }
                maxHealth.applyModifier(new AttributeModifier(RIFT_MINUS_MAX_HEALTH_MODIFIER, "Rift Minus", decrease, Constants.AttributeModifierOperation.ADD));
            }
        }
        return attackResult;
    }
}
