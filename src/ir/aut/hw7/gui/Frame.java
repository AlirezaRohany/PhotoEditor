package ir.aut.hw7.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame {
    private BufferedImage image;
    private ImagePanel imagePanel;
    private String myText;
    private RotateSliderPanel rotateSliderPanel;
    private JTextField textField;
    private JLabel label;

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
            if (imagePanel != null) {
                JOptionPane.showMessageDialog(null, "Please first close the current image!");
                return;
            }
            image = new BufferedImage(650, 800, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 650, 800);
            imagePanel = new ImagePanel(image);
            rotateSliderPanel = new RotateSliderPanel();
            rotateSliderPanel.slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    imagePanel.degree = rotateSliderPanel.slider.getValue();
                    imagePanel.repaint();
                    Frame.this.revalidate();
                    Frame.this.repaint();
                }
            });
            Frame.this.add(rotateSliderPanel);
            Frame.this.add(imagePanel);
            Frame.this.setVisible(true);
            Frame.this.revalidate();
            Frame.this.repaint();
        }
    }

    private class myActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (imagePanel != null) {
                JOptionPane.showMessageDialog(null, "Please first close the current image!");
                return;
            }
            JFileChooser jFileChooser = new JFileChooser();
            int returnValue = jFileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(file);
                    imagePanel = new ImagePanel(image);
                    rotateSliderPanel = new RotateSliderPanel();
                    rotateSliderPanel.slider.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent e) {
                            imagePanel.degree = rotateSliderPanel.slider.getValue();
                            imagePanel.repaint();
                            Frame.this.revalidate();
                            Frame.this.repaint();
                        }
                    });
                    Frame.this.add(rotateSliderPanel);
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
            rotateSliderPanel.setVisible(false);
            if (textField != null) textField.setVisible(false);
            textField = null;
            rotateSliderPanel = null;
            imagePanel = null;
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
                    BufferedImage img = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    imagePanel.paint(img.getGraphics());
                    ImageIO.write(img, "png", selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class myActionListener5 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            textField = new JTextField("Enter text here");
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
            label = new JLabel(myText);
            label.setBounds(250, 100, 100, 100);
            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent me) {
                    label.setLocation(me.getX(), me.getY());
                    label.repaint();
                    Frame.this.repaint();
                }
            });
            imagePanel.add(label);
            this.setVisible(true);
            this.repaint();
            this.revalidate();
        }
    }
}