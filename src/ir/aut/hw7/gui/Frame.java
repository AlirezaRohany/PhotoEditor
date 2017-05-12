package ir.aut.hw7.gui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(int defaultCloseOperation, int width, int height, boolean visibility) {
        super("Photo Editor");
        setLayout(new FlowLayout());
        this.setVisible(true);
        this.setSize(width, height);
        this.setDefaultCloseOperation(defaultCloseOperation);
    }
}
