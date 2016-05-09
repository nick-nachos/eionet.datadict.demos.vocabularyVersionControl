package eionet.datadict.services.impl.data.util;

import java.util.Comparator;

public class ObjectByKeyComparator<K, V> implements Comparator<K> {

    private final ObjectKeyProvider<K, V> keyProvider;
    private final Comparator<V> keyComparator;
    
    public ObjectByKeyComparator(ObjectKeyProvider<K, V> keyProvider, Comparator<V> keyComparator) {
        this.keyProvider = keyProvider;
        this.keyComparator = keyComparator;
    }
    
    @Override
    public int compare(K o1, K o2) {
        V o1Key = this.keyProvider.getKey(o1);
        V o2Key = this.keyProvider.getKey(o2);
        
        return this.keyComparator.compare(o1Key, o2Key);
    }
    
}
