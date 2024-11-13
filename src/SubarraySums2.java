import java.io.*;
import java.util.*;

public class SubarraySums2 {
    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio();
        int arraySize = io.nextInt();
        int target = io.nextInt();
        int[] array = new int[arraySize];

        for (int x = 0; x < arraySize; x++) { array[x] = io.nextInt(); }

        long prefixSum = 0;
        long answer = 0;
        Map<Long, Integer> sums = new HashMap<>();
        sums.put((long)0, 1);

        for (int x : array) {
            prefixSum += x;
            /*
             * If there is a subarray with a prefix sum of prefix_sum - X,
             * we can exclude it from our current subarray to get the desired
             * sum. Thus, we can add the number of those subarrays to our
             * answer.
             */
            if (sums.containsKey(prefixSum -
                    target)) {  // check if it is in our map
                answer += sums.get(prefixSum - target);
            }

            // Increment the amount of prefix sums with a sum of prefix_sum
            if (!sums.containsKey(prefixSum)) {  // not yet in map, so add it
                sums.put(prefixSum, 1);
            } else {  // already in map, add one to it
                sums.put(prefixSum, sums.get(prefixSum) + 1);
            }
        }
        io.println(answer);
        io.close();
    }
    static class Kattio extends PrintWriter {
        private BufferedReader r;
        private StringTokenizer st;
        // standard input
        public Kattio() { this(System.in, System.out); }
        public Kattio(InputStream i, OutputStream o) {
            super(o);
            r = new BufferedReader(new InputStreamReader(i));
        }
        // USACO-style file input
        public Kattio(String problemName) throws IOException {
            super(problemName + ".out");
            r = new BufferedReader(new FileReader(problemName + ".in"));
        }
        // returns null if no more input
        public String next() {
            try {
                while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(r.readLine());
                return st.nextToken();
            } catch (Exception e) { }
            return null;
        }
        public int nextInt() { return Integer.parseInt(next()); }
        public double nextDouble() { return Double.parseDouble(next()); }
        public long nextLong() { return Long.parseLong(next()); }
    }
}