package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.Main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amir Raminfar
 */
public class ApplicationFrame extends JFrame {
    public static final Logger logger = Logger.getLogger(ApplicationFrame.class.getName());
    private final TextEditorPane editor = new TextEditorPane();

    public ApplicationFrame() throws HeadlessException {
        super(Main.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(editor, BorderLayout.CENTER);
        addRightRail();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (d.width * .75), (int) (d.height * .75)));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addRightRail() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.EAST);

        List<Class<? extends AbstractTool>> classes = getTools();
        List<AbstractTool> tools = new ArrayList<AbstractTool>();

        for (Class<? extends AbstractTool> c : classes) {
            try {
                AbstractTool tool = c.newInstance();
                panel.add(tool);
                tools.add(tool);
                tool.setTextPane(editor.getTextPane());
            } catch (InstantiationException e) {
                logger.log(Level.SEVERE, "Initialization Exception", e);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Illegal Access Exception", e);
            }
        }
        editor.setTools(tools);        
    }

    private static List<Class<? extends AbstractTool>> getTools() {
        try {
            Properties config = new Properties();
            config.load(ApplicationFrame.class.getResourceAsStream("/config.properties"));
            List<Class<? extends AbstractTool>> list = new ArrayList<Class<? extends AbstractTool>>();
            String[] classes = config.getProperty("iauthor.tools").split(" *, *");
            for (String c : classes) {
                try {
                    Class<?> clazz = Class.forName(c);
                    if (AbstractTool.class.isAssignableFrom(clazz)) {
                        Class<? extends AbstractTool> tool = clazz.asSubclass(AbstractTool.class);
                        list.add(tool);
                    } else {
                        // print error or throw run time exception
                        logger.log(Level.WARNING, "Class {0} does not extend AbstractTool", clazz.getName());
                    }

                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Class not found", e);
                    // skipping to next class
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
