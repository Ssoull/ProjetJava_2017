package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Planet extends Item {
	
	private final int DISTANCE_MIN = 100;
	private static ArrayList<Item> items;
	private int nbUnit;

	Planet(double x, double y, int w) {
		super(x, y, w, false);
        nbUnit = 0;
	}

	@Override
	public void action() {
		++nbUnit;
	}

	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		arg0.setColor(Color.green);
		arg0.fillOval(x - w / 2, y - w / 2, w, w);

        arg0.setColor(Color.black);
        String test = Integer.toString(nbUnit);
		arg0.drawString(test, x - ((float)test.length()/2 * 6.5f), y + 5);
	}

	@Override
	public void setObjective(Item o) {

		Point2D planetCoord = this.center;
		SpaceShip spaceShip = new SpaceShip(planetCoord.getX(), planetCoord.getY(), 10);
		spaceShip.setObjective(o);
		items.add(spaceShip);
	}

	private double distanceBetween2Points(Point2D p1, Point2D p2) {
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}

	@Override
	public boolean contains(Point2D p) {
		return distanceBetween2Points(this.center, p) <= getWidth()/2;
	}
	
	static void setItems(ArrayList<Item> items) {
		Planet.items = items;
	}

	boolean containsItem(Planet planet) {
		return distanceBetween2Points(planet.center, this.center) <= (getWidth()/2 + DISTANCE_MIN) + (planet.getWidth()/2);
	}

	static Planet checkPlanetCollisions(Planet objective, Point2D center) {

		boolean foundCollision = false;
		int countItems = 0;
        Planet planet = null;

		while (!foundCollision && countItems < items.size()) {

			Item item = items.get(countItems);
			if (item instanceof Planet) {
				planet = (Planet) item;

				if (planet != objective) {
					foundCollision = planet.contains(center);
				}
			}

			countItems++;
		}

		return planet;
	}
}
