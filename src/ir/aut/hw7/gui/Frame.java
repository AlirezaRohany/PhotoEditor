package ir.aut.hw7.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {
    public Frame(int defaultCloseOperation, int width, int height, boolean visibility) {
        super("Photo Editor");
        setLayout(new FlowLayout());
        this.setVisible(visibility);
        this.setSize(width, height);
        this.setDefaultCloseOperation(defaultCloseOperation);
        this.setTheMenuBar();
    }

    private void setTheMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        JMenu menu2 = new JMenu("Edit");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.setMnemonic(KeyEvent.VK_F);
        menu2.setMnemonic(KeyEvent.VK_E);
        JMenuItem item1 = new JMenuItem("New", KeyEvent.VK_N);
        JMenuItem item2 = new JMenuItem("Open", KeyEvent.VK_O);
        JMenuItem item3 = new JMenuItem("Close", KeyEvent.VK_C);
        JMenuItem item4 = new JMenuItem("Save", KeyEvent.VK_S);
        JMenuItem item5 = new JMenuItem("Add Text", KeyEvent.VK_T);
        JMenuItem item6 = new JMenuItem("Add Sticker", KeyEvent.VK_S);
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menu1.add(item4);
        menu2.add(item5);
        menu2.add(item6);
        this.setJMenuBar(menuBar);
    }
}