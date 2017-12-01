package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Planet extends Item {
	
	private final int DISTANCE_MIN_BETWEEN_PLANETS = 100;
	private final int DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP = 15;
	private static ArrayList<Item> items;
	private int nbUnit;
	private int maxLaunchingUnits = 10;

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
		arg0.fillRect(x - w / 2, y - w / 2, w, w);

        arg0.setColor(Color.black);
        String test = Integer.toString(nbUnit);
		arg0.drawString(test, x - ((float)test.length()/2 * 6.5f), y + 5);
	}

	@Override
	public void setObjective(Item o) {

		int nbLaunchingUnits;

		nbLaunchingUnits = maxLaunchingUnits >= nbUnit ? nbUnit : maxLaunchingUnits;

		Point2D planetCoord = this.center;
		SpaceShip spaceShip;
		double ray = this.getWidth()/2;
		double angle = ((360.f * Math.PI) / 180.f)/nbLaunchingUnits;
		double sumAngle = angle;
		for (int count = 0; count < nbLaunchingUnits; count++) {

			spaceShip = new SpaceShip(planetCoord.getX() + ((ray + DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP) * Math.cos(sumAngle)),
									  planetCoord.getY() + ((ray + DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP) * Math.sin(sumAngle)), 10);
			spaceShip.setObjective(o);
			items.add(spaceShip);

			sumAngle += angle;
			nbUnit--;
		}
	}

	private double distanceBetween2Points(Point2D p1, Point2D p2) {
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}

	@Override
	public boolean contains(Point2D p) {
		//return distanceBetween2Points(this.center, p) <= getWidth()/2;  //CIRCULAR PLANET
		Point2D upper_left = new Point2D.Double(this.center.getX() - (getWidth()/2), this.center.getY() - (getWidth()/2));
		return p.getX() >= upper_left.getX() && p.getX() <= upper_left.getX() + getWidth() && p.getY() >= upper_left.getY() && p.getY() <= upper_left.getY() + getWidth();
	}
	
	static void setItems(ArrayList<Item> items) {
		Planet.items = items;
	}

	boolean containsItem(Planet planet) {
		return distanceBetween2Points(planet.center, this.center) <= (getWidth()/2 + DISTANCE_MIN_BETWEEN_PLANETS) + (planet.getWidth()/2);
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

		if (!foundCollision) {
			return null;
		}

		return planet;
	}
}
