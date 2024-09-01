import java.io.*;
import java.util.StringTokenizer;

public class conventionTwo {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numCows = Integer.parseInt(st.nextToken());

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
