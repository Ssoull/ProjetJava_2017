package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

enum Type {PLAYER, NEUTRAL, IA}

public class Planet extends Item {

    private final int DISTANCE_MIN_BETWEEN_PLANETS = 100;
    private final int DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP = 15;
    private static ArrayList<Item> items;

    private static ArrayList<SpaceShip> spaceShipsToDelete;
    private int nbUnit;

    private int percentageToSend;

    private int speedSpaceShips;
    private int timerProductionSpaceShips;
    private int attackSpaceShips;

    private Type type;

    Planet(double x, double y, int w) {
        super(x, y, w, false);
        nbUnit = 0;
        percentageToSend = 50;
        spaceShipsToDelete = new ArrayList<>();

        Random random = new Random();
        speedSpaceShips = 5;//random.nextInt(3 - 1) + 1;
        timerProductionSpaceShips = random.nextInt(2000 - 500) + 500;
        attackSpaceShips = random.nextInt(3 - 1) + 1;
    }

    Planet (double x, double y, int w, Type type){
        super(x,y,w, false);
        this.type = type;
        nbUnit = 0;
        percentageToSend = 50;
        spaceShipsToDelete = new ArrayList<>();

        Random random = new Random();
        speedSpaceShips = random.nextInt(3 - 1) + 1;
        timerProductionSpaceShips = random.nextInt(2000 - 500) + 500;
        attackSpaceShips = random.nextInt(3 - 1) + 1;

    }

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
                        if(((Planet) item).getType() != Type.IA){
                            found = true;
                            setObjective(item);
                        }
                    }
                    it++;
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D arg0) {
        Point2D pos = this.center;
        int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
        arg0.setColor(Color.red);
        switch (this.type){
            case PLAYER:
                arg0.setColor(Color.GREEN);
                break;
            case NEUTRAL:
                arg0.setColor(Color.LIGHT_GRAY);
                break;
            case IA:
                arg0.setColor(Color.RED);
                break;
            default:
                break;
        }
        arg0.fillRect(x - w / 2, y - w / 2, w, w);

        arg0.setColor(Color.black);
        String nbUnitDisplay = "v:" + Integer.toString(nbUnit);
        String percentageDisplay = Integer.toString(percentageToSend) + "%";
        arg0.drawString(nbUnitDisplay, x - ((float)nbUnitDisplay.length()/2 * 6.5f), y);
        arg0.drawString(percentageDisplay, x - ((float)percentageDisplay.length()/2 * 6.5f), y + 15);
    }

    @Override
    public void setObjective(Item o) {

        int nbLaunchingUnits;

        nbLaunchingUnits = Math.round((float)(nbUnit * (percentageToSend/100.0)));
        Point2D planetCoord = this.center;
        SpaceShip spaceShip;
        double ray = this.getWidth()/2;
        double angle = ((360.f * Math.PI) / 180.f)/nbLaunchingUnits;
        double sumAngle = angle;
        for (int count = 0; count < nbLaunchingUnits; count++) {

            spaceShip = new SpaceShip(planetCoord.getX() + ((ray + DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP) * Math.cos(sumAngle)),
                    planetCoord.getY() + ((ray + DISTANCE_MIN_SPAWN_AND_COLLISION_SPACE_SHIP) * Math.sin(sumAngle)), 10, attackSpaceShips, speedSpaceShips,this);
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

    public void incrementPercentageToSend(int percentageToSend) {

        if(this.percentageToSend + percentageToSend <= 100 &&
                this.percentageToSend + percentageToSend >= 0) {
            this.percentageToSend += percentageToSend;
        }
    }

    public static void addSpaceShipToDelete(SpaceShip ship, Planet planet) {
        if(ship != null) {
            spaceShipsToDelete.add(ship);
            if(planet.nbUnit - ship.getAttack() >= 0 && planet.getType() != ship.getOrigin().getType())
                planet.nbUnit -= ship.getAttack();
            else {
                planet.nbUnit+=1;
                planet.setType(ship.getOrigin().getType());
            }

        }
    }

    public static void deleteSpaceShipList() {

        Iterator<SpaceShip> it = spaceShipsToDelete.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (items.contains(item)) {
                items.remove(item);
            }
        }
    }

    public int getTimerProductionSpaceShips() {
        return timerProductionSpaceShips;
    }

    static void savedPlanetAndSpaceShips() {

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
