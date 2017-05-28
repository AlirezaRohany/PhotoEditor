package ir.aut.hw7;

import ir.aut.hw7.gui.frame.Frame;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new Frame(JFrame.EXIT_ON_CLOSE, 2500, 1600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}