package youyihj.herodotusutils.network;

import crafttweaker.CraftTweakerAPI;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import youyihj.herodotusutils.modsupport.crafttweaker.ExpandPlayer;
import youyihj.herodotusutils.util.ITaint;

/**
 * @author youyihj
 */
public class TaintSyncMessage implements IMessage {
    private ITaint taint;

    public TaintSyncMessage setTaint(ITaint taint) {
        this.taint = taint;
        return this;
    }

    public ITaint getTaint() {
        return taint;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.taint = new ITaint.Impl(null);
        taint.setSyncDisabled(true);
        taint.setMaxValue(buf.readInt());
        taint.addInfectedTaint(buf.readInt());
        taint.addStickyTaint(buf.readInt());
        taint.addPermanentTaint(buf.readInt());
        taint.setModifiedValue(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(taint.getMaxValue());
        buf.writeInt(taint.getInfectedTaint());
        buf.writeInt(taint.getStickyTaint());
        buf.writeInt(taint.getPermanentTaint());
        buf.writeInt(taint.getModifiedValue());
    }

    public static class Handler implements IMessageHandler<TaintSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(TaintSyncMessage message, MessageContext ctx) {
            ExpandPlayer.getTaint(CraftTweakerAPI.client.getPlayer()).sync(message.getTaint());
            return null;
        }
    }
}
