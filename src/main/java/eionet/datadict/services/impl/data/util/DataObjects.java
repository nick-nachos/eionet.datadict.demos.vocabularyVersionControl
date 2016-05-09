package eionet.datadict.services.impl.data.util;

import eionet.datadict.util.ComparableComparator;
import eionet.datadict.util.IterableUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DataObjects {

    public static <K, V extends Comparable<V>> void sort(List<K> objects, ObjectKeyProvider<K, V> keyProvider) {
        sort(objects, keyProvider, new ComparableComparator<V>());
    }
    
    public static <K, V> void sort(List<K> objects, ObjectKeyProvider<K, V> keyProvider, Comparator<V> keyComparator) {
        Collections.sort(objects, new ObjectByKeyComparator<>(keyProvider, keyComparator));
    }
    
    public static <K1, K2, V extends Comparable<V>> void linkParentChild(
            List<K1> parents, List<K2> children, ObjectJoinLinker<K1, K2> linker,
            ObjectKeyProvider<K1, V> parentKeyProvider, ObjectKeyProvider<K2, V> childKeyProvider) {
        linkParentChild(parents, children, linker, parentKeyProvider, childKeyProvider, new ComparableComparator<V>());
    }
    
    public static <K1, K2, V> void linkParentChild(List<K1> parents, List<K2> children, ObjectJoinLinker<K1, K2> linker,
            ObjectKeyProvider<K1, V> parentKeyProvider, ObjectKeyProvider<K2, V> childKeyProvider,
            Comparator<V> keyComparator) {
        sort(parents, parentKeyProvider, keyComparator);
        sort(children, childKeyProvider, keyComparator);
        
        Iterator<K1> leftIt = parents.iterator();
        Iterator<K2> rightIt = children.iterator();
        K1 leftObject = IterableUtils.nextOrNull(leftIt);
        K2 rightObject = IterableUtils.nextOrNull(rightIt);
        
        while (leftObject != null && rightObject != null) {
            V leftObjectKey = parentKeyProvider.getKey(leftObject);
            V rightObjectKey = childKeyProvider.getKey(rightObject);
            int cmpResult = keyComparator.compare(leftObjectKey, rightObjectKey);
            
            if (cmpResult == 0) {
                linker.link(leftObject, rightObject);
                rightObject = IterableUtils.nextOrNull(rightIt);
            }
            else if (cmpResult < 0) {
                leftObject = IterableUtils.nextOrNull(leftIt);
            }
            else {
                rightObject = IterableUtils.nextOrNull(rightIt);
            }
        }
    }
    
}
