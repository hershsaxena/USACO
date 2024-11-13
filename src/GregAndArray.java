import java.io.*;
import java.util.*;

public class GregAndArray {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] a = new int[n + 2];
        for (int i = 1; i < n + 1; i++) { // Create Difference Array
            a[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 1; i < n + 2; i++) { // Create Difference Array
            a[i] -= a[i - 1];
        }

        int[][] operations = new int[m][3]; // Create array listing operations
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            operations[i][0] = Integer.parseInt(st.nextToken());
            operations[i][1] = Integer.parseInt(st.nextToken());
            operations[i][2] = Integer.parseInt(st.nextToken());
        }

        int[] operationsApplied = new int[n + 2]; // Create difference array showing how many times each operation is applied
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            operationsApplied[Integer.parseInt(st.nextToken())]++;
            operationsApplied[Integer.parseInt(st.nextToken()) + 1]--;
        }
        createFromDiff(operationsApplied); // Use prefix sums to turn the difference array into the actual array of operations applied

        for (int i = 1; i < operationsApplied.length - 2; i++) {
            int cur = operationsApplied[i];
            a[operations[i][0]] += operations[i][2] * cur;
            a[operations[i][1] + 1] += operations[i][2] * cur;
        }

        createFromDiff(a);

        for (int i = 0; i < a.length - 1; i++) {
            int cur = a[i];
            pw.println(cur + " ");
        }
        pw.println(a[a.length - 1]);

        pw.close();
    }

    static void createFromDiff(int[] initial) {
        for(int i = 1; i < initial.length; i++) {
            initial[i] += initial[i - 1];
        }
    }
}


