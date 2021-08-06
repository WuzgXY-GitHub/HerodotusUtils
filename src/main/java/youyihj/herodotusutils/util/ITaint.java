package youyihj.herodotusutils.util;

/**
 * @author youyihj
 */
public interface ITaint {
    int getMaxValue();

    void addMaxValue(int value);

    int getInfectedTaint();

    int getPermanentTaint();

    int getStickyTaint();

    int getModifiedValue();

    default int getTotalValue() {
        return getInfectedTaint() + getPermanentTaint() + getStickyTaint();
    }

    void addPermanentTaint(int value);

    void addStickyTaint(int value);

    void addInfectedTaint(int value);

    void sync(ITaint target);

    void clear();

    void setModifiedValue(int modifiedValue);

    default float getScale() {
        return (0.0f + getTotalValue()) / getMaxValue();
    }

    default boolean moreThanScale(float value) {
        return getScale() >= value;
    }

    class Impl implements ITaint {
        private int maxValue = 100;
        private int infected = 0;
        private int permanent = 0;
        private int sticky = 0;
        private int modifiedValue = 0;

        public Impl() {
        }

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
            if (modifiedValue >= 20) {
                addMaxValue(modifiedValue / 20 * 5);
                modifiedValue %= 20;
            }
        }

        public void setModifiedValue(int modifiedValue) {
            this.modifiedValue = modifiedValue;
        }

        public void clear() {
            this.maxValue = 0;
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
            target.addMaxValue(maxValue);
            target.addStickyTaint(sticky);
            target.setModifiedValue(modifiedValue);
        }
    }
}
