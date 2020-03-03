package Main;

import View.UIFrame;
import java.awt.Color;
import javax.swing.JFrame;

public class Main {
    
    public static final int FRAME_WIDTH = 750;
    public static final int FRAME_HEIGHT = 500;
    
    public static void main(String[] args) {
        JFrame frame = new UIFrame();
        frame.setTitle("Search Engine - Kelompok 3");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);
    }
}
