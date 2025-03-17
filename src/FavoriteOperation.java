import java.util.*;
import java.io.*;

public class FavoriteOperation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testCases = Integer.parseInt(st.nextToken());
        for (int t = 0; t < testCases; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long m = Long.parseLong(st.nextToken());
            long[] a = new long[n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Long.parseLong(st.nextToken());
            }

            a = Arrays.stream(a).map((long i) -> i % m).toArray();
            Arrays.sort(a);

            long[] arr = new long[2*n];
            for (int i = 0; i < n; i++) {
                arr[i] = a[i] - m;
            }
            for (int i = 0; i < n; i++) {
                arr[n + i] = a[i];
            }

            long[] sums = new long[2*n];
            sums[0] = arr[0];

            for (int i = 1; i < 2*n; i++) {
                sums[i] = sums[i - 1] + arr[i];
            }

            long res = Long.MAX_VALUE;
            for (int end = n - 1; end < 2*n; end++) {
                int beginning = end - n + 1;
                int average = (beginning + end)/2;
                long sumsBeginning;
                if (beginning == 0) sumsBeginning = 0;
                else sumsBeginning = sums[beginning - 1];

                long boredomLeft = arr[average] * (average - beginning + 1) - (sums[average] - sumsBeginning);
                long boredomRight = (sums[end] - sums[average]) - (arr[average]) * (end - average);

                res = Math.min(res, boredomRight + boredomLeft);
            }

            pw.println(res);
        }

        pw.close();
    }
}
