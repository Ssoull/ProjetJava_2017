package simpleUIApp;

import com.sun.javafx.event.DirectEvent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class SpaceShip extends Item {

    private enum Direction { UP, DOWN, LEFT, RIGHT, NONE}

    private Item objective;
    private boolean isDodgingX;
    private boolean isDodgingY;

    public SpaceShip(double x, double y, int w) {
        super(x, y, w, true);
        objective = this;
        isDodgingX = false;
        isDodgingY = false;
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
            if (!isDodgingX ) {
                if (newX > objective.getLocation().getX()) {
                    newX--;
                    dirX = Direction.LEFT;
                } else if (newX < objective.getLocation().getX()) {
                    newX++;
                    dirX = Direction.RIGHT;
                }
            }
            if (!isDodgingY){
                if (newY > objective.getLocation().getY()) {
                    newY--;
                    dirY = Direction.UP;
                } else if (newY < objective.getLocation().getY()) {
                    newY++;
                    dirY = Direction.DOWN;
                }
            }

            //Planet planet = Planet.checkPlanetCollisions((Planet)objective, new Point2D.Double(newX,  newY));
            Planet planet = Planet.checkPlanetCollisions((Planet)objective, this.center);

            boolean verifplanet = objective instanceof Planet && (planet) != null;
            if (verifplanet) {

                double angleRads = Math.atan2(newX - planet.center.getX(), newY - planet.center.getY());

                Point2D UL = new Point2D.Double(planet.center.getX() - (planet.getWidth()/2), planet.center.getY() - (planet.getWidth()/2));

                boolean verif = newX >= UL.getX();
                double test = UL.getX() + planet.getWidth();
                boolean verif2 = newX <= test;
                boolean verif3 = newY >= UL.getY();
                boolean verif4 = newY <= UL.getY() + planet.getWidth();
                if(verif && verif2 && verif3 && verif4 && !isDodgingX && !isDodgingY) {
                    if(dirX == Direction.RIGHT && dirY == Direction.NONE){
                        isDodgingY = true;
                    }
                    if(dirX == Direction.RIGHT && dirY == Direction.UP){
                        if(this.center.getX() <= UL.getX()){
                            newX = center.getX();
                            newY = center.getY()-1;
                        } else {
                            newX = center.getX()+1;
                            newY = center.getY();
                        }
                    }
                    if(dirX == Direction.RIGHT && dirY == Direction.DOWN) {
                        if (this.center.getX() <= UL.getX()) {
                            newX = center.getX();
                            newY = center.getY() + 1;
                        } else {
                            newX = center.getX() + 1;
                            newY = center.getY();
                        }
                    }
                    if(dirX == Direction.LEFT && dirY == Direction.NONE) {
                        isDodgingY =true;
                    }
                    if(dirX == Direction.LEFT && dirY == Direction.UP){
                        if(this.center.getX() >= UL.getX()+planet.getWidth()){
                            newX = center.getX();
                            newY = center.getY()-1;
                        } else {
                            newX = center.getX()-1;
                            newY = center.getY();
                        }
                    }
                    if(dirX == Direction.LEFT && dirY == Direction.DOWN) {
                        if (this.center.getX() >= UL.getX() +planet.getWidth()) {
                            newX = center.getX();
                            newY = center.getY() + 1;
                        } else {
                            newX = center.getX() - 1;
                            newY = center.getY();
                        }
                    }

                }
                if(dirX == Direction.NONE ){
                    newX = center.getX() - 1;
                    newY = center.getY();
                    if(!isDodgingX)
                     isDodgingX = true;
                }

                if(dirY == Direction.NONE ){
                    newY = center.getY() - 1;
                    newX= center.getX();
                }

                if(isDodgingX && center.getX() <= UL.getX()) {
                    isDodgingX = false;
                }

                if(isDodgingY && center.getY() <= UL.getY()) {
                    isDodgingY = false;
                }


                //newX = this.center.getX();
                //newY = this.center.getY();

				/*newX = planet.center.getX() + (((planet.getWidth()) /2) * Math.sin(angleRads));
				if(newX - planet.center.getX() < 1)
					newX+=Math.sin(angleRads);
				newY = planet.center.getY() + (((planet.getWidth()) /2) * Math.cos(angleRads));*/



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
