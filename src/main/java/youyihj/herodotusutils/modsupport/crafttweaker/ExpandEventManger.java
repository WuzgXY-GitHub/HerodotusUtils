package youyihj.herodotusutils.modsupport.crafttweaker;

import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.herodotusutils.modsupport.modularmachinery.event.MachineRecipeCompletedEvent;

@ZenExpansion("crafttweaker.events.IEventManager")
public class ExpandEventManger {
    private static final EventList<CrTMachineRecipeCompletedEvent> elMachineRecipeComplete = new EventList<>();

    @ZenMethod
    public static IEventHandle onMachineRecipeCompleted(IEventManager manager, IEventHandler<CrTMachineRecipeCompletedEvent> ev) {
        return elMachineRecipeComplete.add(ev);
    }

    @Mod.EventBusSubscriber
    public static class Handler {
        @SubscribeEvent
        public static void onMachineRecipeCompleted(MachineRecipeCompletedEvent event) {
            if (elMachineRecipeComplete.hasHandlers()) {
                elMachineRecipeComplete.publish(new CrTMachineRecipeCompletedEvent(event));
            }
        }
    }
}
