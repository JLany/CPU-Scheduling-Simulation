import java.util.*;

public class SJFScheduler extends Scheduler {

    public SJFScheduler(List<Process> processes, int contextSwitchTime) {
        super(processes, contextSwitchTime, new PriorityQueue<Process>(new Comparator<Process>() {
            @Override
            public int compare(Process a, Process b) {
                if (a.getRemainingTime() != b.getRemainingTime()) {
                    return a.getRemainingTime() - b.getRemainingTime();
                }
                if (a.getArrivalTime() != b.getArrivalTime()) {
                    return a.getArrivalTime() - b.getArrivalTime();
                }
                return 0;
            }
        }));
    }

    @Override
    protected boolean shouldDoContextSwitch() {
        return getActiveProcess().getRemainingTime() > peekReadyQueue().getRemainingTime();
    }

}
