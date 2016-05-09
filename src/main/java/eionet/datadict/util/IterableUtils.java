package eionet.datadict.util;

import java.util.Iterator;

public class IterableUtils {
    
    public static <T> T nextOrNull(Iterator<T> it) {
        return it.hasNext() ? it.next() : null;
    }
    
}
