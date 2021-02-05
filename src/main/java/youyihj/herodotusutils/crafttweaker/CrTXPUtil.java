package youyihj.herodotusutils.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crazypants.enderio.base.xp.XpUtil;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.hdsutils.XPUtil")
@SuppressWarnings("unused")
public class CrTXPUtil {
    @ZenMethod
    public static int getPlayerXP(IPlayer player) {
        return XpUtil.getPlayerXP(CraftTweakerMC.getPlayer(player));
    }

    @ZenMethod
    public static void addPlayerXP(IPlayer player, int amount) {
        XpUtil.addPlayerXP(CraftTweakerMC.getPlayer(player), amount);
    }

    @ZenMethod
    public static void removePlayerXP(IPlayer player, int amount) {
        XpUtil.addPlayerXP(CraftTweakerMC.getPlayer(player), -amount);
    }
}
