package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import youyihj.herodotusutils.util.Capabilities;
import youyihj.herodotusutils.util.ITaint;

/**
 * @author youyihj
 */
@ZenExpansion("crafttweaker.player.IPlayer")
public class ExpandPlayer {
    @ZenGetter("taint")
    public static ITaint getTaint(IPlayer player) {
        return CraftTweakerMC.getPlayer(player).getCapability(Capabilities.TAINT_CAPABILITY, null);
    }
}
