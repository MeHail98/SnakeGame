import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageProducer;
import java.util.Random;

public class Field extends JPanel implements ActionListener {

    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_ELEMENTS = 1024;
    private Image dotImage;
    private Image appleImage;
    private int appleX;
    private int appleY;
    private int[] xArray = new int[ALL_ELEMENTS];
    private int[] yArray = new int[ALL_ELEMENTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right = true;
    private boolean up;
    private boolean down;
    private boolean inGame = true;


    public Field (){
        setBackground(Color.RED);
        loadImage();
        initGame();
                addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

     public void initGame(){
        dots = 3;
         for (int i = 0; i < dots; i++) {
              xArray[i] = 96 - i*DOT_SIZE;
              yArray[i] = 96;
         }
         timer = new Timer(250,this);
         timer.start();
         createApple();
     }

     private void createApple(){
        Random random = new Random();
        appleX = random.nextInt(32)*DOT_SIZE;
        appleY = random.nextInt(32)*DOT_SIZE;
     }

    private void loadImage(){
        ImageIcon imageIcon = new ImageIcon("M:/snake1.jpg");
        dotImage = imageIcon.getImage();
        ImageIcon imageIcon1 = new ImageIcon("M:/apple1.jpg");
        appleImage = imageIcon1.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(appleImage,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dotImage,xArray[i],yArray[i],this);
            }
        } else {
            String str = "Suck";
            g.setColor(Color.blue);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            xArray[i] = xArray[i-1];
            yArray[i] = yArray[i-1];
        }
        if(left) xArray[0] -=DOT_SIZE;
        if(right) xArray[0] +=DOT_SIZE;
        if(up) yArray[0] -=DOT_SIZE;
        if(down) yArray[0] +=DOT_SIZE;
    }

    public void checkApple(){
        if(xArray[0] == appleX && yArray[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(xArray[0]>SIZE) inGame = false;
            if(xArray[0]< 0) inGame = false;
            if(yArray[0]>SIZE) inGame = false;
            if(yArray[0]< 0) inGame = false;
            if(i>4 && xArray[0] == xArray[i] && yArray[0] == yArray[i]) inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
