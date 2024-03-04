package midi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * This class is a canvas where all the piano rounded rectangles are drawn
 */

public class MidiGraphics extends JComponent implements MouseListener, MouseMotionListener{
    private final int Y_COORDINATE = 140; //y coordinate of all keys
    private int whiteKeyStartPosition = 100; //starting position of all white keys
    private final int WK_WIDTH = 20; //white key width
    private final int WK_HEIGHT = 135; //white key height
    private final int WK_ARCH = 7;  //white key arch width and arch height
    private final int BK_WIDTH = 12;  //black key width
    private final int BK_HEIGHT = 90;  //black key height
    private final int BK_ARCH = 5;   //black key arch width and arch height
    private int oldKeyNumber = 0;
    private boolean oldKeyWhite = true;

//    private volatile int screenX = 0;
//    private volatile int screenY = 0;
//    private volatile int myX = 0;
//    private volatile int myY = 0;
    private RoundRectangle2D mrb;

    private RoundRectangle2D[] whiteKey; //WHITE keys array

    private RoundRectangle2D[] blackKey = new RoundRectangle2D.Double[20]; //BLACK keys array
    private BufferedImage buffer;

    /**
     * constructor
     */
    public MidiGraphics(){
        //instantiate rectangle midi base
        mrb = new RoundRectangle2D.Double(90, 80, 580, 210, 14,14);

        //initialize white key rectangles
        whiteKey = new RoundRectangle2D.Double[28];
        for(int i = 0; i < whiteKey.length; i++){
            whiteKey[i] = new RoundRectangle2D.Double(whiteKeyStartPosition,Y_COORDINATE, WK_WIDTH, WK_HEIGHT, WK_ARCH,WK_ARCH );
            whiteKeyStartPosition += 20;
        }

        //initialize black keys manually because of uneven gap space
        blackKey[0] = new RoundRectangle2D.Double(115, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[1] = new RoundRectangle2D.Double(135, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[2] = new RoundRectangle2D.Double(175, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[3] = new RoundRectangle2D.Double(195, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[4] = new RoundRectangle2D.Double(215, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[5] = new RoundRectangle2D.Double(255, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[6] = new RoundRectangle2D.Double(275, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[7] = new RoundRectangle2D.Double(315, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[8] = new RoundRectangle2D.Double(335, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[9] = new RoundRectangle2D.Double(355, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[10] = new RoundRectangle2D.Double(395, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[11] = new RoundRectangle2D.Double(415, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[12] = new RoundRectangle2D.Double(455, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[13] = new RoundRectangle2D.Double(475, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[14] = new RoundRectangle2D.Double(495, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[15] = new RoundRectangle2D.Double(535, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[16] = new RoundRectangle2D.Double(555, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[17] = new RoundRectangle2D.Double(595, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[18] = new RoundRectangle2D.Double(615, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);
        blackKey[19] = new RoundRectangle2D.Double(635, Y_COORDINATE, BK_WIDTH, BK_HEIGHT, BK_ARCH,BK_ARCH);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    @Override
    public void paintComponent(Graphics g){
        if(buffer == null){
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        }
        Graphics2D g2 = (Graphics2D)buffer.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(142, 194, 194));
        g2.fillRect(0,0,getWidth(),getHeight());

        g2.setColor(new Color(17, 15, 15, 174));
        g2.fill(mrb);
        g2.setColor(Color.black);
        g2.draw(mrb);
        paintWhiteKeys(g2);
        paintBlackKeys(g2);

        g.drawImage(buffer, 0,0, null);
    }

    /**
     * paints each black key rectangle
     * @param g2 graphics
     */
    private void paintBlackKeys(Graphics2D g2){
        g2.setColor(new Color(5, 9, 26, 255));
        for(int i = 0; i < blackKey.length; i++){
            g2.fill(blackKey[i]);
        }
    }

    /**
     * paints each white key rectangle
     * @param g2 graphics
     */
    private void paintWhiteKeys(Graphics2D g2){
        g2.setColor(Color.WHITE);
        for(int i = 0; i < whiteKey.length; i++){
            g2.fill(whiteKey[i]);
        }
        g2.setColor(Color.BLACK);
        for(int i = 0; i < whiteKey.length; i++){
            g2.draw(whiteKey[i]);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < blackKey.length; i++) {
            if ((e.getButton()) == 1 && blackKey[i].contains(e.getX(), e.getY())) {
                profile.ProfileController.play(profile.KeyConverter.getBlackKey(i));
                changeKeyColorOnMousePressed(i, false);
                return;
            }
        }

        for (int i = 0; i < whiteKey.length; i++) {
            if ((e.getButton()) == 1 && whiteKey[i].contains(e.getX(), e.getY())) {
                changeKeyColorOnMousePressed(i, true);
                profile.ProfileController.play(profile.KeyConverter.getWhiteKey(i));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < blackKey.length; i++) {
            if ((e.getButton()) == 1 && blackKey[i].contains(e.getX(), e.getY())) {
                changeKeyColorOnMouseReleased(i, false);
                profile.ProfileController.stop(profile.KeyConverter.getBlackKey(i));
                return;
            }
        }

        for (int i = 0; i < whiteKey.length; i++) {
            if ((e.getButton()) == 1 && whiteKey[i].contains(e.getX(), e.getY())) {
                changeKeyColorOnMouseReleased(i, true);
                profile.ProfileController.stop(profile.KeyConverter.getWhiteKey(i));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < blackKey.length; i++) {
            if (blackKey[i].contains(e.getX(), e.getY())) {
                changeKeyColorOnMouseMoved(i, false);
                return;
            }
        }

        for (int i = 0; i < whiteKey.length; i++){
            if (whiteKey[i].contains(e.getX(), e.getY())) {
                changeKeyColorOnMouseMoved(i, true);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private void changeKeyColorOnMouseMoved(int keyNumber, boolean white) {
        if (keyNumber == oldKeyNumber && white == oldKeyWhite) {
            return;
        }

        changeKeyColorOnMouseReleased(oldKeyNumber, oldKeyWhite);
        oldKeyNumber = keyNumber;
        oldKeyWhite = white;

        Color color = generateColor();
        changeKeyColor(keyNumber, white, color);
    }

    private void changeKeyColorOnMousePressed(int keyNumber, boolean white) {
        Color color = generateColor();
        changeKeyColor(keyNumber, white, color);
    }

    private void changeKeyColorOnMouseReleased(int keyNumber, boolean white) {
        Color color = white ? Color.WHITE : Color.BLACK;
        changeKeyColor(keyNumber, white, color);
    }

    private void changeKeyColor(int keyNumber, boolean white, Color color) {
        Graphics g = getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        if (white) {
            g2.setColor(color);
            g2.fill(whiteKey[keyNumber]);

            g2.setColor(Color.BLACK);
            g2.draw(whiteKey[keyNumber]);

            // Repaint black keys
            for (int i = 0; i < blackKey.length; i++) {
                g2.setColor(Color.BLACK);
                g2.fill(blackKey[i]);
            }
        } else {
            g2.setColor(color);
            g2.fill(blackKey[keyNumber]);
        }
    }

    /**
     * generates new random colors
     * @return
     */
    public Color generateColor(){
        int r = (int)(Math.random() * 256);
        int g = (int)(Math.random() * 256);
        int b = (int)(Math.random() * 256);
        int a = 100;
        return new Color(r,g,b,a).brighter().brighter();
    }
  
}
