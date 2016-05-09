package eionet.datadict.services.impl.data.util;

public interface ObjectKeyProvider<K, V> {

    V getKey(K obj);
    
}
