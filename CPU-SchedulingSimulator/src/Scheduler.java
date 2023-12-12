import java.util.*;
import java.util.stream.Stream;

// TODO - Think: How to incorporate gathering statistics in the process of this class?

public abstract class Scheduler {

    // Think: Should these be defined here in the base?

    private boolean _run;
    private int _time;
    private final int _contextSwitchTime;
    private Process _activeProcess;
    private final Queue<Process> _readyQueue;
    private final PriorityQueue<Process> _arrivalBus;
    private final List<Process> _killedProcesses;

    // Initializes the ready queue as a custom queue chosen by implementors.
    public Scheduler(List<Process> processes, int contextSwitchTime, Queue<Process> queueImpl) {
        _arrivalBus = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
        _arrivalBus.addAll(processes);

        // The ready queue's type and priority are determined by the implementors of Scheduler.
        _readyQueue = queueImpl;

        _contextSwitchTime = contextSwitchTime;
        _killedProcesses = new ArrayList<>();
    }

    // Initializes the ready queue as a FIFO.
    public Scheduler(List<Process> processes, int contextSwitchTime) {
        this(processes, contextSwitchTime, new LinkedList<>());
    }

    public final void start() {

        // This loop's iteration performs either a context switch + cpu cycle, or just a cpu cycle.
        // Why single cpu cycle?
        // Because some algorithms are preemptive, and thus a process
        // could be preempted after each cycle of cpu.

        while (_run) {
            // This simulates a process arriving.
            populateQueue();

            // This simulates switching to another process.
            if (dispatch()) {
                dispatchInternal();
            }

            // This simulates the active process executing.
            cpuCycle();
        }

    }

    private void populateQueue() {
        if (_arrivalBus.size() < 1) {
            return;
        }

        // Look for a process with arrival time equal to current time.
        // If any, push it to the ready queue.
        // Note: The check above guarantees that the queue is not empty.
        while (_arrivalBus.peek().getArrivalTime() == _time) {
            _readyQueue.add(_arrivalBus.poll());
        }
    }

    // This method will vary across different scheduling algorithms.
    // Return true if dispatched. Else false, indicating that no switch was needed.
    protected abstract boolean dispatch();

    // Performs the actual context switch.
    private void dispatchInternal() {
        Process old = _activeProcess;

        _activeProcess = _readyQueue.poll();

        // No processes in the ready queue.
        if (_activeProcess == null) {
            return;
        }

        _activeProcess.resetCurrentBurstDuration();
        _activeProcess.setCurrentBurstStart(_time);
        _activeProcess.setWaitingTime(_activeProcess.getWaitingTime() + (_time - _activeProcess.getLastRunTime()));

        old.setLastRunTime(_time);

        if (old.getBurstTime() <= 0) {
            old.setDead(true);
            _killedProcesses.add(old);
        } else {
            _readyQueue.add(old);
        }

        _time += _contextSwitchTime;
    }

    // This method counts as one time unit only.
    private void cpuCycle() {
        if (_activeProcess == null) {
            return;
        }

        // TODO - Think: What else needs adjustment during execution of a process?
        // _activeProcess.remainingTime--
        // ...
        // other adjustments needed for a process.
        _activeProcess.incrementCurrentBurstDuration();
        _activeProcess.decrementBurstTime();

        _time++;
    }

    protected final Process getActiveProcess() {
        return _activeProcess;
    }

    protected final Process peekReadyQueue() {
        return _readyQueue.peek();
    }

    protected final void addToKilled(Process process) {
        _killedProcesses.add(process);
    }

    protected final Stream<Process> getReadyQueueStream() {
        return _readyQueue.stream();
    }
}
