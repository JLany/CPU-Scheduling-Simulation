import java.util.*;

public class SRTFScheduler extends Scheduler {

    public SRTFScheduler(List<Process> processes, int contextSwitchTime, int agingRate) {
        super(processes, contextSwitchTime, new PriorityQueue<Process>(new Comparator<Process>() {
            @Override
            public int compare(Process a, Process b) {
                final var priorityA = a.getRemainingTime() - a.getArrivalTime() / agingRate;
                final var priorityB = b.getRemainingTime() - b.getArrivalTime() / agingRate;
                if (priorityA != priorityB) {
                    return priorityA - priorityB;
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
