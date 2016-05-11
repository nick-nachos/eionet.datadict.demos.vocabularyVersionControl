package eionet.datadict.data;

public interface ObjectKeyProvider<K, V> {

    V getKey(K obj);
    
}
