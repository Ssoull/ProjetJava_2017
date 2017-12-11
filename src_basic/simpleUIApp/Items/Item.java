package simpleUIApp.Items;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Any graphical element that will be handle by the application.
 */
abstract public class Item implements Serializable {

    /**
     * Represent the center of the item.
     */
	protected final Point2D center;

    /**
     * Represent the width of the item.
     */
	private final int width;


    /**
     * Constructor of item.
     * @param x Coordinates on the X axis.
     * @param y Coordinates on the Y axis.
     * @param w The width of the item.
     */
	public Item(double x, double y, int w) {
		center = new Point2D.Double(Math.floor(x), Math.floor(y));
		width = w;
	}

    /**
     * Calculation of the distance between 2 points.
     * @param p1 The first point.
     * @param p2 The second point.
     * @return Return the distance between the 2 points.
     */
    public double distanceBetween2Points(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    /**
     * Get the location of the item.
     * @return Return a {@link Point2D} object.
     */
	public Point2D getLocation() {
		return center;
	}

    /**
     * Get the width of the item.
     * @return Return an int.
     */
	public int getWidth() {
		return width;
	}

    /**
     * An abstract method to redefine.
     */
	public abstract void action();

    /**
     * An abstract method to redefine.
     * @param graphics2D Represent the object to draw.
     */
	public abstract void draw(Graphics2D graphics2D);


    /**
     * An abstract method to redefine.
     * @param item Represent the item to go.
     */
	public abstract void setObjective(Item item);

    /**
     * An abstract method to redefine.
     * @param point2D Represent the point to check.
     */
	public abstract boolean contains(Point2D point2D);
}
