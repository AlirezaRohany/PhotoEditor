package ir.aut.hw7.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame {
    private BufferedImage image;
    private ImagePanel imagePanel;
    private String myText;
    private SliderPanel sliderPanel;

    public Frame(int defaultCloseOperation, int width, int height) throws IOException {
        super("Photo Editor");
        setLayout(new FlowLayout());
        this.setSize(width, height);
        this.setDefaultCloseOperation(defaultCloseOperation);
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
        item1.addActionListener(new myActionListener1());
        menu1.add(item2);
        item2.addActionListener(new myActionListener2());
        menu1.add(item3);
        item3.addActionListener(new myActionListener3());
        menu1.add(item4);
        item4.addActionListener(new myActionListener4());
        menu2.add(item5);
        item5.addActionListener(new myActionListener5());
        menu2.add(item6);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    private class myActionListener1 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            image = new BufferedImage(650, 800, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(Color.cyan);
            g.fillRect(0, 0, 650, 800);
            imagePanel = new ImagePanel(image);
            sliderPanel=new SliderPanel();
            sliderPanel.slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    imagePanel.degree=sliderPanel.slider.getValue();
                    imagePanel.repaint();
                }
            });
            Frame.this.add(sliderPanel);
            Frame.this.add(imagePanel);
            Frame.this.setVisible(true);
            Frame.this.revalidate();
            Frame.this.repaint();
        }
    }

    private class myActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JFileChooser jFileChooser = new JFileChooser();
            int returnValue = jFileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(file);
                    imagePanel = new ImagePanel(image);
                    sliderPanel=new SliderPanel();
                    sliderPanel.slider.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent e) {
                            imagePanel.degree=sliderPanel.slider.getValue();
                            imagePanel.repaint();
                        }
                    });
                    Frame.this.add(sliderPanel);
                    Frame.this.add(imagePanel);
                    Frame.this.setVisible(true);
                    Frame.this.revalidate();
                    Frame.this.repaint();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class myActionListener3 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            imagePanel.setVisible(false);
            image = null;
        }
    }

    private class myActionListener4 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter pFilter = new FileNameExtensionFilter("png", "png");
            jFileChooser.setFileFilter(pFilter);
            int status = jFileChooser.showSaveDialog(Frame.this);
            if (status == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jFileChooser.getSelectedFile();
                String fileName;
                try {
                    fileName = selectedFile.getCanonicalPath();
                    selectedFile = new File(fileName + ".png");
                    ImageIO.write(image, "png", selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class myActionListener5 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JTextField textField = new JTextField("Enter text here");
            textField.setPreferredSize(new Dimension(100, 25));
            textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = "";
                    if (e.getSource() == textField) {
                        str = String.format("%s", e.getActionCommand());
                        myText = str;
                        JOptionPane.showMessageDialog(null, "This text is ready to use: " + myText);
                        printText();
                    }
                }
            });
            Frame.this.add(textField);
            Frame.this.setVisible(true);
            Frame.this.revalidate();
            Frame.this.repaint();
        }
    }

    private void printText() {
        if (myText != null && imagePanel != null) {
            JLabel label = new JLabel(myText);
            label.setBounds(10, 10, 100, 100);
            imagePanel.add(label);
            this.setVisible(true);
            this.repaint();
            this.revalidate();
        }
    }
}