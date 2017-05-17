package ir.aut.hw7.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel(BufferedImage img) {
        image = img;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
