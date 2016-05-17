package eionet.datadict.testutil;

public abstract class ExecutionDurationPrinter<T> {

    public T run() throws RuntimeException {
        long start = System.currentTimeMillis();
        T result;
        
        try {
            result = this.execute();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        long end = System.currentTimeMillis();
        double duration = (end - start) / 1000.0;
        
        System.out.format("Operation '%s' complete in %.3f sec%n", this.getOperationDescription(), duration);
        
        return result;
    }
    
    protected abstract String getOperationDescription();
    
    protected abstract T execute() throws Exception;
    
}
