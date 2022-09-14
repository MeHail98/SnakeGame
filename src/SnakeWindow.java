import javax.swing.*;
import java.awt.*;

public class SnakeWindow {

    private JFrame jframe;

    public SnakeWindow(){
        jframe = new JFrame();
        initialize(jframe);
    }

    private void initialize(JFrame jframe){
        jframe.setTitle("Snake");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setBackground(Color.blue);
        jframe.setSize(640,690);
        jframe.setLocation(250,150);
        jframe.setResizable(false);
        jframe.add(new Field());
        jframe.setVisible(true);
    }
}
