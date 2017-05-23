package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private double degree;
    private BufferedImage image;

    public ImagePanel(BufferedImage img) {
        image = img;
        setPreferredSize(new Dimension(650, 800));
        this.setLayout(null);
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        graphics.rotate(Math.toRadians(degree), x, y);
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
