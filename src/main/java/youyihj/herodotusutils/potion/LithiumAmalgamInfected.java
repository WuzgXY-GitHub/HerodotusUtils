package youyihj.herodotusutils.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author youyihj
 */
public class LithiumAmalgamInfected extends Potion {
    private LithiumAmalgamInfected() {
        super(true, 0xcdcdcd);
        this.setRegistryName(NAME);
        this.setPotionName("effect.lithium_amalgam_infected");
    }

    private static final String NAME = "lithium_amalgam_infected";
    public static final LithiumAmalgamInfected INSTANCE = new LithiumAmalgamInfected();
    public static final DamageSource DAMAGE_SOURCE = new DamageSource(NAME).setMagicDamage();

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(DAMAGE_SOURCE, amplifier);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int k = 30 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

    @Mod.EventBusSubscriber
    public static class Registry {
        @SubscribeEvent
        public static void register(RegistryEvent.Register<Potion> event) {
            event.getRegistry().register(INSTANCE);
        }
    }
}
