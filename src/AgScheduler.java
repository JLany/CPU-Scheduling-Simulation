import java.util.*;
import java.util.stream.Stream;

public class AgScheduler extends Scheduler {
    private final Random _random = new Random();

    // Internal queue for local decision-making.
    private final PriorityQueue<Process> _agFactorQueue;

    public AgScheduler(List<Process> processes, int contextSwitchTime, AlgorithmEvaluator evaluator) {
        super(processes, contextSwitchTime, evaluator);

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
        int ag = process.getArrivalTime() + process.getBurstTime();

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

        if (!canPreemptProcess(activeProcess)) {
            return false;
        }

        // Three cases:

        // 1: Consumed all quantum.
        if (consumedQuantum(activeProcess)) {
            int avgQuantum = calculateAvgQuantum();
            increaseProcessQuantum(activeProcess, avgQuantum);

            super.pushReadyQueue(activeProcess);

            return true;
        }

        // 2: Preempted by another higher priority process.
        if (checkForHigherPriority(activeProcess).isPresent()) {
            int remainingQuantum = activeProcess.getQuantum() - activeProcess.getCurrentBurstDuration();
            increaseProcessQuantum(activeProcess, remainingQuantum);

            super.pushReadyQueue(activeProcess);

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

    private Optional<Process> checkForHigherPriority(Process process) {
        Stream<Process> availableProcesses = getAvailableProcesses();

        if (process == null) {
            return availableProcesses
                    .findFirst();
        }

        Optional<Process> processOptional = availableProcesses
                .filter(p -> p.getPid() != process.getPid())
                .findFirst();

        Process candidate = processOptional.orElse(null);

        if (candidate != null && candidate.getAgFactor() < process.getAgFactor()) {
            return processOptional;
        }

        return Optional.empty();
    }

    private boolean consumedQuantum(Process process) {
        return process.getCurrentBurstDuration() >= process.getQuantum();
    }

    private void increaseProcessQuantum(Process process, int additional) {
        process.setQuantum(process.getQuantum() + additional);
    }

    private int calculateAvgQuantum() {
        long processCount = getAvailableProcesses().count();

        if (processCount < 1) {
            return 0;
        }

        return (int)Math.ceil(
                getAvailableProcesses()
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

    private Stream<Process> getAvailableProcesses() {
        return _agFactorQueue.stream()
            .filter(Process::isArrived)
            .filter(p -> !p.isDead());
    }

    @Override
    protected Process selectNextProcess() {
        var processOptional = checkForHigherPriority(super.getActiveProcess());

        if (processOptional.isPresent())
            System.out.printf("[%d] Selected process <%s>%n", super.getTime(), processOptional.get().getName());

        return processOptional.orElseGet(super::selectNextProcess);
    }

    @Override
    protected void pushReadyQueue(Process process) {
        // Do nothing.
        // This is to suppress parent's implementation.
    }
}
