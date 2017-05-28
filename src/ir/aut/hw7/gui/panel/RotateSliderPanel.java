package ir.aut.hw7.gui.panel;

import javax.swing.*;
import java.awt.*;

public class RotateSliderPanel extends JPanel {
    public JSlider slider;

    public RotateSliderPanel() {
        this.setPreferredSize(new Dimension(150, 600));
        slider = new JSlider(JSlider.VERTICAL, 0, 360, 0);
        slider.setPreferredSize(new Dimension(120, 560));
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(25);
        this.setBorder(BorderFactory.createTitledBorder("Rotating degree"));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(90));
        this.add(slider);
        this.setVisible(true);
    }
}
