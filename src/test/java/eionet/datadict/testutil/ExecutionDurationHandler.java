package eionet.datadict.testutil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.commons.lang3.tuple.MutablePair;

public class ExecutionDurationHandler implements TestDecoratorHandler {

    private final Map<String, ExecutionDurationInfo> infos;
    
    public ExecutionDurationHandler() {
        this.infos = new HashMap<>();
    }
    
    @Override
    public void onPreExecute(String methodId, Object[] methodArgs) {
        if (!this.infos.containsKey(methodId)) {
            this.infos.put(methodId, new ExecutionDurationInfo());
        }
        
        ExecutionDurationInfo info = this.infos.get(methodId);
        info.registerNew();
        info.setStart(System.currentTimeMillis());
    }

    @Override
    public void onPostExecute(String methodId, Object[] methodArgs) {
        this.infos.get(methodId).setEnd(System.currentTimeMillis());
    }

    @Override
    public Map<String, TestDecoratorHandlerExecutionInfo> getExecutionInfos() {
        return new HashMap<String, TestDecoratorHandlerExecutionInfo>(infos);
    }
    
    public static class ExecutionDurationInfo implements TestDecoratorHandlerExecutionInfo {
        
        private final LinkedList<MutablePair<Long, Long>> subInfos;
        
        public ExecutionDurationInfo() {
            this.subInfos = new LinkedList<>();
        }
        
        public void registerNew() {
            if (!this.subInfos.isEmpty()) {
                if (this.subInfos.getLast().left == null || this.subInfos.getLast().right == null) {
                    throw new IllegalStateException("Cannot register new execution duration info object after a semi complete one.");
                }
            }
            
            Long blank = null;
            this.subInfos.addLast(MutablePair.of(blank, blank));
        }
        
        public void setStart(Long millis) {
            MutablePair<Long, Long> current = this.getCurrent();
            
            if (current.getLeft() != null) {
                throw new IllegalStateException("Start time already set on registed execution duration info object.");
            }
            
            current.setLeft(millis);
        }
        
        public void setEnd(Long millis) {
            MutablePair<Long, Long> current = this.getCurrent();
            
            if (current.getRight()!= null) {
                throw new IllegalStateException("End time already set on registed execution duration info object.");
            }
            
            current.setRight(millis);
        }
        
        private MutablePair<Long, Long> getCurrent() {
            if (this.subInfos.isEmpty()) {
                throw new IllegalStateException("No registed execution duration info objects found.");
            }
            
            return this.subInfos.getLast();
        }

        @Override
        public String serialize() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Total number of executions: %d%n", this.subInfos.size()));
            long totalMillis = 0;
            
            for (MutablePair<Long, Long> subInfo : subInfos) {
                totalMillis += subInfo.right - subInfo.left;
            }
            
            sb.append(String.format("Total execution time: %.3f sec%n", totalMillis / 1000.0));
            
            if (subInfos.size() < 2) {
                return sb.toString();
            }
            
            int seqNum = 1;
            
            for (MutablePair<Long, Long> subInfo : subInfos) {
                sb.append(String.format(" - Execution #%d: %.3f sec%n", seqNum++, (subInfo.right - subInfo.left) / 1000.0));
            }
            
            return sb.toString();
        }
        
    }
    
}
