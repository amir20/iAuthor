package edu.gwu.raminfar.animation;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Amir Raminfar
 */
public class Animator {
    private final JComponent component;
    private final List<Animation> queue = new ArrayList<Animation>();

    private Timer timer = new Timer(20, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Iterator<Animation> iterator = queue.iterator(); iterator.hasNext();) {
                Animation animation = iterator.next();
                if (animation.isEnded()) {
                    iterator.remove();
                } else {
                    transform(animation);
                }
            }

            if (queue.isEmpty()) {
                timer.stop();
            }

            component.repaint();
        }
    });

    public Animator(JComponent component) {
        this.component = component;
    }

    protected void transform(Animation a) {
        long time = System.currentTimeMillis() - a.start;
        if (a.movingRange != null) {
            double tx = a.easing.ease(time, a.movingRange.from.getX(), a.movingRange.to.getX() - a.movingRange.from.getX(), a.duration);
            double ty = a.easing.ease(time, a.movingRange.from.getY(), a.movingRange.to.getY() - a.movingRange.from.getY(), a.duration);
            AffineTransform transform = new AffineTransform();
            transform.setToTranslation(tx - a.wrapper.getShape().getBounds().getLocation().getX(), ty - a.wrapper.getShape().getBounds().getLocation().getY());
            a.wrapper.setShape(transform.createTransformedShape(a.wrapper.getShape()));
        }

        if (a.rotatingRange != null) {
            double theta = a.easing.ease(time, a.rotatingRange.from, a.rotatingRange.to - a.rotatingRange.from, a.duration);
            AffineTransform transform = new AffineTransform();
            transform.setToRotation(theta);
            a.wrapper.setShape(transform.createTransformedShape(a.wrapper.getShape()));
        }
    }

    protected void addToQueue(Animation animation) {
        if (!timer.isRunning()) {
            timer.start();
        }
        queue.add(animation);
    }

    public Animation newAnimation(ShapeWrapper wrapper) {
        return this.new Animation(wrapper);
    }


    public class Animation {
        protected final ShapeWrapper wrapper;

        // defaults
        protected long start;
        protected long duration = 1000L;
        protected Easing easing = Easing.OutQuintic;


        protected Range<Point> movingRange;
        protected Range<Double> rotatingRange;
        protected Range<Double> scaleRange;

        public Animation(ShapeWrapper wrapper) {
            this.wrapper = wrapper;
        }

        public Animation setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        public Animation setEasing(Easing easing) {
            this.easing = easing;
            return this;
        }

        public Animation rotate(double from, double to) {
            rotatingRange = new Range<Double>(from, to);
            return this;
        }

        public Animation move(Point from, Point to) {
            movingRange = new Range<Point>(from, to);
            return this;
        }

        public Animation moveTo(Point to) {
            return move(wrapper.getShape().getBounds().getLocation(), to);
        }

        public Animation scale(double from, double to) {
            scaleRange = new Range<Double>(from, to);
            return this;
        }

        public boolean isEnded() {
            return start + duration < System.currentTimeMillis();
        }

        public void animate() {
            start = System.currentTimeMillis();
            addToQueue(this);
        }
    }

    private class Range<E> {
        E from;
        E to;

        private Range(E from, E to) {
            this.from = from;
            this.to = to;
        }
    }
}
