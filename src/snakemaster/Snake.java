/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakemaster;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author acer
 */
public class Snake {

    public void move() {
        //create a new location for the head, using the direction
        int x = 0;
        int y = 0;

        switch (getDirection()) {
            case UP:
                x = 0;
                y = -1;
                break;

            case DOWN:
                x = 0;
                y = 1;
                break;

            case RIGHT:
                x = 1;
                y = 0;
                break;

            case LEFT:
                x = -1;
                y = 0;
                break;
        }
        //ınsert new head
        getBody().add(0, new Point(getHead().x + x, getHead().y + y));
        //delete the taıl
        if (getGrowthcounter() > 0) {
            setGrowthcounter(getGrowthcounter() - 1);
        } else {
            getBody().remove(getBody().size() - 1);
        }

    }

    public Point getTail() {
        return body.get(body.size() - 1);
    }

    public boolean selfHitTest() {
        for (int i = 1; i < body.size(); i++) {
            if (getHead().equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean intersects(Point location) {
        for (Point bodyLocation : body) {
            if (bodyLocation.equals(location)) {
                return true;
            }
        }
        return false;
    }

    public Point getHead() {
        return getBody().get(0);
    }

    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthcounter;

    {
        setBody(new ArrayList<Point>());
    }

    /* @return 
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the growthcounter
     */
    public int getGrowthCounter() {
        return getGrowthcounter();
    }

    /**
     * @param growthcounter the growthcounter to set
     */
    public void setGrowthcounter(int growthcounter) {
        this.growthcounter = growthcounter;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the growthcounter
     */
    public int getGrowthcounter() {
        return growthcounter;
    }

    void grow() {
        growthcounter += 2;
    }

}
