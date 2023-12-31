import java.util.*;
import java.util.stream.Stream;

public abstract class Scheduler {
    private int _time;
    private final int _contextSwitchTime;
    private Process _activeProcess;
    private final Queue<Process> _readyQueue;
    private final PriorityQueue<Process> _arrivalBus;
    private final List<Process> _killedProcesses;
    private final AlgorithmEvaluator _evaluator;

    // Initializes the ready queue as a custom queue chosen by implementors.
    public Scheduler(List<Process> processes, int contextSwitchTime, Queue<Process> queueImpl, AlgorithmEvaluator evaluator) {
        _arrivalBus = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
        _arrivalBus.addAll(processes);

        // The ready queue's type and priority are determined by the implementors of Scheduler.
        _readyQueue = queueImpl;

        _contextSwitchTime = contextSwitchTime;
        _killedProcesses = new ArrayList<>();

        _evaluator = evaluator;
    }

    // Initializes the ready queue as a FIFO.
    public Scheduler(List<Process> processes, int contextSwitchTime, AlgorithmEvaluator evaluator) {
        this(processes, contextSwitchTime, new LinkedList<>(), evaluator);
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
            toAdd.setWaitingTime(-_time);

            _readyQueue.add(toAdd);
        }
    }

    // Selects the next process to be scheduled. May be overridden by child class.
    protected Process selectNextProcess() {
        // Note that poll() throws an exception if the queue is empty.
        // That's why we do this check.
        if (!_readyQueue.isEmpty()) {
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

            _evaluator.addToTimeline(_activeProcess);
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

    protected final boolean removeFromReadyQueue(Process process) {
        return _readyQueue.remove(process);
    }

//     This method is temporary, just for debugging purposes.
//    protected final int getTime() {
//        return _time;
//    }
}
