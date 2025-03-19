import java.io.*;
import java.util.*;

public class GregAndArray {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int numOperations = Integer.parseInt(st.nextToken());
        int queries = Integer.parseInt(st.nextToken());
        long[] a = new long[n];
        int[] operationsDiff = new int[numOperations + 2];
        Operation[] operations = new Operation[numOperations];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(st.nextToken());
        }

        for (int i = 0; i < numOperations; i++) {
            st = new StringTokenizer(br.readLine());
            operations[i] = new Operation(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < queries; i++) {
            st = new StringTokenizer(br.readLine());
            operationsDiff[Integer.parseInt(st.nextToken())]++;
            operationsDiff[Integer.parseInt(st.nextToken()) + 1]--;
        }

        int[] operationsApplied = new int[numOperations];
        operationsApplied[0] = operationsDiff[1];

        for (int i = 2; i < numOperations + 1; i++) {
            operationsApplied[i - 1] = operationsApplied[i - 2] + operationsDiff[i];
        }

        long[] aDiff = new long[n + 2];
        for (int i = 0; i < numOperations; i++) {
            Operation o = operations[i];
            int timesApplied = operationsApplied[i];

            aDiff[o.l + 1] += (long) o.val * timesApplied;
            aDiff[o.r + 2] -= (long) o.val * timesApplied;
        }

        long[] res = new long[n];
        res[0] = aDiff[1];
        for (int i = 2; i < n + 1; i++) {
            res[i - 1] = res[i - 2] + aDiff[i];
        }

        for (int i = 0; i < n; i++) {
            res[i] += a[i];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(res[i]);
            if (i != n - 1) sb.append(" ");
        }

        pw.println(sb.toString());
        pw.close();
    }

    static class Operation {
        int l;
        int r;
        int val;

        public Operation(int l, int r, int val) {
            this.l = l;
            this.r = r;
            this.val = val;
        }
    }
}