import java.util.*;
import java.io.*;

public class LemonadeLine {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lemonade.in"));
        PrintWriter pw = new PrintWriter("lemonade.out");

        int n = Integer.parseInt(br.readLine());
        long[] w = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            w[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(w);

        int lower = 0;
        int upper = n - 1;

        while (lower <= upper) {
            long temp = w[lower];
            w[lower] = w[upper];
            w[upper] = temp;

            lower++;
            upper--;
        }

        int i = 0;
        while(w[i] >= i) {
            i++;
        }

        pw.println(i);
        pw.close();
    }
}
