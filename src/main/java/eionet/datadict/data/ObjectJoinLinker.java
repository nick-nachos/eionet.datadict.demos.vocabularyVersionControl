package eionet.datadict.data;

public interface ObjectJoinLinker<T1, T2> {

    void link(T1 left, T2 right);
    
}
