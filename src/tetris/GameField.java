package tetris;

import tetris.blocks.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {

    public final KeyManager keyManager;
    public GameWindow gameWindow;
    
    public final byte[][] field;
    
    public static final byte EMPTY = 0;
    public static final byte CYAN = 1;
    public static final byte BLUE = 2;
    public static final byte ORANGE = 3;
    public static final byte YELLOW = 4;
    public static final byte GREEN = 5;
    public static final byte PURPLE = 6;
    public static final byte RED = 7;
    
    public Block activeBlock = null;
    public final byte activeBlockColor = RED;
    
    public int blockCantMoveDownCount = 0;
    
    public static final int BLOCKS_UP_NEXT_SIZE = 4;
    public final Block[] blocksUpNext = new Block[BLOCKS_UP_NEXT_SIZE];
    
    private int score = 0;
    
    public GameField(int width, int height, KeyManager km) {
        if (width < 1)
            width = 8;
        if (height < 1)
            height = 8;
        
        field = new byte[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                field[y][x] = EMPTY;
        
        this.keyManager = km;
        
        //generate up next queue
        for (int i = 0; i < blocksUpNext.length; i++)
            blocksUpNext[i] = getRandomBlock();
        this.activeBlock = getRandomBlock();
    }
    
    public static Color getColorFromNumber(byte n) {
        switch (n) {
            case 0: return null;
            case 1: return Color.CYAN; 
            case 2: return Color.BLUE; 
            case 3: return Color.ORANGE; 
            case 4: return Color.YELLOW; 
            case 5: return Color.GREEN; 
            case 6: return new Color(181, 37, 248);
            case 7: return Color.RED; 
            default: return Color.PINK;
        }
    }
    
    /**
     * Determines if a tile is empty
     * Out of bounds is considered non-empty. Above the board (negative y) is considered empty.
     * @param y
     * @param x
     * @return 
     */
    public boolean isEmpty(int y, int x) {
        if (x < 0 || y >= this.field.length || x >= this.field[0].length)
            return false;
        if (y < 0)
            return true;
        
        return this.field[y][x] == EMPTY;
    }
    
    /**
     * Generates a random block
     * @return 
     */
    public Block getRandomBlock() {
        int startX = field[0].length / 2;
        int startY = -1;
        int n = new Random().nextInt(7);
        if (n == 0)
            return new BlockSingle(this,startY,startX);
        else if (n == 1)
            return new BlockSquare(this,startY,startX);
        else if (n == 2)
            return new BlockLong(this, startY, startX);
        else if (n == 3)
            return new BlockZLeft(this, startY, startX);
        else if (n == 4)
            return new BlockZRight(this, startY, startX);
        else if (n == 5)
            return new BlockCross(this, startY, startX);
        else if (n == 6)
            return new BlockLLeft(this, startY, startX);
        return null;
    }
    
    /**
     * Does one action
     * @param dir
     * @return Whether or not a move could be made
     */
    public boolean act(Direction dir) {
        if (this.keyManager == null) {
            System.out.println("game field has no access to the keys pressed!");
            return false;
        }
        
        //check for stopping block
        if ( !activeBlock.canMove(Direction.DOWN)) {
            blockCantMoveDownCount++;
            if (blockCantMoveDownCount >= 2) {
                activeBlockComplete();
                blockCantMoveDownCount = 0;
            }
        }
        
        //move active block
        if (activeBlock.canMove(dir)) {
            activeBlock.move(dir);
            blockCantMoveDownCount = 0;
        }
        if (dir == Direction.UP && activeBlock.canRotate())
            activeBlock.rotate();
        
        
        
        return true;
    }
    
    /**
     * When a block stops
     */
    public void activeBlockComplete() {
        //solidify active block
        for (Point p : activeBlock.getConstruct()) {
            this.field[activeBlock.y + p.y][activeBlock.x + p.x] = activeBlock.getColorCode();
        }
        
        //check for completed lines
        List<Integer> completedLines = new ArrayList<>();
        for (int y = field.length-1; y >= 0; y--) {
            boolean lineCompleted = true;
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x] == EMPTY) {
                    lineCompleted = false;
                    break;
                }
            }
            if (lineCompleted)
                completedLines.add(y);
        }
        
        //update score
        switch (completedLines.size()) {
            case 1: score += 40; break;
            case 2: score += 100; break;
            case 3: score += 300; break;
            case 4: score += 1200; break;
        }
        
        //update window score
        this.gameWindow.setScoreLabel(this.score);
        
        this.activeBlock = null;        //or else it will look weird when shifting everything down
        
        for (int i = 0; i < completedLines.size(); i++)         //shifting everything down by 1 each time increases the row count of every line above it!
            completedLines.set(i, completedLines.get(i) + i);
        
        //remove completed line from bottom to top, shifting blocks above down by 1 each time
        for (int line : completedLines) {
            
            for (int x = 0; x < field[0].length; x++)   //erase line (unecessary I think but just to be safe)
                field[line][x] = EMPTY;
            
            for (int y = line; y >= 1; y--) {           //shift everything down
                for (int x = 0; x < field[0].length; x++) {
                    field[y][x] = field[y-1][x];
                    field[y-1][x] = EMPTY;
                }
            }
            
            this.gameWindow.repaint();
            try {
                Thread.sleep(100);
            } 
            catch (Exception e) {}
        }
        
        
        
        //pull next block from queue
        this.activeBlock = blocksUpNext[0];
        
        //step blocks in queue down one
        for (int i = 1; i < blocksUpNext.length; i++)
            blocksUpNext[i-1] = blocksUpNext[i];
        
        //generate new block in end of queue
        blocksUpNext[blocksUpNext.length-1] = getRandomBlock();
    }   
}
