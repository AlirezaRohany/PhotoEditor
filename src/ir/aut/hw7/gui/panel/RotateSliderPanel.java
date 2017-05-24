package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;

public class RotateSliderPanel extends JPanel {
    public JSlider slider;

    public RotateSliderPanel() {
        this.setPreferredSize(new Dimension(120, 300));
        slider = new JSlider(JSlider.VERTICAL, 0, 360, 0);
        slider.setPreferredSize(new Dimension(90, 265));
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        this.setBorder(BorderFactory.createTitledBorder("Rotating degree"));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(90));
        this.add(slider);
        this.setVisible(true);
    }
}
