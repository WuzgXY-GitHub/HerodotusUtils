package youyihj.herodotusutils.network;

import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Consumer;

/**
 * @author youyihj
 */
public enum PlayerSyncer {
    ALLOW_FLYING(player -> player.capabilities.allowFlying = true),
    DENY_FLYING(player -> {
        player.capabilities.allowFlying = false;
        player.capabilities.isFlying = false;
    }),
    SET_NO_CLIP(player -> player.noClip = true),
    SET_CLIP(player -> player.noClip = false);

    private final Consumer<EntityPlayer> syncer;

    PlayerSyncer(Consumer<EntityPlayer> syncer) {
        this.syncer = syncer;
    }

    public void sync(EntityPlayer player) {
        syncer.accept(player);
    }
}
