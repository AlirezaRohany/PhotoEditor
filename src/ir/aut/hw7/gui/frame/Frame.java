package ir.aut.hw7.gui.frame;

import com.jhlabs.image.MaskFilter;
import ir.aut.hw7.gui.panel.ColorSliderPanel;
import ir.aut.hw7.gui.panel.ImagePanel;
import ir.aut.hw7.gui.panel.RotateSliderPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame {
    private BufferedImage image = null;
    private ImagePanel imagePanel;
    private String myText;
    private RotateSliderPanel rotateSliderPanel;
    private JTextField textField;
    private ColorSliderPanel redSlider, blueSlider, greenSlider;
    private JButton resetButton, cropButton;
    private BufferedImage backupImage;
    private Point mousePt1, mousePt2;
    private int x1, y1, x2, y2;
    private JButton filterButton;

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
            image = new BufferedImage(960, 1280, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 960, 1280);
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
        filterButton = new JButton("Filters");
        filterButton.addActionListener(e -> {
            MaskFilter maskFilter = new MaskFilter();
            maskFilter.filter(image, image);
            Frame.this.repaint();
        });
        Frame.this.add(filterButton);
        cropButton = new JButton("Crop");
        cropButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Image is ready to crop.");
            imagePanel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    x1 = evt.getX();
                    y1 = evt.getY();
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                    setCursor(cursor);
                }

                public void mouseReleased(MouseEvent evt) {
                    x2 = evt.getX();
                    y2 = evt.getY();
                    if (x2 > x1) {
                        imagePanel.setX1(x1);
                        imagePanel.setX2(x2);
                    } else {
                        imagePanel.setX1(x2);
                        imagePanel.setX2(x1);
                    }
                    if (y2 > y1) {
                        imagePanel.setY1(y1);
                        imagePanel.setY2(y2);
                    } else {
                        imagePanel.setY1(y2);
                        imagePanel.setY2(y1);
                    }
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                    setCursor(cursor);
                    imagePanel.setVisible(true);
                    imagePanel.repaint();
                    Frame.this.repaint();
                }
            });
            imagePanel.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent evt) {
                    x2 = evt.getX();
                    y2 = evt.getY();
                    imagePanel.getGraphics().setColor(Color.BLACK);
                    if (x2 > x1 && y2 > y1) imagePanel.getGraphics().drawRect(x1, y1, x2 - x1, y2 - y1);
                    else if (x2 > x1 && y2 < y1) imagePanel.getGraphics().drawRect(x1, y2, x2 - x1, y1 - y2);
                    else if (x2 < x1 && y2 > y1) imagePanel.getGraphics().drawRect(x2, y1, x1 - x2, y2 - y1);
                    else if (x2 < x1 && y2 < y1) imagePanel.getGraphics().drawRect(x2, y2, x1 - x2, y1 - y2);
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
                    setCursor(cursor);
                    imagePanel.repaint();
                    imagePanel.setVisible(true);
                    Frame.this.repaint();
                    Frame.this.setVisible(true);
                }
            });
        });
        Frame.this.add(cropButton);
        resetButton = new JButton("Reset the photo to default");
        resetButton.addActionListener(e -> {
            imagePanel.setVisible(false);
            if (textField != null) textField.setVisible(false);
            textField = null;
            for (int i = 0; i < image.getWidth(); i++)
                for (int j = 0; j < image.getHeight(); j++) image.setRGB(i, j, backupImage.getRGB(i, j));
            imagePanel = new ImagePanel(image);
            imagePanel.setVisible(true);
            imagePanel.repaint();
            Frame.this.add(imagePanel);
            repaint();
        });
        Frame.this.add(resetButton);
        rotateSliderPanel.slider.addChangeListener(e -> {
            imagePanel.setDegree(rotateSliderPanel.slider.getValue());
            imagePanel.repaint();
            Frame.this.repaint();
        });
        redSlider = new ColorSliderPanel("Red value");
        redSlider.slider.addChangeListener(e -> {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int p = image.getRGB(i, j);
                    int alpha = (p >> 24) & 0xff;
                    int green = (p >> 8) & 0xff;
                    int blue = (p) & 0xff;
                    int myNewPixel = 0;
                    myNewPixel += alpha;
                    myNewPixel = myNewPixel << 8;
                    myNewPixel += redSlider.slider.getValue();
                    myNewPixel = myNewPixel << 8;
                    myNewPixel += green;
                    myNewPixel = myNewPixel << 8;
                    myNewPixel += blue;
                    image.setRGB(i, j, myNewPixel);
                    imagePanel.repaint();
                    Frame.this.repaint();
                }
            }
        });
        greenSlider = new ColorSliderPanel("Green value");
        greenSlider.slider.addChangeListener(e -> {
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
                    Frame.this.repaint();
                }
            }
        });
        blueSlider = new ColorSliderPanel("Blue value");
        blueSlider.slider.addChangeListener(e -> {
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
                    Frame.this.repaint();
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
            if (imagePanel != null) imagePanel.setVisible(false);
            if (rotateSliderPanel != null) rotateSliderPanel.setVisible(false);
            if (cropButton != null) cropButton.setVisible(false);
            if (redSlider != null) redSlider.setVisible(false);
            if (greenSlider != null) greenSlider.setVisible(false);
            if (blueSlider != null) blueSlider.setVisible(false);
            if (textField != null) textField.setVisible(false);
            if (resetButton != null) resetButton.setVisible(false);
            if (filterButton != null) filterButton.setVisible(false);
            resetButton = null;
            filterButton = null;
            redSlider = null;
            greenSlider = null;
            blueSlider = null;
            textField = null;
            rotateSliderPanel = null;
            image = null;
            imagePanel = null;
            backupImage = null;
            myText = null;
            cropButton = null;
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
                    BufferedImage finalImg = img.getSubimage(0, 0, imagePanel.getImage().getWidth(), imagePanel.getImage().getHeight());
                    ImageIO.write(finalImg, "png", selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class myActionListener5 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (imagePanel == null) {
                JOptionPane.showMessageDialog(null, "Please first add an image!");
            }
            if (textField == null && imagePanel != null) {
                textField = new JTextField("Enter text here", 30);
                textField.setPreferredSize(new Dimension(250, 50));
                textField.setFont(new Font("Courier", Font.BOLD, 20));
                textField.addActionListener(e -> {
                    String str;
                    if (e.getSource() == textField) {
                        str = String.format("%s", e.getActionCommand());
                        myText = str;
                        JOptionPane.showMessageDialog(null, "This text is ready to use: " + myText);
                        printTextLabel();
                    }
                });
                Frame.this.add(textField);
                Frame.this.setVisible(true);
                Frame.this.revalidate();
                Frame.this.repaint();
            }
        }
    }

    private void printTextLabel() {
        if (myText != null && imagePanel != null) {
            final JLabel label = new JLabel(myText);
            label.setBounds(250, 200, 300, 80);
            label.setFont(new Font("Courier", Font.BOLD, 20));
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    final JPanel textEditPanel = new JPanel();
                    final JButton editTextButton = new JButton("Edit the text");
                    final JButton sizeButton = new JButton("Change text size");
                    final JButton colorButton = new JButton("Change text color");
                    final JButton deleteButton = new JButton("Delete the text");

                    sizeButton.addActionListener(e15 -> {
                        String str = JOptionPane.showInputDialog("Please enter the font size:");
                        if (str != null) label.setFont(new Font("Courier", Font.BOLD, Integer.parseInt(str)));
                    });
                    deleteButton.addActionListener(e14 -> label.setVisible(false));
                    editTextButton.addActionListener(e13 -> {
                        String str = JOptionPane.showInputDialog("Please enter the new text you want to replace with the first one:");
                        if (str != null) label.setText(str);
                    });
                    colorButton.addActionListener(e12 -> {
                        final JPanel colorPanel = new JPanel();
                        final JRadioButton blackButton = new JRadioButton("black");
                        final JRadioButton blueButton = new JRadioButton("blue");
                        final JRadioButton redButton = new JRadioButton("red");
                        final JRadioButton greenButton = new JRadioButton("green");
                        final JRadioButton yellowButton = new JRadioButton("yellow");
                        final JRadioButton grayButton = new JRadioButton("gray");
                        final JRadioButton cyanButton = new JRadioButton("cyan");
                        final JRadioButton orangeButton = new JRadioButton("orange");
                        final JRadioButton magentaButton = new JRadioButton("magenta");
                        final JRadioButton pinkButton = new JRadioButton("pink");
                        final ButtonGroup buttonGroup = new ButtonGroup();
                        buttonGroup.add(blackButton);
                        buttonGroup.add(blueButton);
                        buttonGroup.add(redButton);
                        buttonGroup.add(greenButton);
                        buttonGroup.add(yellowButton);
                        buttonGroup.add(cyanButton);
                        buttonGroup.add(grayButton);
                        buttonGroup.add(magentaButton);
                        buttonGroup.add(orangeButton);
                        buttonGroup.add(pinkButton);
                        redButton.addActionListener(e1 -> label.setForeground(Color.RED));
                        blackButton.addActionListener(e1213 -> label.setForeground(Color.BLACK));
                        blueButton.addActionListener(e1212 -> label.setForeground(Color.BLUE));
                        greenButton.addActionListener(e121 -> label.setForeground(Color.GREEN));
                        yellowButton.addActionListener(e111 -> label.setForeground(Color.YELLOW));
                        cyanButton.addActionListener(e110 -> label.setForeground(Color.CYAN));
                        grayButton.addActionListener(e19 -> label.setForeground(Color.GRAY));
                        orangeButton.addActionListener(e18 -> label.setForeground(Color.ORANGE));
                        magentaButton.addActionListener(e17 -> label.setForeground(Color.MAGENTA));
                        pinkButton.addActionListener(e16 -> label.setForeground(Color.PINK));
                        colorPanel.add(blackButton);
                        colorPanel.add(greenButton);
                        colorPanel.add(redButton);
                        colorPanel.add(blueButton);
                        colorPanel.add(orangeButton);
                        colorPanel.add(yellowButton);
                        colorPanel.add(grayButton);
                        colorPanel.add(pinkButton);
                        colorPanel.add(cyanButton);
                        colorPanel.add(magentaButton);
                        JOptionPane.showMessageDialog(null, colorPanel);
                    });

                    textEditPanel.add(editTextButton);
                    textEditPanel.add(sizeButton);
                    textEditPanel.add(colorButton);
                    textEditPanel.add(deleteButton);
                    JOptionPane.showMessageDialog(null, textEditPanel);
                }

                public void mousePressed(MouseEvent me) {
                    Component component = (Component) me.getSource();
                    mousePt1 = component.getLocation();
                    mousePt2 = me.getLocationOnScreen();
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
                    setCursor(cursor);
                }

                public void mouseReleased(MouseEvent me) {
                    setLocationAct(me);
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                    setCursor(cursor);
                }
            });
            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent me) {
                    setLocationAct(me);
                    Cursor cursor;
                    cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
                    setCursor(cursor);
                }
            });
            imagePanel.add(label);
            this.setVisible(true);
            this.repaint();
            this.revalidate();
        }
    }

    private void setLocationAct(MouseEvent me) {
        Component component = (Component) me.getSource();
        Point locationOnScreen = me.getLocationOnScreen();
        int x = locationOnScreen.x - mousePt2.x + mousePt1.x;
        int y = locationOnScreen.y - mousePt2.y + mousePt1.y;
        component.setLocation(x, y);
        Frame.this.repaint();
    }
}