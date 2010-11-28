package edu.gwu.raminfar;

import edu.gwu.raminfar.iauthor.ui.ApplicationFrame;

import javax.swing.*;

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
                ApplicationFrame.logger.info("Starting application frame...");
                new ApplicationFrame();
            }
        });
    }
}
