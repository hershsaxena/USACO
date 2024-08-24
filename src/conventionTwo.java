import java.io.*;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Arrays;

public class conventionTwo {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numCows = Integer.parseInt(st.nextToken());

        PriorityQueue<Cow> cows = new PriorityQueue<>(numCows, Comparator.comparingInt((Cow a) -> a.seniority));
        Cow[] cowsTime = new Cow[numCows];
        for (int i = 0; i < numCows; i++) {
            st = new StringTokenizer(br.readLine());
            cowsTime[i] = new Cow(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        Arrays.sort(cowsTime, Comparator.comparingInt((Cow a) -> a.arrival));
        cows.add(cowsTime[0]);
        int cowIndex = 0;
        int longestWait = 0;

        while(cowIndex < numCows) {
            Cow cur = cows.poll();
            while(cowsTime[cowIndex].arrival < cur.arrival + cur.eatingTime) {
                cows.add(cowsTime[cowIndex]);
                cowIndex++;
            }


        }

        pw.println(longestWait);
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
