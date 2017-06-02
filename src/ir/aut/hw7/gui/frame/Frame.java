package ir.aut.hw7.gui.frame;

import com.jhlabs.image.*;
import com.jhlabs.image.GrayFilter;
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
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        editMenu.setMnemonic(KeyEvent.VK_E);
        JMenuItem newItem = new JMenuItem("New", KeyEvent.VK_N);
        JMenuItem openItem = new JMenuItem("Open", KeyEvent.VK_O);
        JMenuItem closeItem = new JMenuItem("Close", KeyEvent.VK_C);
        JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        JMenuItem addTextItem = new JMenuItem("Add Text", KeyEvent.VK_T);
        JMenuItem addStickerItem = new JMenuItem("Add Sticker", KeyEvent.VK_S);
        fileMenu.add(newItem);
        newItem.addActionListener(new NewActionListener());
        fileMenu.add(openItem);
        openItem.addActionListener(new OpenActionListener());
        fileMenu.add(closeItem);
        closeItem.addActionListener(new CloseActionListener());
        fileMenu.add(saveItem);
        saveItem.addActionListener(new SaveActionListener());
        editMenu.add(addTextItem);
        addTextItem.addActionListener(new AddTextActionListener());
        editMenu.add(addStickerItem);
        addStickerItem.addActionListener(new AddStickerActionListener());
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    private class NewActionListener implements ActionListener {
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
            JPanel filterPanel = new JPanel();
            JLabel descLabel = new JLabel("Please choose the effect you want:");
            filterPanel.add(descLabel);
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton noneButton = new JRadioButton("None");
            JRadioButton maskButton = new JRadioButton("Mask filter");
            JRadioButton crystallizeButton = new JRadioButton("Crystallize filter");
            JRadioButton invertButton = new JRadioButton("Invert filter");
            JRadioButton lookUpButton = new JRadioButton("Lookup filter");
            JRadioButton solarizeButton = new JRadioButton("Solarize filter");
            JRadioButton exposureButton = new JRadioButton("Exposure filter");
            JRadioButton diffusionButton = new JRadioButton("Diffusion filter");
            JRadioButton weaveButton = new JRadioButton("Weave filter");
            JRadioButton chromeButton = new JRadioButton("Chrome filter");
            JRadioButton thresholdButton = new JRadioButton("Threshold filter");
            JRadioButton displaceButton = new JRadioButton("Displace filter");
            JRadioButton grayButton = new JRadioButton("Gray filter");
            buttonGroup.add(noneButton);
            buttonGroup.add(maskButton);
            buttonGroup.add(crystallizeButton);
            buttonGroup.add(invertButton);
            buttonGroup.add(lookUpButton);
            buttonGroup.add(solarizeButton);
            buttonGroup.add(exposureButton);
            buttonGroup.add(diffusionButton);
            buttonGroup.add(weaveButton);
            buttonGroup.add(chromeButton);
            buttonGroup.add(thresholdButton);
            buttonGroup.add(displaceButton);
            buttonGroup.add(grayButton);
            noneButton.addActionListener(e13 -> {
                BufferedImage bf = backupImage.getSubimage(0, 0, image.getWidth(), image.getHeight());
                for (int i = 0; i < image.getWidth(); i++)
                    for (int j = 0; j < image.getHeight(); j++) image.setRGB(i, j, bf.getRGB(i, j));
                Frame.this.repaint();
            });
            maskButton.addActionListener(e12 -> {
                MaskFilter maskFilter = new MaskFilter();
                maskFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            crystallizeButton.addActionListener(e1 -> {
                CrystallizeFilter crystallizeFilter = new CrystallizeFilter();
                crystallizeFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            invertButton.addActionListener(e113 -> {
                InvertFilter invertFilter = new InvertFilter();
                invertFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            lookUpButton.addActionListener(e112 -> {
                LookupFilter lookupFilter = new LookupFilter();
                lookupFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            solarizeButton.addActionListener(e111 -> {
                SolarizeFilter solarizeFilter = new SolarizeFilter();
                solarizeFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            exposureButton.addActionListener(e110 -> {
                ExposureFilter exposureFilter = new ExposureFilter();
                exposureFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            diffusionButton.addActionListener(e19 -> {
                DiffusionFilter diffusionFilter = new DiffusionFilter();
                diffusionFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            weaveButton.addActionListener(e18 -> {
                WeaveFilter weaveFilter = new WeaveFilter();
                weaveFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            chromeButton.addActionListener(e17 -> {
                ChromeFilter chromeFilter = new ChromeFilter();
                chromeFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            thresholdButton.addActionListener(e16 -> {
                ThresholdFilter thresholdFilter = new ThresholdFilter();
                thresholdFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            displaceButton.addActionListener(e15 -> {
                DisplaceFilter displaceFilter = new DisplaceFilter();
                displaceFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            grayButton.addActionListener(e14 -> {
                GrayFilter grayFilter = new GrayFilter();
                grayFilter.filter(backupImage, image);
                Frame.this.repaint();
            });
            filterPanel.add(noneButton);
            filterPanel.add(maskButton);
            filterPanel.add(crystallizeButton);
            filterPanel.add(invertButton);
            filterPanel.add(lookUpButton);
            filterPanel.add(solarizeButton);
            filterPanel.add(exposureButton);
            filterPanel.add(thresholdButton);
            filterPanel.add(diffusionButton);
            filterPanel.add(weaveButton);
            filterPanel.add(chromeButton);
            filterPanel.add(displaceButton);
            filterPanel.add(grayButton);
            JOptionPane.showMessageDialog(null, filterPanel);
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

    private class OpenActionListener implements ActionListener {
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

    private class CloseActionListener implements ActionListener {
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

    private class SaveActionListener implements ActionListener {
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

    private class AddTextActionListener implements ActionListener {
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
                    mousePressedAct(me);
                }

                public void mouseReleased(MouseEvent me) {
                    mouseReleasedAct(me);
                }
            });
            label.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent me) {
                    mouseDraggedAct(me);
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

    private void mouseDraggedAct(MouseEvent me) {
        setLocationAct(me);
        Cursor cursor;
        cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        setCursor(cursor);
    }

    private void mousePressedAct(MouseEvent me) {
        Component component = (Component) me.getSource();
        mousePt1 = component.getLocation();
        mousePt2 = me.getLocationOnScreen();
        Cursor cursor;
        cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        setCursor(cursor);
    }

    private void mouseReleasedAct(MouseEvent me) {
        setLocationAct(me);
        Cursor cursor;
        cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        setCursor(cursor);
    }

    private void addMouseListenersToLabel(JLabel myLabel) {
        myLabel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                mouseDraggedAct(me);
            }
        });
        myLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                mousePressedAct(me);
            }

//            public void mouseClicked(MouseEvent e) {
//                int dialogButton = JOptionPane.YES_NO_OPTION;
//                int result = JOptionPane.showConfirmDialog(null, "Would you like to remove this sticker?", "Remove sticker ", dialogButton);
//                if (result == JOptionPane.YES_OPTION) {
//                    myLabel.setVisible(false);
//                }
//            }

            public void mouseReleased(MouseEvent me) {
                mouseReleasedAct(me);
            }
        });
    }

    private void addMouseListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                imagePanel.add(label);
                label.setLocation(300, 300);
                addMouseListenersToLabel(label);
                Frame.this.repaint();
            }
        });
    }

    private class AddStickerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (imagePanel == null) {
                JOptionPane.showMessageDialog(null, "Please first add an image");
                return;
            }
            JPanel stickerPanel = new JPanel();
            JLabel label1 = new JLabel();
            JLabel label2 = new JLabel();
            JLabel label3 = new JLabel();
            JLabel label4 = new JLabel();
            JLabel label5 = new JLabel();
            JLabel label6 = new JLabel();
            JLabel label7 = new JLabel();
            JLabel label8 = new JLabel();
            JLabel label9 = new JLabel();
            JLabel label10 = new JLabel();


            ImageIcon imageIcon1 = new ImageIcon("stickers\\Monztars_1.png");
            ImageIcon imageIcon2 = new ImageIcon("stickers\\Monztars_2.png");
            ImageIcon imageIcon3 = new ImageIcon("stickers\\Monztars_3.png");
            ImageIcon imageIcon4 = new ImageIcon("stickers\\Monztars_4.png");
            ImageIcon imageIcon5 = new ImageIcon("stickers\\Monztars_5.png");
            ImageIcon imageIcon6 = new ImageIcon("stickers\\Monztars_6.png");
            ImageIcon imageIcon7 = new ImageIcon("stickers\\Monztars_7.png");
            ImageIcon imageIcon8 = new ImageIcon("stickers\\Monztars_8.png");
            ImageIcon imageIcon9 = new ImageIcon("stickers\\Monztars_9.png");
            ImageIcon imageIcon10 = new ImageIcon("stickers\\Monztars_10.png");


            label1.setIcon(imageIcon1);
            label2.setIcon(imageIcon2);
            label3.setIcon(imageIcon3);
            label4.setIcon(imageIcon4);
            label5.setIcon(imageIcon5);
            label6.setIcon(imageIcon6);
            label7.setIcon(imageIcon7);
            label8.setIcon(imageIcon8);
            label9.setIcon(imageIcon9);
            label10.setIcon(imageIcon10);

            addMouseListener(label1);
            addMouseListener(label2);
            addMouseListener(label3);
            addMouseListener(label4);
            addMouseListener(label5);
            addMouseListener(label6);
            addMouseListener(label7);
            addMouseListener(label8);
            addMouseListener(label9);
            addMouseListener(label10);


            stickerPanel.add(label1);
            stickerPanel.add(label2);
            stickerPanel.add(label3);
            stickerPanel.add(label4);
            stickerPanel.add(label5);
            stickerPanel.add(label6);
            stickerPanel.add(label7);
            stickerPanel.add(label8);
            stickerPanel.add(label9);
            stickerPanel.add(label10);

            JOptionPane.showMessageDialog(null, stickerPanel);
        }
    }
}