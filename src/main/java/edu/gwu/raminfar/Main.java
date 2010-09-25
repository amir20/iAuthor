package edu.gwu.raminfar;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class Main {
    public static boolean IS_MAC;
    public static String APP_NAME = "iAuthor";

    public static void main(String... args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        IS_MAC = System.getProperty("os.name").toLowerCase().startsWith("mac os x");
        if (IS_MAC) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);
        }

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(APP_NAME);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //Add content to the window.
                frame.add(new TextEditorPane());
                //Display the window.
                frame.setSize(new Dimension(800, 600));
                frame.setVisible(true);
            }
        });

    }
}
