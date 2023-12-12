import java.util.*;

public class Main {
    public static void main(String[] args) {
        final var processes = new ArrayList<Process>();

        // Example from the book page 270
        processes.add(new Process(1, "P1", 0, 0, 8, 0));
        processes.add(new Process(2, "P2", 1, 0, 4, 0));
        processes.add(new Process(3, "P3", 2, 0, 9, 0));
        processes.add(new Process(4, "P4", 3, 0, 5, 0));

        final var sched = new SJFScheduler(processes, 0);

        sched.start();
    }
}