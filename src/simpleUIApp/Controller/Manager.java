package simpleUIApp.Controller;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import fr.ubordeaux.simpleUI.ItemManager;
import simpleUIApp.Items.Item;

public class Manager implements ItemManager<Item> {

	@Override
	public boolean contains(Point2D arg0, Item arg1) {
		return arg1.contains(arg0);
	}

	@Override
	public void draw(Graphics2D arg0, Item arg1) {
		arg1.draw(arg0);
	}

	@Override
	public boolean intersects(Shape arg0, Item arg1) {
		return arg0.contains(arg1.getLocation());
	}

	@Override
	public boolean isContained(Shape arg0, Item arg1) {
		Point2D pos = arg1.getLocation();
		int x = (int) pos.getX(), y = (int) pos.getY(), w = arg1.getWidth();
		return arg0.contains(new Rectangle(x - w / 2, y - w / 2, w, w));
	}

}
