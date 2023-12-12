import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> list = new ArrayList<>();

        Process p1 = new Process(1, "h1", 0, 1, 5, 1);
        Process p2 = new Process(2, "h2", 0, 1, 5, 1);
        Process p3 = new Process(3, "h3", 0, 1, 5, 1);
        Process p4 = new Process(4, "h4", 0, 1, 5, 1);
        Process p5 = new Process(5, "h5", 0, 1, 5, 1);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        List<Process> anotherList = new ArrayList<>();
        anotherList.add(p1);
        anotherList.add(p2);
        anotherList.add(p3);
        anotherList.add(p4);
        anotherList.add(p5);
        Collections.copy(anotherList, list);



        for (Process p : list) {
            System.out.println(p.getName());
        }

        System.out.println();

        anotherList.get(3).setName("CORRUPTED");

        for (Process p : list) {
            System.out.println(p.getName());
        }
    }
}