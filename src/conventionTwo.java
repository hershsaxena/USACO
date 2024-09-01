import java.io.*;
import java.util.*;

public class conventionTwo {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numCows = Integer.parseInt(st.nextToken());
        Cow[] cowsSortedTime = new Cow[numCows];
        int cowIndex = 1;
        Queue<Cow> cows = new PriorityQueue<>(numCows, Comparator.comparingInt(a -> a.seniority));
        for (int i = 0; i < numCows; i++) {
            st = new StringTokenizer(br.readLine());
            cowsSortedTime[i] = new Cow(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(cowsSortedTime, Comparator.comparingInt(a -> a.arrival));
        cows.add(cowsSortedTime[0]);
        int time = cowsSortedTime[0].arrival;
        int max = 0;

        while (cowIndex < numCows || !cows.isEmpty()) {
            Cow cur = cows.remove();
            time = Math.max(time, cur.arrival);
            max = Math.max(max, time - cur.arrival);
            time += cur.eatingTime;
            if (cows.isEmpty() && cowIndex < numCows) {
                int curTime = cowsSortedTime[cowIndex].arrival;

                do {
                    cows.add(cowsSortedTime[cowIndex]);
                    cowIndex++;
                } while (cowIndex < numCows && cowsSortedTime[cowIndex].arrival == curTime);
            }
            while (cowIndex < numCows && cowsSortedTime[cowIndex].arrival <= time) {
                cows.add(cowsSortedTime[cowIndex]);
                cowIndex++;
            }
        }

        pw.println(max);
        pw.close();
    }

    static class Cow {
        public int seniority;
        public int arrival;
        public int eatingTime;

        public Cow(int seniority, int arrival, int eatingTime) {
            this.seniority = seniority;
            this.arrival = arrival;
            this.eatingTime = eatingTime;
        }
    }
}
