package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;

public class ColorSliderPanel extends JPanel {
    public JSlider slider;

    public ColorSliderPanel(String title) {
        this.setPreferredSize(new Dimension(90, 250));
        slider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
        slider.setPreferredSize(new Dimension(80, 215));
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        this.setBorder(BorderFactory.createTitledBorder(title));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(51));
        this.add(slider);
        this.setVisible(true);
    }
}