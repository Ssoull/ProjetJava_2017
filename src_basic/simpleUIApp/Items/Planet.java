package simpleUIApp.Items;

import java.awt.*;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.Random;
import java.util.*;

/**
 * Represent the planet class.
 */
public class Planet extends Item {

    /**
     * Represent the different type of a planet.
     */
    public enum Type {PLAYER, NEUTRAL, IA}

    /**
     * Represent the minimal distance between planets.
     */
    private final int DISTANCE_MIN_BETWEEN_PLANETS = 100;

    /**
     * Represent the minimal distance where the space ships spawn.
     */
    private final int DISTANCE_MIN_SPAWN_SPACE_SHIPS = 15;

    /**
     * Represent a reference on the item list.
     */
    private static ArrayList<Item> items;

    /**
     * Represent the arrayList to add space ships which need to be destroyed.
     */
    private static ArrayList<SpaceShip> spaceShipsToDelete;

    /**
     * Represent the number of units.
     */
    private int nbUnit;

    /**
     * Represent the percentage of space ships to send.
     */
    private int percentageToSend;

    /**
     * Represent the speed of space ships.
     */
    private int speedSpaceShips;

    /**
     * Represent the delay to product 1 space ship.
     */
    private int timerProductionSpaceShips;

    /**
     * Represent the attack of space ships.
     */
    private int attackSpaceShips;

    /**
     * Represent the type of the planet.
     */
    private Type type;

    /**
     * The constructor of the planet.
     * @param x Coordinates on the X axis.
     * @param y Coordinates on the Y axis.
     * @param w The width of the planet.
     * @param type The type of the planet.
     */
    public Planet (double x, double y, int w, Type type){
        super(x,y,w);
        this.type = type;
        nbUnit = 0;
        percentageToSend = 50;
        spaceShipsToDelete = new ArrayList<>();

        Random random = new Random();
        speedSpaceShips = random.nextInt(3 - 1) + 1;
        timerProductionSpaceShips = random.nextInt(2000 - 500) + 500;
        attackSpaceShips = random.nextInt(3 - 1) + 1;
    }

    /**
     * The implemented method from {@link Item} class.
     */
    @Override
    public void action() {
        if(this.type == Type.PLAYER || this.type == Type.IA)
            nbUnit++;
        if(this.type == Type.IA){
            if(this.nbUnit >= 10){
                boolean found = false;
                int it = 0;

                while(!found && it < items.size()) {
                    ArrayList<Item> items_copy = new ArrayList<>(items);
                    Collections.shuffle(items_copy);
                    Item item = items_copy.get(it);
                    if(item instanceof Planet){
                        if(((Planet) item).type != Type.IA){
                            found = true;
                            setObjective(item);
                        }
                    }
                    it++;
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
        graphics2D.setColor(Color.red);
        switch (this.type){
            case PLAYER:
                graphics2D.setColor(Color.GREEN);
                break;
            case NEUTRAL:
                graphics2D.setColor(Color.LIGHT_GRAY);
                break;
            case IA:
                graphics2D.setColor(Color.RED);
                break;
            default:
                break;
        }
        graphics2D.fillRect(x - w / 2, y - w / 2, w, w);

        graphics2D.setColor(Color.black);
        String nbUnitDisplay = "v:" + Integer.toString(nbUnit);
        String percentageDisplay = Integer.toString(percentageToSend) + "%";
        graphics2D.drawString(nbUnitDisplay, x - ((float)nbUnitDisplay.length()/2 * 6.5f), y);
        graphics2D.drawString(percentageDisplay, x - ((float)percentageDisplay.length()/2 * 6.5f), y + 15);
    }


    /**
     * The implemented method from {@link Item} class.
     * @param item Represent the item to go.
     */
    @Override
    public void setObjective(Item item) {

        int nbLaunchingUnits;

        nbLaunchingUnits = Math.round((float) (nbUnit * (percentageToSend / 100.0)));
        Point2D planetCoord = this.center;
        SpaceShip spaceShip;
        double ray = this.getWidth() / 2;
        double angle = ((360.f * Math.PI) / 180.f) / nbLaunchingUnits;
        double sumAngle = angle;
        for (int count = 0; count < nbLaunchingUnits; count++) {

            spaceShip = new SpaceShip(planetCoord.getX() + ((ray + DISTANCE_MIN_SPAWN_SPACE_SHIPS) * Math.cos(sumAngle)),
                    planetCoord.getY() + ((ray + DISTANCE_MIN_SPAWN_SPACE_SHIPS) * Math.sin(sumAngle)), 10, attackSpaceShips, speedSpaceShips, this);
            spaceShip.setObjective(item);
            items.add(spaceShip);

            sumAngle += angle;
            nbUnit--;
        }
    }

    /**
     * The implemented method from {@link Item} class.
     * @param point2D Represent the point to check.
     * @return Return true if the planet contains another item, false if not.
     */
    @Override
    public boolean contains(Point2D point2D) {
        Point2D upper_left = new Point2D.Double(this.center.getX() - (getWidth()/2), this.center.getY() - (getWidth()/2));
        return point2D.getX() >= upper_left.getX() && point2D.getX() <= upper_left.getX() + getWidth() && point2D.getY() >= upper_left.getY() && point2D.getY() <= upper_left.getY() + getWidth();
    }

    /**
     * Setup the reference of the arrayList of items.
     */
    public static void setItems(ArrayList<Item> items) {
        Planet.items = items;
    }

    /**
     * Check if the planet that call this method contains the planet in parameter.
     * @param planet The planet to check.
     * @return Return true if the planet contains the planet in parameter, false if not.
     */
    public boolean containsPlanet(Planet planet) {
        return distanceBetween2Points(planet.center, this.center) <= (getWidth()/2 + DISTANCE_MIN_BETWEEN_PLANETS) + (planet.getWidth()/2);
    }

    /**
     * Check if there is a collision between space ships and the other planets.
     * @param objective Represent the objective of the space ship.
     * @param center Represent the center of the space ship.
     * @return Return the planet with which the space ships is in collision, null if there is no collision detected.
     */
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

    /**
     * Method used to increment the percentage of space ships to send.
     * @param percentageToAdd Represent the percentage to add to the current percentage.
     */
    public void incrementPercentageToSend(int percentageToAdd) {

        if(this.percentageToSend + percentageToAdd <= 100 &&
                this.percentageToSend + percentageToAdd >= 0) {
            this.percentageToSend += percentageToAdd;
        }
    }

    /**
     * Method used to add a space ship to the list and work with.
     * @param ship The ship to destroy.
     * @param planet The planet to decrease its life.
     */
    static void addSpaceShipToDelete(SpaceShip ship, Planet planet) {
        if(ship != null) {
            spaceShipsToDelete.add(ship);
            if(planet.nbUnit - ship.getAttack() >= 0 && planet.type != ship.getOrigin().type)
                planet.nbUnit -= ship.getAttack();
            else {
                planet.nbUnit+=1;
                planet.type = ship.getOrigin().type;
            }

        }
    }

    /**
     * Method used to delete the space ships from the items list.
     */
    public static void deleteSpaceShipList() {

        items.removeAll(spaceShipsToDelete);
        spaceShipsToDelete.clear();
    }

    /**
     * Getter for the type attribute.
     * @return Return the type of the planet.
     */
    public Type getType() {
        return type;
    }

    /**
     * Getter for the items list.
     * @return Return the items list.
     */
    public static ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Return the delay of production for the space ships.
     * @return Return the delay.
     */
    public int getTimerProductionSpaceShips() {
        return timerProductionSpaceShips;
    }
}
