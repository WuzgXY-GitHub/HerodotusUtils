package youyihj.herodotusutils.util;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

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

        @Override
        public int getMaxValue() {
            return maxValue;
        }

        @Override
        public void addMaxValue(int value) {
            maxValue += value;
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
        }

        @Override
        public void addStickyTaint(int value) {
            addModifiedValue(value);
            sticky += restrictValueToMaxValue(value);
        }

        @Override
        public void addInfectedTaint(int value) {
            addModifiedValue(value);
            infected += restrictValueToMaxValue(value);
        }

        private void addModifiedValue(int value) {
            if (value > 0) {
                modifiedValue += value;
            }
            if (modifiedValue >= MODIFIED_VALUE_BOUND) {
                addMaxValue(modifiedValue / MODIFIED_VALUE_BOUND * UP_MAX_VALUE_BY_MODIFYING_TAINT);
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

        private int restrictValueToMaxValue(int value) {
            if (value + getTotalValue() >= getMaxValue()) {
                return getMaxValue() - getTotalValue();
            } else return value;
        }

        @Override
        public void sync(ITaint target) {
            target.clear();
            target.addInfectedTaint(infected);
            target.addPermanentTaint(permanent);
            target.addMaxValue(this.maxValue - ORIGIN_MAX_VALUE);
            target.addStickyTaint(sticky);
            target.setModifiedValue(modifiedValue);
        }
    }
}
