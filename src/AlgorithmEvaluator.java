import java.util.*;

public class AlgorithmEvaluator {
    private final List<Process> _timeline;

    AlgorithmEvaluator() {
        _timeline = new ArrayList<>();
    }

    public void addToTimeline(Process process) {
        _timeline.add(process);
    }

    public double getAverageWaitingTime() {
        return _timeline.stream()
                .distinct()
                .mapToInt(Process::getWaitingTime)
                .average()
                .orElse(0.0);
    }

    public double getAverageTurnaroundTime() {
        return _timeline.stream()
                .distinct()
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
        return _timeline.stream()
                .distinct()
                .toList();
    }
}
