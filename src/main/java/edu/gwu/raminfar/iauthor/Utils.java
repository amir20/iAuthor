package edu.gwu.raminfar.iauthor;

import edu.gwu.raminfar.iauthor.core.Word;
import edu.gwu.raminfar.iauthor.ui.ApplicationFrame;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * @author Amir Raminfar
 */
public final class Utils {
    private Utils() {
    }

    public static String join(Collection<Word> objects, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Word> iterator = objects.iterator(); iterator.hasNext();) {
            Word w = iterator.next();
            if (w != null) {
                sb.append(w.getText());
                if (iterator.hasNext()) {
                    sb.append(delimiter);
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
                    ApplicationFrame.logger.log(Level.WARNING, "Error when closing", e);
                }
            }
        }
    }
}
