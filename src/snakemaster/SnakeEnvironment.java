/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakemaster;

import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author acer
 */
class SnakeEnvironment extends Environment {

    private Grid grid;
    private int score = 5;
    private Snake snake;
    private ArrayList<Point> apples;

    private int speed = -400;
    private int moveCounter = speed;

    private Direction backgroundImageDirection = Direction.LEFT;
    private int MAX_LEFT = -200;
    private int MAX_RIGHT = -5;

    public SnakeEnvironment() {

    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/leaves.jpg"));
        this.grid = new Grid();
        this.grid.setColor(Color.GREEN);
        this.grid.setColumns(50);
        this.grid.setCellHeight(15);
        this.grid.setRows(40);
        this.grid.setPosition(new Point(19, 90));

        this.apples = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            this.apples.add(getRandomPoint());
        }

        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(5, 3));

    }

    @Override
    public void timerTaskHandler() {
//     System.out.println("Timer");
        if (snake != null) {
            if (moveCounter <= 0) {
                snake.move();
                moveCounter = speed;
                checkSnakeIntersection();
            } else {
                moveCounter--;
            }
        }

        if (snake.getDirection() == Direction.RIGHT) {
            if (snake.getHead().x >= this.grid.getColumns()) {
                snake.getHead().x = 0;
            }
        } else if (snake.getDirection() == Direction.LEFT) {
            if (snake.getHead().x <= -1) {
                snake.getHead().x = this.grid.getColumns() - 1;
            }
        } else if (snake.getDirection() == Direction.UP) {
            if (snake.getHead().y <= -1) {
                snake.getHead().y = this.grid.getRows() - 1;
            }
        } else if (snake.getDirection() == Direction.DOWN) {
            if (snake.getHead().y >= this.grid.getRows()) {
                snake.getHead().y = 0;
            }
        }

        //this.getBackgroundImagePosition().x -= 1;
        moveBackgroundImage();

    }

    private Point getRandomPoint() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));

    }

    private void checkSnakeIntersection() {
        //if the snake location is the same as an apple location
        //then grow the snake and remove the apple
        //later, move apple and make a sound and increase the score

        for (int i = 0; i < this.apples.size(); i++) {
            if (snake.getHead().equals(this.apples.get(i))) {
                System.out.println("APPLE Chomp");

                //grow method
                snake.grow();
                this.apples.get(i).setLocation(getRandomPoint());
                this.score += 5;
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 50;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.snake.setDirection(Direction.DOWN);
        }

    }

    @Override

    public void keyReleasedHandler(KeyEvent e) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            if (this.apples != null) {
                for (int i = 0; i < this.apples.size(); i++) {
                    this.apples.get(i);
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.apples.get(i)), new Point(this.grid.getCellSize()));

                }
            }

            Point cellLocation;
            graphics.setColor(Color.red);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++) {
                    if (i == 0) {
                        graphics.setColor(Color.BLACK);
                    } else {
                        graphics.setColor(Color.BLACK);
                    }

                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }

        }

        //GraphicsPalette.enterPortal(graphics, new Point(50, 50), new Point(40, 40), Color.blue);
         
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, 800, 80);
        graphics.setColor(Color.MAGENTA);
        graphics.fillRect(0, 80, this.getWidth(), 5);
        
        int[] xs = {300, 280, 480, 500};
        int[] ys = {0, 80, 80, 0};
        graphics.setColor(Color.MAGENTA);
        graphics.fillPolygon(xs, ys, 4);
        
        
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Calibri", Font.ITALIC, 20));
        graphics.drawString("Score:" + this.score, 10, 15);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 25));
        graphics.drawString("Challenge Mode <DRACULA>", 80, 50);
         graphics.setFont(new Font("Calibri", Font.PLAIN, 15));
        graphics.drawString("Time Left/9 days", 380, 70);
         graphics.setFont(new Font("Calibri", Font.PLAIN, 25));
        graphics.drawString("OZGURTARIM&SNAKEGAME", 500, 50);
         graphics.setFont(new Font("Calibri", Font.PLAIN, 23));
        graphics.drawString("!!COMPLETE 1000 APPLES GET "
                           + "DRACULA BEFORE LEFT!!", 800, 70);
    }

    private void moveBackgroundImage() {
        if (backgroundImageDirection == Direction.LEFT) {
            if (this.getBackgroundImagePosition().x <= MAX_LEFT) {
                backgroundImageDirection = Direction.RIGHT;
            } else {
                this.getBackgroundImagePosition().x -= 1;
            }
        }

        if (backgroundImageDirection == Direction.RIGHT) {
            if (this.getBackgroundImagePosition().x >= MAX_RIGHT) {
                backgroundImageDirection = Direction.LEFT;
            } else {
                this.getBackgroundImagePosition().x += 1;

            }
        }
    }

}
