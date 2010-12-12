package edu.gwu.raminfar.animation;

/**
 * @author Amir Raminfar
 */
public interface Easing {
    /**
     * @param t time
     * @param d duration
     * @param c range
     * @param b offset
     * @return
     */
    double ease(double t, double b, double c, double d);

    public final static Easing Linear = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            t /= d;
            return b + c * (t);
        }
    };

    // Cubic
    public final static Easing OutCubic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0.16666666666666563 * tc * ts + -0.5555555555555571 * ts * ts + 1.6666666666666696 * tc + -3.333333333333334 * ts + 3.055555555555556 * t);
        }
    };

    public final static Easing InOutCubic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 0 * ts * ts + -2 * tc + 3 * ts + 0 * t);
        }
    };

    public final static Easing BackInCubic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 0 * ts * ts + 4 * tc + -3 * ts + 0 * t);
        }
    };

    public final static Easing BackOutCubic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 0 * ts * ts + 4 * tc + -9 * ts + 6 * t);
        }
    };


    // Quintic
    public final static Easing OutQuintic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (1 * tc * ts + -5 * ts * ts + 10 * tc + -10 * ts + 5 * t);
        }
    };

    public final static Easing InOutQuintic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (6 * tc * ts + -15 * ts * ts + 10 * tc + 0 * ts + 0 * t);
        }
    };

    // Quartic
    public final static Easing OutQuartic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + -1 * ts * ts + 4 * tc + -6 * ts + 4 * t);
        }
    };

    public final static Easing InQuartic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 1 * ts * ts + 0 * tc + 0 * ts + 0 * t);
        }
    };

    public final static Easing BackInQuartic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 2 * ts * ts + 2 * tc + -3 * ts + 0 * t);
        }
    };

    public final static Easing BackOutQuartic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + -2 * ts * ts + 10 * tc + -15 * ts + 8 * t);
        }
    };

    public final static Easing InOutQuartic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (0 * tc * ts + 0 * ts * ts + 6 * tc + -9 * ts + 4 * t);
        }
    };


    // Elastic
    public final static Easing OutElastic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (33 * tc * ts + -106 * ts * ts + 126 * tc + -67 * ts + 15 * t);
        }
    };

    public final static Easing InElastic = new Easing() {
        @Override
        public double ease(double t, double b, double c, double d) {
            double ts = (t /= d) * t;
            double tc = ts * t;
            return b + c * (33 * tc * ts + -59 * ts * ts + 32 * tc + -5 * ts + 0 * t);
        }
    };

}
