package paint;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {
    public Player player;

    public final int screenWidth = 1600;
    public final int screenHeight = 900;

    public PaintPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1600, 900));
        setBackground(Color.GRAY);
        setVisible(true);
    }

    public void update() {

    }
}