import java.io.*;
import java.util.StringTokenizer;

public class CakeGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int testCases = Integer.parseInt(br.readLine());

        for (int t = 0; t < testCases; t++) {
            int numCakes = Integer.parseInt(br.readLine());
            long[] cakes = new long[numCakes];
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int i = 0; i < numCakes; i++) {
                cakes[i] = Long.parseLong(st.nextToken());
            }

            long[] prefixSums = new long[numCakes];
            prefixSums[0] = cakes[0];
            for (int i = 1; i < numCakes; i++) {
                prefixSums[i] = prefixSums[i - 1] + cakes[i];
            }

            long elsieMax = 0;

            for (int i = 0; i < (numCakes / 2) - 1; i++) {
                long left;
                long right;

                left = prefixSums[i];
                int rightLength = (numCakes / 2) - 2 - i;
                if (rightLength == 0) right = 0;
                else {
                    right = prefixSums[numCakes - 1] - prefixSums[numCakes - rightLength - 1];
                }

                elsieMax = Math.max(left + right, elsieMax);
            }

            reverse(cakes, numCakes);
            prefixSums[0] = cakes[0];

            for (int i = 1; i < numCakes; i++) {
                prefixSums[i] = prefixSums[i - 1] + cakes[i];
            }

            for (int i = 0; i < (numCakes / 2) - 1; i++) {
                long left;
                long right;

                left = prefixSums[i];
                int rightLength = (numCakes / 2) - 2 - i;
                if (rightLength == 0) right = 0;
                else {
                    right = prefixSums[numCakes - 1] - prefixSums[numCakes - rightLength - 1];
                }

                elsieMax = Math.max(left + right, elsieMax);
            }

            pw.println(prefixSums[numCakes - 1] - elsieMax + " " + elsieMax);
        }

        pw.close();
    }

     static void reverse(long[] arr, int numCakes) {
        for (int i = 0; i < numCakes / 2; i++) {
            long temp = arr[i];
            arr[i] = arr[numCakes - 1 - i];
            arr[numCakes - 1 - i] = temp;
        }
    }
}
