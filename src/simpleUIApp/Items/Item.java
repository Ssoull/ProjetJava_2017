package simpleUIApp.Items;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract public class Item implements Serializable {

	protected final Point2D center;
	protected boolean movable;
	private final int width;

	public Item(double x, double y, int w, boolean movable) {
		center = new Point2D.Double(Math.floor(x), Math.floor(y));
		width = w;
		this.movable = movable;
	}

	public Point2D getLocation() {
		return center;
	}

	public int getWidth() {
		return width;
	}

	public abstract void action();

	public abstract void draw(Graphics2D arg0);

	public abstract void setObjective(Item o);

	public abstract boolean contains(Point2D p);
}
