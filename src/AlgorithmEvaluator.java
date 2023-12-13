import java.util.*;

public class AlgorithmEvaluator {
    private final List<Process> _timeline;
    private final Set<Process> _distinctProcesses;

    AlgorithmEvaluator() {
        _timeline = new ArrayList<>();
        _distinctProcesses = new TreeSet<>(Comparator.comparingInt(Process::getPid));
    }

    public void addToTimeline(Process process) {
        _timeline.add(process);
        _distinctProcesses.add(process);
    }

    public double getAverageWaitingTime() {
        return _distinctProcesses.stream()
                .mapToInt(Process::getWaitingTime)
                .average()
                .orElse(0.0);
    }

    public double getAverageTurnaroundTime() {
        return _distinctProcesses.stream()
                .mapToInt(p -> p.getWaitingTime() + p.getBurstTime())
                .average()
                .orElse(0.0);
    }

    public List<String> getExecutionOrder() {
        return _timeline.stream()
                .map(Process::getName)
                .toList();
    }

    public List<Process> getProcesses() {
        return _distinctProcesses.stream()
                .toList();
    }
}
