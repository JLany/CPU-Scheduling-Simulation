import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shortest-Job-First Scheduler");
        final var sjfProcesses = new ArrayList<Process>();

        // Example from the book page 270
        sjfProcesses.add(new Process(1, "P1", 0, 0, 8, 0));
        sjfProcesses.add(new Process(2, "P2", 1, 0, 4, 0));
        sjfProcesses.add(new Process(3, "P3", 2, 0, 9, 0));
        sjfProcesses.add(new Process(4, "P4", 3, 0, 5, 0));

        final var sjfSched = new SJFScheduler(sjfProcesses, 0);
        sjfSched.start();

        System.out.println("Shortest-Remaining-Time-First Scheduler");
        final var srtfProcesses = new ArrayList<Process>();

        // Example from the book page 270
        srtfProcesses.add(new Process(1, "P1", 0, 0, 8, 0));
        srtfProcesses.add(new Process(2, "P2", 1, 0, 4, 0));
        srtfProcesses.add(new Process(3, "P3", 2, 0, 9, 0));
        srtfProcesses.add(new Process(4, "P4", 3, 0, 5, 0));

        final var srtfSched = new SJFScheduler(srtfProcesses, 0);
        srtfSched.start();

    }
}