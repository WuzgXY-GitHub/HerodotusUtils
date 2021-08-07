package youyihj.herodotusutils.util;

import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;
import youyihj.herodotusutils.network.NetworkHandler;
import youyihj.herodotusutils.network.TaintSyncMessage;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
@ZenClass("mods.hdsutils.ITaint")
public interface ITaint {
    int ORIGIN_MAX_VALUE = 100;
    int MODIFIED_VALUE_BOUND = 20;
    int UP_MAX_VALUE_BY_MODIFYING_TAINT = 5;

    @ZenGetter("maxValue")
    int getMaxValue();

    @ZenMethod
    void addMaxValue(int value);

    @ZenSetter("maxValue")
    void setMaxValue(int value);

    @ZenGetter("infected")
    int getInfectedTaint();

    @ZenGetter("permanent")
    int getPermanentTaint();

    @ZenGetter("sticky")
    int getStickyTaint();

    int getModifiedValue();

    @ZenGetter("total")
    default int getTotalValue() {
        return getInfectedTaint() + getPermanentTaint() + getStickyTaint();
    }

    @ZenMethod
    void addPermanentTaint(int value);

    @ZenMethod
    void addStickyTaint(int value);

    @ZenMethod
    void addInfectedTaint(int value);

    void sync(ITaint target);

    @ZenMethod
    void clear();

    void setModifiedValue(int modifiedValue);

    void setSyncDisabled(boolean flag);

    @ZenGetter("scale")
    default float getScale() {
        return (0.0f + getTotalValue()) / getMaxValue();
    }

    @ZenMethod
    default boolean moreThanScale(float value) {
        return getScale() >= value;
    }

    class Impl implements ITaint {
        private int maxValue = ORIGIN_MAX_VALUE;
        private int infected = 0;
        private int permanent = 0;
        private int sticky = 0;
        private int modifiedValue = 0;
        private boolean syncDisabled = false;
        private final @Nullable
        EntityPlayer player;

        public Impl(@Nullable EntityPlayer player) {
            this.player = player;
        }

        public Impl() {
            this(null);
        }

        @Override
        public int getMaxValue() {
            return maxValue;
        }

        @Override
        public void addMaxValue(int value) {
            maxValue += value;
            sendClientSyncMessage();
        }

        @Override
        public void setMaxValue(int maxValue) {
            this.maxValue = maxValue;
            sendClientSyncMessage();
        }

        @Override
        public int getInfectedTaint() {
            return infected;
        }

        @Override
        public int getPermanentTaint() {
            return permanent;
        }

        @Override
        public int getStickyTaint() {
            return sticky;
        }

        @Override
        public int getModifiedValue() {
            return modifiedValue;
        }

        @Override
        public void addPermanentTaint(int value) {
            addModifiedValue(value);
            permanent += restrictValueToMaxValue(value);
            sendClientSyncMessage();
        }

        @Override
        public void addStickyTaint(int value) {
            addModifiedValue(value);
            sticky += restrictValueToMaxValue(value);
            sendClientSyncMessage();
        }

        @Override
        public void addInfectedTaint(int value) {
            addModifiedValue(value);
            infected += restrictValueToMaxValue(value);
            sendClientSyncMessage();
        }

        private void addModifiedValue(int value) {
            if (value > 0) {
                modifiedValue += value;
            }
            if (modifiedValue >= MODIFIED_VALUE_BOUND) {
                maxValue += modifiedValue / MODIFIED_VALUE_BOUND * UP_MAX_VALUE_BY_MODIFYING_TAINT;
                modifiedValue %= MODIFIED_VALUE_BOUND;
            }
        }

        public void setModifiedValue(int modifiedValue) {
            this.modifiedValue = modifiedValue;
        }

        public void clear() {
            this.maxValue = ORIGIN_MAX_VALUE;
            this.infected = 0;
            this.permanent = 0;
            this.sticky = 0;
            this.modifiedValue = 0;
        }

        @Override
        public void sync(ITaint from) {
            this.maxValue = from.getMaxValue();
            this.infected = from.getInfectedTaint();
            this.sticky = from.getStickyTaint();
            this.permanent = from.getPermanentTaint();
            this.modifiedValue = from.getModifiedValue();
        }

        @Override
        public void setSyncDisabled(boolean flag) {
            syncDisabled = flag;
        }

        private int restrictValueToMaxValue(int value) {
            if (value + getTotalValue() >= getMaxValue()) {
                return getMaxValue() - getTotalValue();
            } else return value;
        }

        private void sendClientSyncMessage() {
            if (!syncDisabled && player != null && !player.world.isRemote) {
                NetworkHandler.INSTANCE.sendMessageToPlayer(new TaintSyncMessage().setTaint(this), player);
            }
        }
    }
}
