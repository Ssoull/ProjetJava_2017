package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Planet extends Item {
	
	private final int distanceMin = 50;
	private static ArrayList<Item> items;

	public Planet(double x, double y, int w) {
		super(x, y, w, false);
	}

	@Override
	public void move() {
		
	}

	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		arg0.setColor(Color.green);
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}

	@Override
	public void setObjective(Item o) {
		// TODO Auto-generated method stub
		
	}
	
	private static double squareDistance(Point2D p1, Point2D p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return dx * dx + dy * dy;
	}

	@Override
	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);
	}
	
	public static void setItems(ArrayList<Item> items) {
		Planet.items = items;
	}

	public boolean containsPlanet(Planet planet) {
		
		return squareDistance(planet.center, this.center) <= distanceMin - ((getWidth()/2) * (planet.getWidth()/2));
	}
}
