import java.util.*;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Shortest-Job-First Scheduler");
//        final var sjfProcesses = new ArrayList<Process>();
//
//        // Example from the book page 270
//        sjfProcesses.add(new Process(1, "P1", 0, 0, 8, 0));
//        sjfProcesses.add(new Process(2, "P2", 1, 0, 4, 0));
//        sjfProcesses.add(new Process(3, "P3", 2, 0, 9, 0));
//        sjfProcesses.add(new Process(4, "P4", 3, 0, 5, 0));
//
//        AlgorithmEvaluator sjfEvaluator = new AlgorithmEvaluator();
//        final var sjfSched = new SJFScheduler(sjfProcesses, 0, sjfEvaluator);
//        sjfSched.start();
//
//        System.out.println("Shortest-Remaining-Time-First Scheduler");
//        final var srtfProcesses = new ArrayList<Process>();
//
//        // Example from the book page 270
//        srtfProcesses.add(new Process(1, "P1", 0, 0, 8, 0));
//        srtfProcesses.add(new Process(2, "P2", 1, 0, 4, 0));
//        srtfProcesses.add(new Process(3, "P3", 2, 0, 9, 0));
//        srtfProcesses.add(new Process(4, "P4", 3, 0, 5, 0));
//
//        AlgorithmEvaluator srtfEvaluator = new AlgorithmEvaluator();
//        final var srtfSched = new SRTFScheduler(srtfProcesses, 0, 10, srtfEvaluator);
//        srtfSched.start();
//
        System.out.println("Priority Scheduler");
        final var priorityProcesses = new ArrayList<Process>();

        // Example from the book page 270
        priorityProcesses.add(new Process(1, "P1", 0, 3, 10, 0));
        priorityProcesses.add(new Process(2, "P2", 0, 1, 1, 0));
        priorityProcesses.add(new Process(3, "P3", 0, 4, 2, 0));
        priorityProcesses.add(new Process(4, "P4", 0, 5, 1, 0));
        priorityProcesses.add(new Process(5, "P5", 0, 2, 5, 0));

        AlgorithmEvaluator priorityEvaluator = new AlgorithmEvaluator();
        final var prioritySched = new PriorityScheduler(priorityProcesses, 0, 10, priorityEvaluator);
        prioritySched.start();

        printResults(priorityEvaluator);


        System.out.println("AG Scheduler");
        final var agProcesses = new ArrayList<Process>();

        // Example from the assignment.
        agProcesses.add(new Process(1, "P1", 0, 4, 17, 4));
        agProcesses.add(new Process(2, "P2", 3, 9, 6, 4));
        agProcesses.add(new Process(3, "P3", 4, 3, 10, 4));
        agProcesses.add(new Process(4, "P4", 29, 8, 4, 4));

        agProcesses.get(0).setAgFactor(20);
        agProcesses.get(1).setAgFactor(17);
        agProcesses.get(2).setAgFactor(16);
        agProcesses.get(3).setAgFactor(43);

        AlgorithmEvaluator agEvaluator = new AlgorithmEvaluator();
        final var agSched = new AgScheduler(agProcesses, 0, agEvaluator);
        agSched.start();


        // TODO - Use evaluators to display results.
        printResults(agEvaluator);
    }

    private static void printResults(AlgorithmEvaluator evaluator) {
        System.out.println("Execution Order:");
        for (String pname : evaluator.getExecutionOrder()) {
            System.out.printf("%s ", pname);
        }

        System.out.println();

        List<Process> processes = evaluator.getProcesses();
        System.out.println("\tProcess\t\tWaiting Time");
        for (Process p : processes) {
            System.out.printf("\t%s\t\t\t%d\n", p.getName(), p.getWaitingTime());
        }
        System.out.println();
        System.out.printf("Average: %d s", (int)evaluator.getAverageWaitingTime());

        System.out.println();
        System.out.println();

        System.out.println("\tProcess\t\tTurnaround Time");
        for (Process p : processes) {
            System.out.printf("\t%s\t\t\t%d\n", p.getName(), p.getWaitingTime() + p.getBurstTime());
        }
        System.out.println();
        System.out.printf("Average: %d s", (int)evaluator.getAverageTurnaroundTime());

        System.out.println();
    }
}