import java.io.*;
import java.util.*;

public class Diamond {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("diamond.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("diamond.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numDiamonds = Integer.parseInt(st.nextToken());
        int maxDiff = Integer.parseInt(st.nextToken());
        int[] diamonds = new int[numDiamonds];
        for (int i = 0; i < numDiamonds; i++) {
            diamonds[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(diamonds);
        int[] cases = new int[numDiamonds];
        int end = 0;
        for (int i = 0; i < diamonds.length; i++) {
            int d = diamonds[i];
            int target = d + maxDiff;

            while(diamonds[end] < target && end != diamonds.length - 1 && diamonds[end + 1] <= target || (end != diamonds.length - 1 && diamonds[end + 1] == diamonds[end])) {
                end++;
            }

            cases[i] = end - i;
        }
        int[] maxes = new int[numDiamonds];
        maxes[maxes.length - 1] = cases[cases.length - 1];

        for (int i = diamonds.length - 2; i >= 0; i--) {
            maxes[i] = Math.max(maxes[i + 1], cases[i]);
        }

        int caseOn = 0;
        int res = 0;
        for (int split = (cases[0]); split < diamonds.length - 1; split++) {
            // Split is after the index
            while (cases[caseOn] + caseOn < split) {
                caseOn++;
            }

            res = Math.max(res, cases[caseOn] + maxes[split + 1]);
        }

        pw.println(res + 2);
        pw.close();
    }
}