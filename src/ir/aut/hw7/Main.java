package ir.aut.hw7;

import ir.aut.hw7.gui.Frame;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Frame frame = new Frame(JFrame.EXIT_ON_CLOSE, 1500, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}