package eionet.datadict.testutil;

import java.util.Map;

public interface TestDecoratorHandler {

    Map<String, TestDecoratorHandlerExecutionInfo> getExecutionInfos();
    
    void onPreExecute(String methodId, Object[] methodArgs);
    
    void onPostExecute(String methodId, Object[] methodArgs);
    
}
