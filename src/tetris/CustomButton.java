package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class CustomButton 
    extends JButton {
    
    private final Color MOUSE_NOT_OVER = new Color(40,40,40);
    private final Color MOUSE_OVER = new Color(80,80,80);
    private final Color MOUSE_DOWN = new Color(180,180,180);
    
    public CustomButton(String text) {
        super(text);
        
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 25));
        
        this.setFont(new Font("Tahoma", Font.PLAIN, 12).deriveFont(15.0f));
        this.setForeground(new Color(255,255,255));
        this.setBackground(MOUSE_NOT_OVER);
        this.setBorderPainted(false);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                //setBackground(MOUSE_DOWN);
            }
            @Override public void mousePressed(MouseEvent e) {
                setBackground(MOUSE_DOWN);
                repaint();
            }
            @Override public void mouseReleased(MouseEvent e) {
                setBackground(MOUSE_OVER);
                repaint();
            }
            @Override public void mouseEntered(MouseEvent e) {
                setBackground(MOUSE_OVER);
                repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                setBackground(MOUSE_NOT_OVER);
                repaint();
            }
        });
    }
    
    @Override public void paint(Graphics g) {
        if (this.isEnabled() ) {
            g.setColor(this.getBackground());
            this.setForeground(new Color(255,255,255));
        } else {
            g.setColor(new Color(25,25,25));
            this.setForeground(new Color(160,160,160));
        }
        
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        super.paint(g);
    }
    
}
