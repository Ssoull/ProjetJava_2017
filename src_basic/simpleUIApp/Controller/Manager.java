package simpleUIApp.Controller;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import fr.ubordeaux.simpleUI.ItemManager;
import simpleUIApp.Items.Item;

/**
 * The manager class.
 */
public class Manager implements ItemManager<Item> {

	/**
	 * The implemented method of the class {@link ItemManager}.
	 * @param point1 The first point to check.
	 * @param item The item to check if the point is contained.
	 * @return Return true if the points is contained in the item, false if not.
	 */
	@Override
	public boolean contains(Point2D point1, Item item) {
		return item.contains(point1);
	}

	/**
	 * The implemented method of the class {@link ItemManager}.
	 * @param graphics2D The graphical container of the rendering of item.
	 * @param item The item to graphically render.
	 */
	@Override
	public void draw(Graphics2D graphics2D, Item item) {
		item.draw(graphics2D);
	}

	/**
	 * The implemented method of the class {@link ItemManager}.
	 * @param shape a {@link Shape} that may intersect or not with the item.
	 * @param item an item that may intersect or not with the selection.
	 * @return Return true if item intersects location, false if not.
	 */
	@Override
	public boolean intersects(Shape shape, Item item) {
		return shape.contains(item.getLocation());
	}

	/**
	 * The implemented method of the class {@link ItemManager}.
	 * @param shape a {@link Shape} that may contained or not the item.
	 * @param item an item that may be contained or not into the selection.
	 * @return Return true if item is contained into selection, false if not.
	 */
	@Override
	public boolean isContained(Shape shape, Item item) {
		Point2D pos = item.getLocation();
		int x = (int) pos.getX(), y = (int) pos.getY(), w = item.getWidth();
		return shape.contains(new Rectangle(x - w / 2, y - w / 2, w, w));
	}
}
