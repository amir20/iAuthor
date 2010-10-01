package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.Main;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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
    private static final Logger logger = Logger.getLogger(ApplicationFrame.class.getName());
    public ApplicationFrame() throws HeadlessException {
        super(Main.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addTextEditor();
        addRightRail();

        setSize(new Dimension(960, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTextEditor() {
        add(new TextEditorPane(), BorderLayout.CENTER);
    }

    private void addRightRail() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        List<Class<? extends AbstractTool>> tools = getTools();
        for (Class<? extends AbstractTool> c : tools) {
            try {
                AbstractTool tool = c.newInstance();
                panel.add(tool);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        add(panel, BorderLayout.EAST);
    }

    private List<Class<? extends AbstractTool>> getTools() {
        try {
            Properties config = new Properties();
            config.load(getClass().getResourceAsStream("/config.properties"));
            List<Class<? extends AbstractTool>> list = new ArrayList<Class<? extends AbstractTool>>();
            String[] classes = config.getProperty("iauthor.tools").split(" *, *");
            for (String c : classes) {
                try {
                    Class<?> clazz = Class.forName(c);
                    if (AbstractTool.class.isAssignableFrom(clazz)) {
                        Class<? extends AbstractTool> tool = clazz.asSubclass(AbstractTool.class);
                        list.add(tool);
                    }else{
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
