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

    private ArrayList<Point> body;
    private Direction direction = Direction.RIGHT;
    private int growthcounter;

    {
        body = new ArrayList<>();
    }

    public void move() {
        //create a new location for the head, using the direction
        int x = 0;
        int y = 0;

        switch (direction) {
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
        body.add(0, new Point(getHead().x + x, getHead().y + y));
        //delete the taıl
        if (growthcounter > 0) {
            growthcounter--;
        } else {
            body.remove(body.size() - 1);
        }

    }

    public Point getHead() {
        return body.get(0);
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
        return growthcounter;
    }

    /**
     * @param growthcounter the growthcounter to set
     */
    public void setGrowthcounter(int growthcounter) {
        this.growthcounter = growthcounter;
    }

}
