import java.util.*;

public class PriorityScheduler extends Scheduler {

    public PriorityScheduler(List<Process> processes, int contextSwitchTime, int agingRate) {
        super(processes, contextSwitchTime, new PriorityQueue<Process>(new Comparator<Process>() {
            @Override
            public int compare(Process a, Process b) {
                final var priorityA = a.getPriority() - a.getArrivalTime() / agingRate;
                final var priorityB = b.getPriority() - b.getArrivalTime() / agingRate;
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
        return false;
    }

}
