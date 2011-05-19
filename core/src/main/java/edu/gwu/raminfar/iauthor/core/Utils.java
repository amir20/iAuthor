package edu.gwu.raminfar.iauthor.core;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amir Raminfar
 */
public final class Utils {
    public static final Logger logger = Logger.getLogger(Utils.class.getName());

    private Utils() {
    }

    public static String join(Collection<Word> objects, String delimiter) {
        StringBuilder sb = new StringBuilder();
        if (objects != null) {
            for (Iterator<Word> iterator = objects.iterator(); iterator.hasNext(); ) {
                Word w = iterator.next();
                if (w != null) {
                    sb.append(w.getText());
                    if (iterator.hasNext()) {
                        sb.append(delimiter);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Error when closing", e);
                }
            }
        }
    }
}
