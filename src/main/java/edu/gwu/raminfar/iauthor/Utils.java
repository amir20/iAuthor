package edu.gwu.raminfar.iauthor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Amir Raminfar
 */
public final class Utils {
    private Utils() {
    }

    public static String join(Collection<?> objects, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<?> iterator = objects.iterator(); iterator.hasNext();) {
            Object o = iterator.next();
            if (o != null) {
                sb.append(o.toString());
                if (iterator.hasNext()) {
                    sb.append(delimiter);
                }
            }
        }
        return sb.toString();
    }

    public static String join(Object[] objects, String delimiter) {
        return join(Arrays.asList(objects), delimiter);
    }
}
