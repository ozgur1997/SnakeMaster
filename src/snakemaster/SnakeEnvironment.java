/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakemaster;

import environment.Environment;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author acer
 */
class SnakeEnvironment extends Environment {

    private Grid grid;
    private int score = 0;
    private Snake snake;
    
    private int speed = 5;
    private int moveCounter = speed;

    public SnakeEnvironment() {

    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/images2.jpg"));
        this.grid = new Grid();
        this.grid.setColor(Color.RED);
        this.grid.setColumns(40);
        this.grid.setCellHeight(15);
        this.grid.setRows(20);
        this.grid.setPosition(new Point(19, 19));
        this.snake = new Snake();
        this.snake.getBody().add(new Point(5, 5));
        this.snake.getBody().add(new Point(5, 4));
        this.snake.getBody().add(new Point(5, 3));
        this.snake.getBody().add(new Point(5, 3));
    }

    @Override
    public void timerTaskHandler() {
         System.out.println("Timer");
         if (snake  != null){
             if (moveCounter <= 0){
             snake.move();
             moveCounter = speed;
             } else {
                 moveCounter --;
             }
             
    }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.score += 50;
        } else if (e.getKeyCode()== KeyEvent.VK_M){
            snake.move();
        } else if (e.getKeyCode()== KeyEvent.VK_G){
            snake.setGrowthcounter(2);
            
        }

    } @Override
   
    public void keyReleasedHandler(KeyEvent e) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (this.grid != null) {
            this.grid.paintComponent(graphics);

            Point cellLocation;
            graphics.setColor(Color.red);
            if (snake != null) {
                for (int i = 0; i < snake.getBody().size(); i++)   {
                    if (i == 0) {          
                    graphics.setColor(Color.YELLOW);
                    } else {
                        graphics.setColor(Color.YELLOW);
                    }
                    
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight() );
                }
            }

        }
        graphics.setFont(new Font("Calibri", Font.ITALIC, 60));
        graphics.drawString("Score:" + this.score, 50, 50);
    }
    
   
        }



