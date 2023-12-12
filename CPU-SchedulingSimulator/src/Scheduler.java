import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Scheduler {

    // Think: Should these be defined here in the base?

    private boolean _run;
    private int _time;
    private final int _contextSwitchTime;
    private Process _activeProcess;
    private PriorityQueue<Process> _readyQueue;
    private PriorityQueue<Process> _arrivalBus;
    private List<Process> _killedProcesses;

    public Scheduler(List<Process> processes, int contextSwitchTime, Comparator<Process> priority) {
        _arrivalBus = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
        _arrivalBus.addAll(processes);

        // The ready queue's priority is determined by the implementors of Scheduler.
        _readyQueue = new PriorityQueue<>(priority);

        _contextSwitchTime = contextSwitchTime;
        _killedProcesses = new ArrayList<>();
    }

    public final void start() {

        // This loop's iteration counts as one unit of time.
        // Why?
        // Because some algorithms are preemptive, and thus a process
        // could be preempted after each cycle of cpu.

        while (_run) {
            // This simulates a process arriving.
            populateQueue();

            // This simulates switching to another process.
            if (dispatch()) {
                dispatchInternal();
            }

            cpuCycle();
            _time++;
        }

    }

    // This method counts as on time unit only.
    private void cpuCycle() {
        // _activeProcess.remainingTime--
        // ...
        // other adjustments needed for a process.
    }

    private void populateQueue() {
        // When arrival bus queue is empty, we terminate by setting _run to false.
        if (_arrivalBus.size() < 1) {
            _run = false;
            return;
        }

        // Look for a process with arrival time equal to current time.
        // If any, push it to the ready queue.
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

        old.setLastRunTime(_time);
        _readyQueue.add(old);

        _time += _contextSwitchTime;
    }

    protected final int getTime() {
        return _time;
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
}
