package eionet.datadict.testutil;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.MethodUtils;

public class TestDecoratorBase<T> {

    public static class DecoratorInvokationException extends RuntimeException {

        public DecoratorInvokationException(Throwable cause) {
            super(cause);
        }
        
    }
    
    private final T targetInstance;
    private final List<TestDecoratorHandler> handlers;
    
    public TestDecoratorBase(T targetInstance, TestDecoratorHandler... handlers) {
        if (targetInstance == null) {
            throw new IllegalArgumentException();
        } 
        
        this.targetInstance = targetInstance;
        this.handlers = Arrays.asList(handlers);
    }
    
    public String serializeHandlerResults() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%n"));
        
        for (TestDecoratorHandler handler : this.handlers) {
            Map<String, TestDecoratorHandlerExecutionInfo> infos = handler.getExecutionInfos();
            
            for (String methodId : infos.keySet()) {
                sb.append(String.format("Method name: %s%n", methodId));
                sb.append(infos.get(methodId).serialize());
            }
        }
        
        return sb.toString();
    }
    
    protected <V> V execute(String methodId, String methodName, Class<V> resultType, Object... args) {
        preExecute(methodId, args);
        
        try {
            return (V) MethodUtils.invokeMethod(this.targetInstance, methodName, args);
        }
        catch (NoSuchMethodException ex) {
            throw new DecoratorInvokationException(ex);
        } 
        catch (IllegalAccessException ex) {
            throw new DecoratorInvokationException(ex);
        } 
        catch (InvocationTargetException ex) {
            throw new DecoratorInvokationException(ex);
        }
        finally {
            postExecute(methodId, args);
        }
    }
    
    private void preExecute(String methodId, Object[] args) {
        for (TestDecoratorHandler handler : this.handlers) {
            handler.onPreExecute(methodId, args);
        }
    }
    
    private void postExecute(String methodId, Object[] args) {
        for (TestDecoratorHandler handler : this.handlers) {
            handler.onPostExecute(methodId, args);
        }
    }
    
}
