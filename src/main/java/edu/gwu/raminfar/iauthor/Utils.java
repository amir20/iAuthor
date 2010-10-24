package edu.gwu.raminfar.iauthor;

import edu.gwu.raminfar.iauthor.core.Word;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

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
}
