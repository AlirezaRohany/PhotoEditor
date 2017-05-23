package ir.aut.hw7.gui;

import javax.swing.*;
import java.awt.*;

class RotateSliderPanel extends JPanel {
    JSlider slider;

    RotateSliderPanel() {
        setPreferredSize(new Dimension(300, 50));
        slider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(90));
        add(slider);
    }
}