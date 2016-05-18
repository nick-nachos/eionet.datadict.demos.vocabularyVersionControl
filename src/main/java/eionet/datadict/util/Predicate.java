package eionet.datadict.util;

public interface Predicate<T> {

    boolean isMatch(T obj);
    
}
