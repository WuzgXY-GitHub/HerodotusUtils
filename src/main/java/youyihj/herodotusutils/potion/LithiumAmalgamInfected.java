package youyihj.herodotusutils.potion;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.brackets.BracketHandlerOre;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
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
    private static final DamageSource DAMAGE_SOURCE = new DamageSource(NAME).setMagicDamage();

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(DAMAGE_SOURCE, amplifier);
        if (entityLivingBaseIn.isDead) {
            World world = entityLivingBaseIn.world;
            if (!world.isRemote) {
                ItemStack stack = CraftTweakerMC.getItemStack(BracketHandlerOre.getOre("crystalLithium").getFirstItem());
                if (stack.isEmpty()) return;
                entityLivingBaseIn.world.spawnEntity(
                        new EntityItem(entityLivingBaseIn.world, entityLivingBaseIn.posX, entityLivingBaseIn.posY + 0.5f, entityLivingBaseIn.posZ, stack)
                );
            }
        }
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
