package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private double degree;
    private BufferedImage image;
    private int x1, y1, x2, y2;

    public ImagePanel(BufferedImage img) {
        image = img;
        setPreferredSize(new Dimension(960, 1280));
        this.setLayout(null);
        x1 = y1 = 0;
        x2 = image.getWidth();
        y2 = image.getHeight();
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        graphics.rotate(Math.toRadians(degree), x, y);
        super.paintComponent(g);
        if (x2 - x1 < image.getWidth() && y2 - y1 < image.getHeight())
            image = image.getSubimage(x1, y1, x2 - x1, y2 - y1);
        g.drawImage(image, 0, 0, this);
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

}
