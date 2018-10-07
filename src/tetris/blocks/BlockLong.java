package tetris.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public class BlockLong 
    extends Block {
    
    boolean isUpAndDown = false;

    public BlockLong(GameField field, int startY, int startX) {
        super(field, startY, startX);
    }

    @Override
    public boolean canMove(Direction dir) {
        if (isUpAndDown) {
            switch (dir) {
                case UP:    return false;
                case RIGHT: return field.isEmpty(y - 1, x + 1) 
                        && field.isEmpty(y, x + 1)
                        && field.isEmpty(y + 1, x + 1)
                        && field.isEmpty(y + 2, x + 1);
                case DOWN: return field.isEmpty(y + 3, x);
                case LEFT: return field.isEmpty(y - 1, x - 1) 
                        && field.isEmpty(y, x - 1)
                        && field.isEmpty(y + 1, x - 1)
                        && field.isEmpty(y + 2, x - 1);
                default:    return false;
            }
        }
        else {
            switch (dir) {
                case UP:    return false;
                case RIGHT: return field.isEmpty(y, x+3);
                case DOWN:  return field.isEmpty(y+1, x-1)
                        && field.isEmpty(y+1, x)
                        && field.isEmpty(y+1, x+1)
                        && field.isEmpty(y+1, x+2);
                case LEFT:  return field.isEmpty(y, x-2);
                default:    return false;
            }
        }
        
    }

    @Override
    public boolean canRotate() {
        if (this.isUpAndDown) {
            return field.isEmpty(y, x-1) && field.isEmpty(y, x+1) && field.isEmpty(y, x+2);
        } else {
            return field.isEmpty(y-1, x) && field.isEmpty(y+1, x) && field.isEmpty(y+2, x);
        }
    }

    @Override
    public void rotate() {
        this.isUpAndDown = !this.isUpAndDown;
    }

    @Override
    public List<Point> getConstruct() {
        List<Point> cons = new ArrayList<>();
        if (this.isUpAndDown) {
            cons.add(new Point(-1, 0));
            cons.add(new Point(0, 0));
            cons.add(new Point(1, 0));
            cons.add(new Point(2, 0));
        }
        else {
            cons.add(new Point(0, -1));
            cons.add(new Point(0, 0));
            cons.add(new Point(0, 1));
            cons.add(new Point(0, 2));
        }
        return cons;
    }

    @Override
    public Color getColor() {
        return GameField.getColorFromNumber(GameField.CYAN);
    }

    @Override
    public byte getColorCode() {
        return GameField.CYAN;
    }
}
