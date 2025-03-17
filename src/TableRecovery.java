import java.util.*;
import java.io.*;

public class TableRecovery {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        if (n == 1) {
            pw.println(2);
            pw.close();
            System.exit(0);
        }

        int[][] table = new int[n + 1][n + 1];

        for (int r = 1; r <= n; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= n; c++) {
                table[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        Option[] options = new Option[n + 1];

        for (int i = 1; i <= n ; i++) {
            options[i] = new Option(i + 1, 2*n - (i - 1));
        }

        HashMap<Integer, Integer> assignments = new HashMap<>();
        int[] counts = new int[2*n + 1];

        for(int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                counts[table[r][c]]++;
            }
        }

        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                int cur = table[r][c];
                Option currentOptions = options[counts[cur]];

                if(!assignments.containsKey(cur)) {
                    if (!assignments.containsValue(currentOptions.lesser)) {
                        assignments.put(cur, currentOptions.lesser);
                        table[r][c] = currentOptions.lesser;
                    }
                    else {
                        assignments.put(cur, currentOptions.greater);
                        table[r][c] = currentOptions.greater;
                    }
                }

                else {
                    table[r][c] = assignments.get(cur);
                }
            }
        }

        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                if(c != 1) pw.print(" ");
                pw.print(table[r][c]);
            }
            pw.println();
        }
        pw.close();
    }

    static class Option {
        int lesser;
        int greater;

        public Option(int lesser, int greater) {
            this.lesser = lesser;
            this.greater = greater;
        }
    }
}
