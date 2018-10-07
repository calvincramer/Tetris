package tetris;

import tetris.blocks.Block;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GameWindow 
    extends JFrame {
    
    private final GameField board;
    private final KeyManager keyManager;
    
    private final Graphics2D offScreenG;
    private final Image offScreenImage;
    
    private final JPanel gamePanel;
    private final JPanel upNextPanel;
    private final JLabel scoreLabel;
    
    private final JButton restartButton;
    
    private final Color BACKGROUND_COLOR = new Color(40,40,40);
    private final Color BOARD_BACKGROUND_COLOR = new Color(20,20,40);
    private final Color BOARD_BORDER_COLOR = new Color(130,130,150);
    private final Color BOARD_INTERIOR_LINES_COLOR = new Color(50,50,70);
    
    private final Dimension WINDOW_SIZE = new Dimension(1000,1000);
    private final Dimension GAME_PANEL_SIZE = new Dimension(386,700);
    private final Dimension UP_NEXT_PANEL_SIZE = new Dimension(120, 100*GameField.BLOCKS_UP_NEXT_SIZE);
    private final Dimension SCORE_LABEL_SIZE = new Dimension(200, 80);
    private final int SPACING = 14;
    
    private final int BOARD_BORDER_WIDTH = 10;
    private final int PIXELS_PER_SQUARE = 32;
    private final int PIXELS_PER_SQUARE_UP_NEXT = 24;
    private final int widthOfBoard;
    private final int heightOfBoard;
    
    public GameWindow(GameField gf, KeyManager km) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setSize(1000, 1000);
        this.setTitle("Tetris");
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setFocusable(true);
        this.keyManager = km;
        this.board = gf;
        this.setFocusTraversalKeysEnabled(false);
        
        
        this.gamePanel = new JPanel() {     //panel where the game is displayed
            @Override
            public void paint(Graphics g) { //paint the game state
                if (offScreenG == null)
                    return;

                offScreenG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                offScreenG.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                offScreenG.setColor(BACKGROUND_COLOR);                      //fill background
                offScreenG.fillRect(0, 0, this.getWidth(), this.getHeight());

                //paint board
                int leftBoard = this.getWidth() / 2 - widthOfBoard / 2;
                int topBoard = this.getHeight() / 2 - heightOfBoard / 2;
                //border
                offScreenG.setColor(BOARD_BORDER_COLOR);
                offScreenG.fillRect(leftBoard - BOARD_BORDER_WIDTH, topBoard - BOARD_BORDER_WIDTH, widthOfBoard + (2 * BOARD_BORDER_WIDTH), heightOfBoard + (2 * BOARD_BORDER_WIDTH));
                //background
                offScreenG.setColor(BOARD_BACKGROUND_COLOR);
                offScreenG.fillRect(leftBoard, topBoard, widthOfBoard, heightOfBoard);

                offScreenG.setStroke(new BasicStroke(2.0f));
                offScreenG.setColor(BOARD_INTERIOR_LINES_COLOR);
                //lines
                for (int y = topBoard; y < topBoard + heightOfBoard; y += PIXELS_PER_SQUARE) {
                    for (int x = leftBoard; x < leftBoard + widthOfBoard; x += PIXELS_PER_SQUARE) {
                        offScreenG.drawRect(x, y, PIXELS_PER_SQUARE, PIXELS_PER_SQUARE);
                    }
                }

                //tiles
                offScreenG.setStroke(new BasicStroke(1.0f));
                int indexY = 0;
                int indexX = 0;
                for (int y = topBoard; y < topBoard + heightOfBoard; y += PIXELS_PER_SQUARE) {
                    for (int x = leftBoard; x < leftBoard + widthOfBoard; x += PIXELS_PER_SQUARE) {
                        Color tileColor = board.getColorFromNumber(board.field[indexY][indexX]);
                        if (tileColor != null) {
                            offScreenG.setColor(tileColor);
                            offScreenG.fillRect(x + 1, y + 1, PIXELS_PER_SQUARE - 2, PIXELS_PER_SQUARE - 2);
                        }
                        indexX++;
                    }
                    indexX = 0;
                    indexY++;
                }
                //active block
                if (board.activeBlock != null) {
                    offScreenG.setColor(board.activeBlock.getColor());
                    for (Point p : board.activeBlock.getConstruct()) {
                        int coordY = p.y + board.activeBlock.y;
                        int coordX = p.x + board.activeBlock.x;
                        if (coordY >= 0 && coordX >= 0)             //stops active block at very top of board showing things above board
                            offScreenG.fillRect(coordX * PIXELS_PER_SQUARE + 1 + leftBoard, coordY * PIXELS_PER_SQUARE + 1 + topBoard, PIXELS_PER_SQUARE - 2, PIXELS_PER_SQUARE - 2);
                    }
                }

                g.drawImage(offScreenImage, 0, 0, this);
                super.paintBorder(g);       //only care about border, this panel should have no components to be painted
            };
        };       
        this.gamePanel.setBorder(new LineBorder(Color.BLACK, 2));
        this.gamePanel.setBounds(WINDOW_SIZE.width / 2 - GAME_PANEL_SIZE.width / 2, WINDOW_SIZE.height / 2 - GAME_PANEL_SIZE.height / 2, 
                GAME_PANEL_SIZE.width, GAME_PANEL_SIZE.height);
        this.getContentPane().add(gamePanel);
        
        
        this.upNextPanel = new JPanel() {
            @Override public void paint(Graphics g) {
                int y = 50;
                int width = PIXELS_PER_SQUARE_UP_NEXT;
                for (Block b : board.blocksUpNext) {
                    g.setColor(b.getColor());
                    for (Point p : b.getConstruct())
                        g.fillRect(p.x * width + this.getWidth() / 2 - width / 2,y + p.y * width, width, width);
                    y+=100;
                }
                super.paintBorder(g);
            }
        };
        this.upNextPanel.setBounds(WINDOW_SIZE.width / 2 + GAME_PANEL_SIZE.width / 2 + SPACING, WINDOW_SIZE.height / 2 - UP_NEXT_PANEL_SIZE.height / 2, 
                UP_NEXT_PANEL_SIZE.width, UP_NEXT_PANEL_SIZE.height);
        this.upNextPanel.setBorder(new LineBorder(Color.BLACK, 2));
        this.getContentPane().add(this.upNextPanel);
        
        
        this.scoreLabel = new JLabel("Score: 0");
        this.scoreLabel.setBorder(new LineBorder(Color.BLACK, 2));
        this.scoreLabel.setFont(new Font("Veranda", Font.PLAIN, 28));
        this.scoreLabel.setForeground(Color.WHITE);
        this.scoreLabel.setBounds(WINDOW_SIZE.width / 2 - GAME_PANEL_SIZE.width / 2 - SPACING - SCORE_LABEL_SIZE.width, 
                WINDOW_SIZE.height / 2 + GAME_PANEL_SIZE.height / 2 - SCORE_LABEL_SIZE.height, 
                SCORE_LABEL_SIZE.width, SCORE_LABEL_SIZE.height);
        this.scoreLabel.setHorizontalAlignment(JLabel.LEFT);
        this.getContentPane().add(this.scoreLabel);
        
        
        this.restartButton = new CustomButton("Restart");   //restart button
        this.restartButton.setBounds(0, 0, 100, 100);
        this.getContentPane().add(this.restartButton);
        
        KeyManager.DefaultKeyListener keyListener = new KeyManager.DefaultKeyListener(this.keyManager);
        this.addKeyListener(keyListener);
        
        this.widthOfBoard = this.board.field[0].length * PIXELS_PER_SQUARE;
        this.heightOfBoard = this.board.field.length * PIXELS_PER_SQUARE;
        
        this.setVisible(true);
        this.requestFocusInWindow();
        
        offScreenImage = createImage(1000,1000);
        offScreenG = (Graphics2D) offScreenImage.getGraphics();
    }
    
    
    public void setScoreLabel(int score) {
        if (this.scoreLabel != null)
            this.scoreLabel.setText("Score: "+score);
    }
}