package snakemaster;

import audio.AudioPlayer;
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

    private GameState gameState = GameState.PAUSED;
    private Grid grid;
    private int score = 0;
    private Snake snake;
    private ArrayList<Point> apples;
    private ArrayList<Point> poison;

    private int speed = 0;
    private int moveCounter = speed;

    private Direction backgroundImageDirection = Direction.LEFT;
    private int MAX_LEFT = -200;
    private int MAX_RIGHT = -5;

    public SnakeEnvironment() {
        AudioPlayer.play("/Sound/kris2.wav");
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/leaves.jpg"));
        this.grid = new Grid();
        this.grid.setColor(new Color(222, 222, 222));
        this.grid.setColumns(50);
        this.grid.setCellHeight(15);
        this.grid.setRows(40);
        this.grid.setPosition(new Point(19, 90));
        this.apples = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            this.apples.add(getRandomPoint());
        }
        this.poison = new ArrayList<Point>();
        for (int i = 0; i < 5; i++) {
            this.poison.add(getRandomPoint());
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

        if (gameState == GameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    snake.move();
                    moveCounter = speed;
                    if (snake.selfHitTest()) {
                        //set the game state to ENDED
                        this.gameState =GameState.ENDED;
                    }
                    speed = -3;
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

            moveBackgroundImage();
        }
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
                this.apples.get(i).setLocation(getRandomPoint());
                //System.out.println("APPLE Chomp");

                //grow method
                snake.grow();
                this.setScore(this.getScore() + 5);
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //toggle the PAUSED/RUNNING state
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
            this.setScore(this.getScore() + 5);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake.getDirection() != Direction.LEFT) {
              snake.setDirection(Direction.RIGHT);      
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
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
                        graphics.setColor(new Color(199, 97, 20));
                    } else {
                        graphics.setColor(new Color(122, 197, 205));
                    }

                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }
        }

        //GraphicsPalette.enterPortal(graphics, new Point(50, 50), new Point(40, 40), Color.blue);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, 1020, 80);
        graphics.setColor(new Color(134, 39, 82));
        graphics.fillRect(0, 80, 1020, 9);

        int[] xs = {180, 160, 360, 380};
        int[] ys = {0, 80, 80, 0};
        //graphics.setColor(Color.MAGENTA);
        graphics.fillPolygon(xs, ys, 4);

        int[] xs1 = {500, 480, 680, 700};
        int[] ys1 = {0, 80, 80, 0};
        //graphics.setColor(Color.MAGENTA);
        graphics.fillPolygon(xs1, ys1, 4);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Calibri", Font.ITALIC, 40));
        graphics.drawString("Score:" + this.getScore(), 10, 30);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 15));
        graphics.drawString("Challenge Mode <DRACULA>", 200, 30);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 15));
        graphics.drawString("Time Left/9 days", 380, 70);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 16));
        graphics.drawString("", 500, 40);
        graphics.setFont(new Font("Calibri", Font.PLAIN, 15));
        graphics.drawString("OZGUR TARIM", 690, 30);

        if (gameState == GameState.PAUSED) {
            graphics.setFont(new Font("Calibri", Font.CENTER_BASELINE, 62));
            graphics.setColor(Color.ORANGE);
            graphics.drawString("PAUSED", 400, 300);

        }

        if (gameState == GameState.ENDED) {
            graphics.setFont(new Font("Calibri", Font.CENTER_BASELINE, 62));
            graphics.setColor(Color.ORANGE);
            graphics.drawString("GAME OVER", 400, 300);

        }

        
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

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}
