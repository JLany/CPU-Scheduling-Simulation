import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public abstract class Scheduler {

    // Think: Should these be defined here in the base?

    private boolean _run;
    private int _time;
    private int _contextSwitchTime;
    private Process _activeProcess;
    private PriorityQueue<Process> _readyQueue;
    private PriorityQueue<Process> _arrivalBus;

    public Scheduler(List<Process> processes, int contextSwitchTime,
                     Comparator<Process> priority) {
        _contextSwitchTime = contextSwitchTime;

        _readyQueue = new PriorityQueue<>(priority);
        _arrivalBus = new PriorityQueue<>(Comparator.comparingInt(Process::getArrivalTime));
    }

    public void start() {

        while (_run) {
            // This simulates a process arriving.
            populateQueue();

            // This simulates switching to another process.
            if (dispatch()) {
                _time += _contextSwitchTime;
            }

            cpuCycle();
        }

    }

    // This method counts as on time unit only.
    private void cpuCycle() {
        // _activeProcess.remainingTime--
        // ...
        // other adjustments needed for a process.

        _time++;
    }

    private void populateQueue() {
        // Look for a process with arrival time equal to current time.
        // If any, push it to the ready queue.

        // Might use a separate priority queue for arriving processes.
        // When this queue is empty, we terminate by setting _run to false.
    }

    // This method will vary across different scheduling algorithms.
    // Return true if dispatched. Else false, indicating that no switch was needed.
    protected abstract boolean dispatch();

    protected final void setActiveProcess(Process process) {
        _activeProcess = process;
    }

    protected final void pushToReadyQueue(Process process) {
        _readyQueue.add(process);
    }
}
