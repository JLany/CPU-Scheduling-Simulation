import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AgScheduler extends Scheduler {
    private final Random _random = new Random();

    public AgScheduler(List<Process> processes, int contextSwitchTime, Comparator<Process> priority) {
        super(processes, contextSwitchTime, priority);

        initAg(processes);
    }

    private void initAg(List<Process> processes) {
        for (Process p : processes) {
            p.setAgFactor(calculateAg(p));
        }
    }

    private int calculateAg(Process process) {
        int rf = _random.nextInt(21);
        int ag = getActiveProcess().getArrivalTime() + process.getBurstTime();

        if (rf == 10) {
            ag += process.getPriority();
        } else if (rf < 10) {
            ag += rf;
        } else {
            ag += 10;
        }

        return ag;
    }

    @Override
    protected boolean dispatch() {
        if (!checkPreemption(super.getActiveProcess())) {
            return false;
        }

        // Three cases:

        // 1: Consumed all quantum.

        // 2: Preempted by another higher priority process.

        // 3: Finished burst.

        return false;
    }

    private boolean checkPreemption(Process activeProcess) {
        int activeFor = super.getTime() - activeProcess.getLastRunTime();
        int quantum = activeProcess.getQuantum();

        return (activeFor >= Math.ceil(quantum / 2.0));
    }
}
