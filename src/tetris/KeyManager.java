package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager {

    public boolean leftPressed = false;
    public boolean upPressed = false;
    public boolean rightPressed = false;
    public boolean downPressed = false;
    
    private static final int LEFT_KEY_CODE = KeyEvent.VK_LEFT;
    private static final int UP_KEY_CODE = KeyEvent.VK_UP;
    private static final int RIGHT_KEY_CODE = KeyEvent.VK_RIGHT;
    private static final int DOWN_KEY_CODE = KeyEvent.VK_DOWN;
    
    public void keyDown(KeyEvent e) {
        switch (e.getKeyCode()) {
            case LEFT_KEY_CODE : leftPressed = true; break;
            case UP_KEY_CODE : upPressed = true; break;
            case RIGHT_KEY_CODE : rightPressed = true; break;
            case DOWN_KEY_CODE : downPressed = true; break;
        }
    }
    
    public void keyUp(KeyEvent e) {
        switch (e.getKeyCode()) {
            case LEFT_KEY_CODE : leftPressed = false; break;
            case UP_KEY_CODE : upPressed = false; break;
            case RIGHT_KEY_CODE : rightPressed = false; break;
            case DOWN_KEY_CODE : downPressed = false; break;
        }
    }
    
    @Override 
    public String toString() {
        String s = "";
        if (leftPressed)
            s += "left ";
        if (upPressed)
            s += "up ";
        if (rightPressed)
            s += "right ";
        if (downPressed)
            s += "down";
        return s;
    }
    
    
    public static class DefaultKeyListener implements KeyListener {

        private final KeyManager km;
        
        public DefaultKeyListener(KeyManager km) {
            this.km = km;
        }
        
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            km.keyDown(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            km.keyUp(e);
        }
        
    }
}
