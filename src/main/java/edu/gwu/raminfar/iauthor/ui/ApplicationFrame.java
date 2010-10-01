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

/**
 * @author Amir Raminfar
 */
public class ApplicationFrame extends JFrame {
    public ApplicationFrame() throws HeadlessException {
        super(Main.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addTextEditor();
        addRightRail();

        setSize(new Dimension(800, 600));
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
                    @SuppressWarnings("unchecked")
                    Class<? extends AbstractTool> clazz = (Class<? extends AbstractTool>) Class.forName(c);
                    list.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
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
