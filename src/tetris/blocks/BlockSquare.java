package tetris.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public class BlockSquare 
    extends Block {

    public BlockSquare(GameField field, int startY, int startX) {
        super(field, startY, startX);
    }

    @Override
    public boolean canMove(Direction dir) {
        switch (dir) {
            case UP:    return false;
            case RIGHT: return field.isEmpty(y, x+2) && field.isEmpty(y+1, x+2);
            case DOWN:  return field.isEmpty(y+2, x) && field.isEmpty(y+2, x+1);
            case LEFT:  return field.isEmpty(y, x-1) && field.isEmpty(y+1, x-1);
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
        cons.add(new Point(0,1));
        cons.add(new Point(1,0));
        cons.add(new Point(1,1));
        return cons;
    }

    @Override
    public Color getColor() {
        return GameField.getColorFromNumber(GameField.YELLOW);
    }
    
    @Override
    public byte getColorCode() {
        return GameField.YELLOW;
    }
}
