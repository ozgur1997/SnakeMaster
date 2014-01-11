/*
 * To cphange this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
   
         
         
package snakemaster;

import environment.ApplicationStarter;


/**
 *
 * @author acer
 */
public class SnakeMaster {
//prıvate ınt growthCounter = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ApplicationStarter.run("blall", new SnakeEnvironment());
    }

}
