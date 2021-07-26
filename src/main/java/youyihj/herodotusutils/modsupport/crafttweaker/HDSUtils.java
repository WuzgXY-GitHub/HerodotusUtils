package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IWorld;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.herodotusutils.entity.EntityRedSlime;

/**
 * @author youyihj
 */
@ZenClass("mods.hdsutils.HDSUtils")
public class HDSUtils {
    @ZenMethod
    public static void spawnRedSlime(IWorld world, Position3f position, double baseMaxHealth, double baseAttackStrength) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        EntityRedSlime entityRedSlime = new EntityRedSlime(mcWorld);
        entityRedSlime.baseMaxHealth = baseMaxHealth;
        entityRedSlime.baseAttackStrength = baseAttackStrength;
        entityRedSlime.setPosition(position.getX(), position.getY(), position.getZ());
        mcWorld.spawnEntity(entityRedSlime);
    }
}
