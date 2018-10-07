package tetris;

import java.util.Timer;
import java.util.TimerTask;

public class Tetris {

    private final GameWindow gameWindow;
    private final KeyManager keyManager;
    private final GameField gameField;
    private final Timer repaintTimer;
    
    private static final int REPAINT_SLEEP_DURATION = 16;
    private static final int MAIN_LOOP_SLEEP = 50;
    private static final int DO_NO_ACTION_LOW = 2;    //eat inputs for a delay right after pressing and holding an arrow key
    private static final int DO_NO_ACTION_HIGH = 4;
    private static final int SLOW_DOWN_COUNT_CYCLES = 15;
    
    private int leftPressedCount = 0;
    private int upPressedCount = 0;
    private int rightPressedCount = 0;
    private int downPressedCount = 0;
    private int slowDownCount = 0;
    
    public Tetris() {
        this.keyManager = new KeyManager();
        this.gameField = new GameField(10,20, this.keyManager);
        this.gameWindow = new GameWindow(this.gameField, this.keyManager);
        this.gameField.gameWindow = this.gameWindow;
        this.repaintTimer = new Timer();
        this.repaintTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameWindow.repaint();
            }
            
        }, 0, REPAINT_SLEEP_DURATION);
        
        this.gameWindow.setVisible(true);
        mainLoop();
    }
    
    public void mainLoop() {
        
        while (true) {
            
            //System.out.println(keyManager);
            //update game state
            if (keyManager.leftPressed) {
                leftPressedCount++;
                if (leftPressedCount < DO_NO_ACTION_LOW || leftPressedCount > DO_NO_ACTION_HIGH) {
                    //do action
                    this.gameField.act(Direction.LEFT);
                }
            }
            else
                leftPressedCount = 0;
            
            if (keyManager.upPressed) {
                upPressedCount++;
                if (upPressedCount < DO_NO_ACTION_LOW || upPressedCount > DO_NO_ACTION_HIGH) {
                    //do action
                    this.gameField.act(Direction.UP);
                }
            }
            else
                upPressedCount = 0;
            
            if (keyManager.rightPressed) {
                rightPressedCount++;
                if (rightPressedCount < DO_NO_ACTION_LOW || rightPressedCount > DO_NO_ACTION_HIGH) {
                    //do action
                    this.gameField.act(Direction.RIGHT);
                }
            }
            else
                rightPressedCount = 0;
            
            if (keyManager.downPressed) {
                downPressedCount++;
                //do action regardless of how long down is pressed
                this.gameField.act(Direction.DOWN);
                
            }
            else
                downPressedCount = 0;
            
            //slow moving down
            if ( !keyManager.downPressed) {
                slowDownCount++;
                if (slowDownCount >= SLOW_DOWN_COUNT_CYCLES) {
                    this.gameField.act(Direction.DOWN);
                    slowDownCount = 0;
                }
            }
            
            
            
            //sleep
            try {
                Thread.sleep(MAIN_LOOP_SLEEP);
            } 
            catch (Exception e) {}
        }
    }
    
    public static void main(String[] args) {
        new Tetris();
    }

}
/*
TO IMPLEMENT:

* mutli block blocks

* up next in window look nicer

* Where the block is going to land shadow

* for each block add starting orientation
    * long block starts horizontally

* leaderboards

* knowing when the game ends

*/