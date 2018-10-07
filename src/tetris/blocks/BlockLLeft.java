package tetris.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import tetris.Direction;
import tetris.GameField;
import tetris.Point;

public class BlockLLeft 
    extends Block {

    Direction orientation = Direction.RIGHT;
    
    public BlockLLeft(GameField field, int startY, int startX) {
        super(field, startY, startX);
    }

    @Override
    public byte getColorCode() {
        return GameField.ORANGE;
    }

    @Override
    public Color getColor() {
        return GameField.getColorFromNumber(GameField.ORANGE);
    }

    @Override
    public boolean canMove(Direction dir) {
        switch (orientation) {
            case UP:
                switch (dir) {
                    case UP:    return false;
                    case RIGHT: return field.isEmpty(y-1, x+1) && field.isEmpty(y, x+1) && field.isEmpty(y+1, x+2);
                    case DOWN:  return field.isEmpty(y+2, x) && field.isEmpty(y+2, x+1);
                    case LEFT:  return field.isEmpty(y-1, x-1) && field.isEmpty(y, x-1) && field.isEmpty(y+1, x-1);
                    default:    return false;
                }
            case RIGHT:
                switch (dir) {
                    case UP:    return false;
                    case RIGHT: return field.isEmpty(y, x+2) && field.isEmpty(y+1, x);
                    case DOWN:  return field.isEmpty(y+2, x-1) && field.isEmpty(y+1, x) && field.isEmpty(y-1, x+1);
                    case LEFT:  return field.isEmpty(y, x-2) && field.isEmpty(y+1, x-2);
                    default:    return false;
                }
            case DOWN:
                switch (dir) {
                    case UP:    return false;
                    case RIGHT: return field.isEmpty(y-1, x+1) && field.isEmpty(y, x+1) && field.isEmpty(y+1, x+1);
                    case DOWN:  return field.isEmpty(y, x-1) && field.isEmpty(y+2, x);
                    case LEFT:  return field.isEmpty(y-1, x-2) && field.isEmpty(y, x-1) && field.isEmpty(y+1, x-1);
                    default:    return false;
                }
            case LEFT:
                switch (dir) {
                    case UP:    return false;
                    case RIGHT: return field.isEmpty(y-1, x+2) && field.isEmpty(y, x+2);
                    case DOWN:  return field.isEmpty(y+1, x-1) && field.isEmpty(y+1, x) && field.isEmpty(y+1, x+1);
                    case LEFT:  return field.isEmpty(y, x-2) && field.isEmpty(y-1, x);
                    default:    return false;
                }
            default:    return false;
        }
    }

    @Override
    public boolean canRotate() {
        switch (orientation) {
            case UP:    return field.isEmpty(y+1, x-1) && field.isEmpty(y, x-1) && field.isEmpty(y, x+1);
            case RIGHT: return field.isEmpty(y-1, x-1) && field.isEmpty(y-1, x) && field.isEmpty(y+1, x);
            case DOWN:  return field.isEmpty(y, x-1) && field.isEmpty(y-1, x+1) && field.isEmpty(y, x+1);
            case LEFT:  return field.isEmpty(y-1, x)   && field.isEmpty(y+1, x) && field.isEmpty(y+1, x+1);
            default:    return false;
        }
    }

    @Override
    public void rotate() {
        this.orientation = orientation.rotateClockwise();
    }

    @Override
    public List<Point> getConstruct() {
        List<Point> cons = new ArrayList<>();
        switch(orientation) {
            case UP:
                cons.add(new Point(-1,0));
                cons.add(new Point(0,0));
                cons.add(new Point(1,0));
                cons.add(new Point(1,1));
                break;
            case RIGHT:
                cons.add(new Point(1,-1));
                cons.add(new Point(0,-1));
                cons.add(new Point(0,0));
                cons.add(new Point(0,1));
                break;
            case DOWN:
                cons.add(new Point(-1,-1));
                cons.add(new Point(-1,0));
                cons.add(new Point(0,0));
                cons.add(new Point(1,0));
                break;
            case LEFT:
                cons.add(new Point(0,-1));
                cons.add(new Point(0,0));
                cons.add(new Point(0,1));
                cons.add(new Point(-1,1));
                break;
        }
        
        return cons;
    }

}
