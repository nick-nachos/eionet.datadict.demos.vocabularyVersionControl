package eionet.datadict.util;

import java.util.Iterator;

public class IterableUtils {
    
    public static <T> T firstOrNull(Iterable<T> source) {
        return nextOrNull(source.iterator());
    }
    
    public static <T> T nextOrNull(Iterator<T> it) {
        return it.hasNext() ? it.next() : null;
    }
    
    public static <T> T locate(Iterable<T> source, Predicate<T> p) {
        for (T item : source) {
            if (p.isMatch(item)) {
                return item;
            }
        }
        
        return null;
    }
    
    public static <T> int indexOf(Iterable<T> source, Predicate<T> p) {
        int index = 0;
        
        for (T item : source) {
            if (p.isMatch(item)) {
                return index;
            }
            
            index++;
        }
        
        return -1;
    }
    
}
