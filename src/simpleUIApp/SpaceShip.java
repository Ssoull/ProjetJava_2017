package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class SpaceShip extends Item {

    private enum Direction { UP, DOWN, LEFT, RIGHT, NONE}

	private Item objective;

	public SpaceShip(double x, double y, int w) {
		super(x, y, w, true);
		objective = this;
	}

	public void setObjective(Item o) {
		this.objective = o;
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

	@Override
	public void action() {

		if (!objective.contains(this.center)) {

		    Direction dirX = Direction.NONE;
		    Direction dirY = Direction.NONE;

			double newX = center.getX();
			double newY = center.getY();
			if (newX > objective.getLocation().getX()) {
				newX--;
				dirX = Direction.LEFT;
			} else if (newX < objective.getLocation().getX()) {
				newX++;
                dirX = Direction.RIGHT;
			}
			if (newY > objective.getLocation().getY()) {
				newY--;
				dirY = Direction.UP;
			} else if (newY < objective.getLocation().getY()) {
				newY++;
				dirY = Direction.DOWN;
			}

			Planet planet = Planet.checkPlanetCollisions((Planet)objective, new Point2D.Double(this.center.getX() + newX, this.center.getY() + newY));
			if (objective instanceof Planet && planet != null) {

                //TODO Rediriger le vaisseau dans la direction adéquate (random ou trigo? (PI/4 puis changer de maniere bien))
            }

			center.setLocation(newX, newY);
		} else {
			objective = this;
		}


	}

	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		arg0.setColor(Color.blue);
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}
}
