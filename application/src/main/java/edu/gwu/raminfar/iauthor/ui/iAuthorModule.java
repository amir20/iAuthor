package edu.gwu.raminfar.iauthor.ui;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import edu.gwu.raminfar.iauthor.core.AbstractTool;
import edu.gwu.raminfar.iauthor.core.EnabledModules;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.google.common.base.Throwables.propagate;

/**
 * @author amir.raminfar
 */
public class iAuthorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<List<AbstractTool>>() {
        }).annotatedWith(EnabledModules.class).toProvider(new Provider<List<AbstractTool>>() {
            @Override
            public List<AbstractTool> get() {
                Properties config = new Properties();
                try {
                    config.load(ApplicationFrame.class.getResourceAsStream("/config.properties"));
                } catch (IOException e) {
                    throw propagate(e);
                }
                String[] classes = config.getProperty("iauthor.tools").split(" *, *");
                List<AbstractTool> list = new ArrayList<AbstractTool>();
                for (String c : classes) {
                    try {
                        Class<?> clazz = Class.forName(c);
                        if (AbstractTool.class.isAssignableFrom(clazz)) {
                            try {
                                list.add(clazz.asSubclass(AbstractTool.class).newInstance());
                            } catch (InstantiationException e) {
                                throw propagate(e);
                            } catch (IllegalAccessException e) {
                                throw propagate(e);
                            }
                        } else {
                            throw new IllegalArgumentException(String.format("Class %s does not extend AbstractTool", clazz.getName()));
                        }

                    } catch (ClassNotFoundException e) {
                        throw propagate(e);
                    }
                }
                return list;
            }
        }).in(Singleton.class);
    }
}
