package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;

public class ColorSliderPanel extends JPanel {
    public JSlider slider;

    public ColorSliderPanel(String title) {
        this.setPreferredSize(new Dimension(120, 350));
        slider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
        slider.setPreferredSize(new Dimension(100, 310));
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(51);
        this.setBorder(BorderFactory.createTitledBorder(title));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(51));
        this.add(slider);
        this.setVisible(true);
    }
}
