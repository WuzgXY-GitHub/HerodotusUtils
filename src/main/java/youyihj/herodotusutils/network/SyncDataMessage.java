package youyihj.herodotusutils.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author youyihj
 */
public class SyncDataMessage implements IMessage {
    private PlayerSyncer syncer;

    public SyncDataMessage setSyncer(PlayerSyncer syncer) {
        this.syncer = syncer;
        return this;
    }

    public PlayerSyncer getSyncer() {
        return syncer;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.syncer = PlayerSyncer.values()[buf.readInt()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(syncer.ordinal());
    }

    public static class Handler implements IMessageHandler<SyncDataMessage, IMessage> {

        @Override
        public IMessage onMessage(SyncDataMessage message, MessageContext ctx) {
            message.syncer.sync(Minecraft.getMinecraft().player);
            return null;
        }
    }
}
