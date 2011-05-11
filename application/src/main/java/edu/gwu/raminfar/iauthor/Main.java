package edu.gwu.raminfar.iauthor;

import java.io.IOException;
import edu.gwu.raminfar.iauthor.ui.ApplicationFrame;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class Main {
    public final static boolean IS_MAC = System.getProperty("os.name").toLowerCase().startsWith("mac os x");
    public final static String APP_NAME = "iAuthor";

    public static void main(String... args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        if (IS_MAC) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);
        }

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationFrame.logger.info("Starting application frame...");
                ApplicationFrame frame = new ApplicationFrame();
                if(!IS_MAC) {
                    try {
                        frame.setIconImage(ImageIO.read(getClass().getResource("/images/icon.ico")));
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
