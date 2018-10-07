package tetris.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public class BlockZLeft 
    extends Block {

    boolean isHorizontal = true;
    
    public BlockZLeft(GameField field, int startY, int startX) {
        super(field, startY, startX);
    }

    @Override
    public byte getColorCode() {
        return GameField.GREEN;
    }

    @Override
    public Color getColor() {
        return GameField.getColorFromNumber(GameField.GREEN);
    }

    @Override
    public boolean canMove(Direction dir) {
        if (this.isHorizontal) {
            switch (dir) {
                case UP:    return false;
                case RIGHT: return field.isEmpty(y, x+1) && field.isEmpty(y-1, x+2);
                case DOWN:  return field.isEmpty(y+1, x-1) && field.isEmpty(y+1, x) && field.isEmpty(y, x+1);
                case LEFT:  return field.isEmpty(y-1, x-1) && field.isEmpty(y, x-2);
                default:    return false;
            }
        }
        else {
            switch (dir) {
                case UP:    return false;
                case RIGHT: return field.isEmpty(y-1, x+1) && field.isEmpty(y, x+2) && field.isEmpty(y+1, x+2);
                case DOWN:  return field.isEmpty(y+1, x) && field.isEmpty(y+2, x+1);
                case LEFT:  return field.isEmpty(y-1, x-1) && field.isEmpty(y, x-1) && field.isEmpty(y+1, x);
                default:    return false;
            }
        }
    }

    @Override
    public boolean canRotate() {
        if (this.isHorizontal) {
            return field.isEmpty(y, x+1) && field.isEmpty(y+1, x+1);
        }
        else {
            return field.isEmpty(y, x-1) && field.isEmpty(y-1, x+1);
        }
    }

    @Override
    public void rotate() {
        this.isHorizontal = !this.isHorizontal;
    }

    @Override
    public List<Point> getConstruct() {
        List<Point> cons = new ArrayList<>();
        if (isHorizontal) {
            cons.add(new Point(0,-1));
            cons.add(new Point(0,0));
            cons.add(new Point(-1,0));
            cons.add(new Point(-1,1));
        }
        else {
            cons.add(new Point(-1,0));
            cons.add(new Point(0,0));
            cons.add(new Point(0,1));
            cons.add(new Point(1,1));
        }
        return cons;
    }

}
