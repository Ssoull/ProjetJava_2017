package simpleUIApp.Items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Represent the space ship class.
 */
public class SpaceShip extends Item {

    /**
     * Represent the different direction of a space ship.
     */
    private enum Direction { UP, DOWN, LEFT, RIGHT, NONE}

    /**
     * Represent the objective of a space ships.
     */
    private Item objective;

    /**
     * Boolean to check if the space ship is dodging on the X axis.
     */
    private boolean isDodgingX;

    /**
     * Boolean to check if the space ship is dodging on the Y axis.
     */
    private boolean isDodgingY;

    /**
     * Represent the attack of a space ship.
     */
    private int attack;

    /**
     * Represent the speed of a space ship.
     */
    private int speed;

    /**
     * Represent the origin of a space ship.
     */
    private Planet origin;

    /**
     * The constructor of space ship.
     * @param x Coordinates on the X axis.
     * @param y Coordinates on the Y axis.
     * @param w The width of the space ship.
     * @param attack The attack of the space ship.
     * @param speed The speed of the space ship.
     * @param origin The origin planet of the space ship.
     */
    public SpaceShip(double x, double y, int w, int attack, int speed, Planet origin){
        super(x,y, w);
        objective = this;
        isDodgingX = false;
        isDodgingY = false;

        this.attack = attack;
        this.speed = speed;
        this.origin = origin;
    }

    /**
     * Set the objective of the space ship.
     * @param item Represent the item to go.
     */
    @Override
    public void setObjective(Item item) {
        this.objective = item;
    }


    /**
     * The implemented method from {@link Item} class.
     * @param point2D Represent the point to check.
     * @return Return true if a space ship contains another item, false if not.
     */
    @Override
    public boolean contains(Point2D point2D) {
        return Math.pow(distanceBetween2Points(this.center, point2D), 2) <= (getWidth() / 2) * (getWidth() / 2);
    }

    /**
     * The implemented method from {@link Item} class.
     */
    @Override
    public void action() {
        for(int i = 0; i< speed; i++) {
            double newX = center.getX();
            double newY = center.getY();
            if (!objective.contains(this.center)) {
                Direction dirX = Direction.NONE;
                Direction dirY = Direction.NONE;


                if (!isDodgingX) {
                    if (newX > objective.getLocation().getX()) {
                        newX -= 1;
                        dirX = Direction.LEFT;
                    } else if (newX < objective.getLocation().getX()) {
                        newX += 1;
                        dirX = Direction.RIGHT;
                    }
                }
                if (!isDodgingY) {
                    if (newY > objective.getLocation().getY()) {
                        newY -= 1;
                        dirY = Direction.UP;
                    } else if (newY < objective.getLocation().getY()) {
                        newY += 1;
                        dirY = Direction.DOWN;
                    }
                }

                if (objective instanceof Planet) {

                    Planet planet = Planet.checkPlanetCollisions((Planet) objective, this.center);

                    if (planet != null) {

                        Point2D upper_left = new Point2D.Double(planet.center.getX() - (planet.getWidth() / 2), planet.center.getY() - (planet.getWidth() / 2));

                        boolean check = newX >= upper_left.getX();
                        double test = upper_left.getX() + planet.getWidth();
                        boolean check_2 = newX <= test;
                        boolean check_3 = newY >= upper_left.getY();
                        boolean check_4 = newY <= upper_left.getY() + planet.getWidth();
                        if (check && check_2 && check_3 && check_4 && !isDodgingX && !isDodgingY) {
                            if (dirX == Direction.RIGHT && dirY == Direction.NONE) {
                                isDodgingY = true;
                            }
                            if (dirX == Direction.RIGHT && dirY == Direction.UP) {
                                if (this.center.getX() <= upper_left.getX()) {
                                    newX = center.getX();
                                    newY = center.getY() - 1;
                                } else {
                                    newX = center.getX() + 1;
                                    newY = center.getY();
                                }
                            }
                            if (dirX == Direction.RIGHT && dirY == Direction.DOWN) {
                                if (this.center.getX() <= upper_left.getX()) {
                                    newX = center.getX();
                                    newY = center.getY() + 1;
                                } else {
                                    newX = center.getX() + 1;
                                    newY = center.getY();
                                }
                            }
                            if (dirX == Direction.LEFT && dirY == Direction.NONE) {
                                isDodgingY = true;
                            }
                            if (dirX == Direction.LEFT && dirY == Direction.UP) {
                                if (this.center.getX() >= upper_left.getX() + planet.getWidth()) {
                                    newX = center.getX();
                                    newY = center.getY() - 1;
                                } else {
                                    newX = center.getX() - 1;
                                    newY = center.getY();
                                }
                            }
                            if (dirX == Direction.LEFT && dirY == Direction.DOWN) {
                                if (this.center.getX() >= upper_left.getX() + planet.getWidth()) {
                                    newX = center.getX();
                                    newY = center.getY() + 1;
                                } else {
                                    newX = center.getX() - 1;
                                    newY = center.getY();
                                }
                            }

                        }
                        if (dirX == Direction.NONE) {
                            if (this.center.getY() < planet.center.getY()) {
                                newX = center.getX() - 1;
                            } else {
                                newX = center.getX() + 1;
                            }

                            newY = center.getY();

                            if (!isDodgingX) {
                                isDodgingX = true;
                            }
                        }

                        if (dirY == Direction.NONE) {
                            if (this.center.getX() < planet.center.getX()) {
                                newY = center.getY() - 1;
                            } else {
                                newY = center.getY() + 1;
                            }

                            newX = center.getX();
                        }

                        if (isDodgingX && (center.getX() <= upper_left.getX() || center.getX() > upper_left.getX() + planet.getWidth())) {
                            isDodgingX = false;
                        }

                        if (isDodgingY && (center.getY() <= upper_left.getY() || center.getY() > upper_left.getY() + planet.getWidth())) {
                            isDodgingY = false;
                        }
                    }
                }

                center.setLocation(newX, newY);
            } else {
                if (objective instanceof Planet) {
                    Planet.addSpaceShipToDelete(this, (Planet) objective);
                    objective = this;
                }
            }
        }
    }

    /**
     * The implemented method from {@link Item} class.
     * @param graphics2D Represent the object to draw.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        Point2D pos = this.center;
        int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
        if(origin.getType() == Planet.Type.PLAYER) {
            graphics2D.setColor(Color.BLUE);
        }
        else if (origin.getType() == Planet.Type.IA) {
            graphics2D.setColor(Color.BLACK);
        }
        graphics2D.fillRect(x - w / 2, y - w / 2, w, w);
    }

    /**
     * Getter for the attack value.
     * @return Return the attack value.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Getter for the origin planet of the space ship.
     * @return Return the origin value.
     */
    public Planet getOrigin() {
        return origin;
    }
}
