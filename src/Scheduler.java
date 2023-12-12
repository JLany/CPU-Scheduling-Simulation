import java.util.*;
import java.util.stream.Stream;

// TODO - Think: How to incorporate gathering statistics in the process of this class?

public abstract class Scheduler {

    // Think: Should these be defined here in the base?

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

        while (true) {
            // This simulates a process arriving.
            populateQueue();

            // This simulates switching to another process.
            if (_activeProcess == null || (!_readyQueue.isEmpty() && shouldDoContextSwitch())) {
                doContextSwitch();
            }

            // This simulates the active process executing.
            advanceCpuCycle();

            if (_activeProcess == null && _readyQueue.isEmpty() && _arrivalBus.isEmpty()) {
                break;
            }
        }

    }

    private void populateQueue() {
        // Look for a process with arrival time less than or equal to current time.
        // If any, push it to the ready queue.
        while (!_arrivalBus.isEmpty() && _arrivalBus.peek().getArrivalTime() <= _time) {
            Process toAdd = _arrivalBus.poll();
            toAdd.setArrived(true);
            _readyQueue.add(toAdd);
        }
    }

    // Selects the next process to be scheduled. May be overridden by child class.
    protected Process selectNextProcess() {
        // Note that poll() throws an exception if the queue is empty.
        // That's why we do this check.
        if (!_readyQueue.isEmpty()) {
            // TODO: Find better way to debug
            System.out.printf("[%d] Selected process <%s>%n", _time, _readyQueue.peek().getName());

            return _readyQueue.poll();
        } else {
            return null;
        }
    }

    // This method will vary across different scheduling algorithms.
    // Return true if dispatched. Else false, indicating that no switch was needed.
    protected abstract boolean shouldDoContextSwitch();

    // Performs the actual context switch.
    private void doContextSwitch() {
        Process old = _activeProcess;

        _activeProcess = selectNextProcess();
        if (_activeProcess != null) {
            _activeProcess.resetCurrentBurstDuration();
            _activeProcess.setCurrentBurstStart(_time);
            _activeProcess.setWaitingTime(_activeProcess.getWaitingTime() + (_time - _activeProcess.getLastRunTime()));
        }

        if (old != null) {
            old.setLastRunTime(_time);
            _readyQueue.add(old);
            _time += _contextSwitchTime;
        }
    }

    // This method counts as one time unit only.
    private void advanceCpuCycle() {
        if (_activeProcess != null) {
            // TODO - Think: What else needs adjustment during execution of a process?
            // _activeProcess.remainingTime--
            // ...
            // other adjustments needed for a process.
            _activeProcess.incrementCurrentBurstDuration();
            _activeProcess.decrementRemainingTime();

            if (_activeProcess.getRemainingTime() <= 0) {
                killProcess(_activeProcess);
                _activeProcess = null;
            }
        }

        _time++;
    }

    protected final Process getActiveProcess() {
        return _activeProcess;
    }

    protected final Process peekReadyQueue() {
        return _readyQueue.peek();
    }

    protected final void killProcess(Process process) {
        process.setDead(true);
        _killedProcesses.add(process);
    }

    protected final Stream<Process> getReadyQueueStream() {
        return _readyQueue.stream();
    }
}
