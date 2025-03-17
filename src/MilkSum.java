import java.util.*;
import java.io.*;

public class MilkSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int q = Integer.parseInt(br.readLine());
        long originalSum = 0;

        int[] sorted = Arrays.stream(a).sorted().toArray();

        for (int i = 0; i < n; i++) {
            originalSum += (long) sorted[i] * (i + 1);
        }

        long[] sums = new long[n];
        sums[0] = sorted[0];

        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + sorted[i];
        }

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int originalStart = Integer.parseInt(st.nextToken()) - 1;
            int newValue = Integer.parseInt(st.nextToken());
            int start = Arrays.binarySearch(sorted, a[originalStart]);
            int end = Arrays.binarySearch(sorted, newValue);
            int originalValue = sorted[start];

            if (end < 0) {
                end = end * (-1) -1;
            }

            if (end > start) end--;


            long res = originalSum;

            res -= (long) (start + 1) * (originalValue);
            res += (long) (end + 1) * (newValue);

            if (end > start) {
                res -= sums[end] - sums[start];
            }
            else if (start > end) {
                if (end == 0) res += sums[start - 1];
                else res += sums[start - 1] - sums[end - 1];
            }

            pw.println(res);
        }

        pw.close();
    }
}
