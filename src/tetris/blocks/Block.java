package tetris.blocks;

import java.awt.Color;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public abstract class Block {
    
    public int x;
    public int y;
    public final GameField field;
    
    public Block(GameField field, int startY, int startX) {
        this.field = field;
        this.y = startY;
        this.x = startX;
       
        /*
        if (this.x < 0)
            this.x = 0;
        if (this.y < 0)
            this.y = 0;
        if (this.x >= this.field.field[0].length)
            this.x = 0;
        if (this.y >= this.field.field.length)
            this.y = 0;
        */
        
    }
    
    public void move(Direction dir) {
        switch (dir) {
            case UP:    /*nothing*/  break;     //even though canMove(up) should return false
            case RIGHT: this.x += 1; break;
            case DOWN:  this.y += 1; break;
            case LEFT:  this.x -= 1; break;
        }
    }

    abstract public byte getColorCode();
    abstract public Color getColor();
    abstract public boolean canMove(Direction dir);
    abstract public boolean canRotate(); //only rotates clockwise
    abstract public void rotate();
    abstract public List<Point> getConstruct();      //all points contained within this block, centered around (0,0)
    
}
