package edu.gwu.raminfar.animation;

import java.awt.Shape;

/**
 * @author Amir Raminfar
 */
public class ShapeWrapper {
    private Shape shape;

    public ShapeWrapper(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    protected void setShape(Shape shape) {
        this.shape = shape;
    }
}
