package ir.aut.hw7.gui.frame;

import ir.aut.hw7.gui.panel.ColorSliderPanel;
import ir.aut.hw7.gui.panel.ImagePanel;
import ir.aut.hw7.gui.panel.RotateSliderPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame {
    private BufferedImage image;
    private ImagePanel imagePanel;
    private String myText;
    private RotateSliderPanel rotateSliderPanel;
    private JTextField textField;
    private ColorSliderPanel redSlider, blueSlider, greenSlider;
    private JButton resetButton;
    private BufferedImage backupImage;

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
            ColorModel cm = image.getColorModel();
            boolean isAlphaPreMultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = image.copyData(null);
            backupImage = new BufferedImage(cm, raster, isAlphaPreMultiplied, null);
            imagePanel = new ImagePanel(image);
            rotateSliderPanel = new RotateSliderPanel();
            loadingPhotoActs();
            Frame.this.add(rotateSliderPanel);
            Frame.this.add(imagePanel);
            Frame.this.setVisible(true);
            Frame.this.revalidate();
            Frame.this.repaint();
        }
    }

    private void loadingPhotoActs() {
        resetButton = new JButton("reset the photo to default");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imagePanel.setVisible(false);
                image = backupImage;
                imagePanel = new ImagePanel(image);
                imagePanel.setVisible(true);
                imagePanel.repaint();
                Frame.this.add(imagePanel);
                resetButton.setVisible(false);
            }
        });
        Frame.this.add(resetButton);
        rotateSliderPanel.slider.addChangeListener(e -> {
            imagePanel.setDegree(rotateSliderPanel.slider.getValue());
            imagePanel.repaint();
            Frame.this.revalidate();
            Frame.this.repaint();
        });
        redSlider = new ColorSliderPanel("Red value");
        redSlider.slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        int p = image.getRGB(i, j);
                        int alpha = (p >> 24) & 0xff;
                        int green = (p >> 8) & 0xff;
                        int blue = (p) & 0xff;
                        int newPixel = 0;
                        newPixel += alpha;
                        newPixel = newPixel << 8;
                        newPixel += redSlider.slider.getValue();
                        newPixel = newPixel << 8;
                        newPixel += green;
                        newPixel = newPixel << 8;
                        newPixel += blue;
                        image.setRGB(i, j, newPixel);
                        imagePanel.repaint();
                        imagePanel.revalidate();
                        Frame.this.repaint();
                    }
                }
            }
        });
        greenSlider = new ColorSliderPanel("Green value");
        greenSlider.slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        int p = image.getRGB(i, j);
                        int alpha = (p >> 24) & 0xff;
                        int red = (p >> 16) & 0xff;
                        int blue = (p) & 0xff;
                        int newPixel = 0;
                        newPixel += alpha;
                        newPixel = newPixel << 8;
                        newPixel += red;
                        newPixel = newPixel << 8;
                        newPixel += greenSlider.slider.getValue();
                        newPixel = newPixel << 8;
                        newPixel += blue;
                        image.setRGB(i, j, newPixel);
                        imagePanel.repaint();
                        imagePanel.revalidate();
                        Frame.this.repaint();
                    }
                }
            }
        });
        blueSlider = new ColorSliderPanel("Blue value");
        blueSlider.slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        int p = image.getRGB(i, j);
                        int alpha = (p >> 24) & 0xff;
                        int red = (p >> 16) & 0xff;
                        int green = (p >> 8) & 0xff;
                        int newPixel = 0;
                        newPixel += alpha;
                        newPixel = newPixel << 8;
                        newPixel += red;
                        newPixel = newPixel << 8;
                        newPixel += green;
                        newPixel = newPixel << 8;
                        newPixel += blueSlider.slider.getValue();
                        image.setRGB(i, j, newPixel);
                        imagePanel.repaint();
                        imagePanel.revalidate();
                        Frame.this.repaint();
                    }
                }
            }
        });
        Frame.this.add(redSlider);
        Frame.this.add(greenSlider);
        Frame.this.add(blueSlider);
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
                    ColorModel cm = image.getColorModel();
                    boolean isAlphaPreMultiplied = cm.isAlphaPremultiplied();
                    WritableRaster raster = image.copyData(null);
                    backupImage = new BufferedImage(cm, raster, isAlphaPreMultiplied, null);
                    imagePanel = new ImagePanel(image);
                    rotateSliderPanel = new RotateSliderPanel();
                    loadingPhotoActs();
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
            redSlider.setVisible(false);
            greenSlider.setVisible(false);
            blueSlider.setVisible(false);
            if (textField != null) textField.setVisible(false);
            resetButton = null;
            redSlider = null;
            greenSlider = null;
            blueSlider = null;
            textField = null;
            rotateSliderPanel = null;
            image = null;
            imagePanel = null;
            backupImage = null;
            myText = null;
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
            if (textField != null) return;
            textField = new JTextField("Enter text here");
            textField.setPreferredSize(new Dimension(100, 25));
            textField.addActionListener(e -> {
                String str;
                if (e.getSource() == textField) {
                    str = String.format("%s", e.getActionCommand());
                    myText = str;
                    JOptionPane.showMessageDialog(null, "This text is ready to use: " + myText);
                    printText();
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