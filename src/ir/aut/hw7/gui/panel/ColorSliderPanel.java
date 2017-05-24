package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;

public class ColorSliderPanel extends JPanel {
    public JSlider slider;

    public ColorSliderPanel(String title) {
        setPreferredSize(new Dimension(250, 50));
        slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(25);
        this.setBorder(BorderFactory.createTitledBorder(title));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(51));
        add(slider);
    }
}
