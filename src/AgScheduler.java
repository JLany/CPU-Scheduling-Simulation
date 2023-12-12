import java.util.*;

public class AgScheduler extends Scheduler {
    private final Random _random = new Random();

    // Internal queue for local decision-making.
    private final PriorityQueue<Process> _agFactorQueue;

    public AgScheduler(List<Process> processes, int contextSwitchTime) {
        super(processes, contextSwitchTime);

        _agFactorQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getAgFactor));
        initAg(processes);
    }

    private void initAg(List<Process> processes) {
        for (Process p : processes) {
            p.setAgFactor(calculateAg(p));
        }

        _agFactorQueue.addAll(processes);
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
    protected boolean shouldDoContextSwitch() {
        Process activeProcess = super.getActiveProcess();

        if (!canPreemptProcess(super.getActiveProcess())) {
            return false;
        }

        // Three cases:

        // 1: Consumed all quantum.
        if (consumedQuantum(activeProcess)) {
            int avgQuantum = calculateAvgQuantum();
            increaseProcessQuantum(activeProcess, avgQuantum);

            return true;
        }

        // 2: Preempted by another higher priority process.
        if (checkForHigherPriority(activeProcess)) {
            int remainingQuantum = activeProcess.getQuantum() - activeProcess.getCurrentBurstDuration();
            increaseProcessQuantum(activeProcess, remainingQuantum);

            return true;
        }

        // 3: Finished burst.
        if (finishedBurst(activeProcess)) {
            activeProcess.setQuantum(0);
            return true;
        }

        return false;
    }

    private boolean finishedBurst(Process process) {
        return process.getBurstTime() <= 0;
    }

    private boolean checkForHigherPriority(Process process) {
        Optional<Process> processOptional = _agFactorQueue.stream()
                .filter(Process::isArrived)
                .findFirst();

        Process candidate = processOptional.orElse(null);

        return candidate != null && candidate.getAgFactor() < process.getAgFactor();
    }

    private boolean consumedQuantum(Process process) {
        return process.getCurrentBurstDuration() >= process.getQuantum();
    }

    private void increaseProcessQuantum(Process process, int additional) {
        process.setQuantum(process.getQuantum() + additional);
    }

    private int calculateAvgQuantum() {
        long processCount = super.getReadyQueueStream().count();

        if (processCount < 1) {
            return 0;
        }

        return (int)Math.ceil(
                super.getReadyQueueStream()
                .mapToInt(Process::getQuantum)
                .sum()
                / (double)processCount
                / 10.0
        );
    }

    private boolean canPreemptProcess(Process activeProcess) {
        int activeFor = activeProcess.getCurrentBurstDuration();
        int quantum = activeProcess.getQuantum();

        return (activeFor >= Math.ceil(quantum / 2.0));
    }
}
