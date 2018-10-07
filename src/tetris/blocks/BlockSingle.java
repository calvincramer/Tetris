package tetris.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public class BlockSingle 
    extends Block {

    public BlockSingle(GameField field, int startY, int startX) {
        super(field, startY, startX);
    }

    @Override
    public boolean canMove(Direction dir) {
        switch (dir) {
            case UP:    return false;
            case RIGHT: return this.field.isEmpty(y, x+1);
            case DOWN:  return this.field.isEmpty(y+1, x);
            case LEFT:  return this.field.isEmpty(y, x-1);
            default:    return false;
        }
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    @Override
    public void rotate() {
        //nothing
    }

    @Override
    public List<Point> getConstruct() {
        List<Point> cons = new ArrayList<>();
        cons.add(new Point(0,0));
        return cons;
    }

    @Override
    public Color getColor() {
        return GameField.getColorFromNumber((byte)-1);
    }
    
    @Override
    public byte getColorCode() {
        return (byte) -1;
    }

}
