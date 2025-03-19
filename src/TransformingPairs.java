import java.util.*;
import java.io.*;

public class TransformingPairs {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int testCases = Integer.parseInt(st.nextToken());

        outer:
        for (int t = 0; t < testCases; t++) {
            st = new StringTokenizer(br.readLine());
            long goalOne =  Long.parseLong(st.nextToken());
            long goalTwo = Long.parseLong(st.nextToken());
            long pileOne = Long.parseLong(st.nextToken());
            long pileTwo = Long.parseLong(st.nextToken());
            long res = 0;
            long lastOne = -1;
            long lastTwo = -1;

            while (pileOne > goalOne || pileTwo > goalTwo) {
                if (pileOne == lastOne && pileTwo == lastTwo) break;
                lastOne = pileOne;
                lastTwo = pileTwo;

                if ((pileOne > pileTwo && pileOne != goalOne) || pileTwo == goalTwo) {
                    long timesToRemove = pileOne/pileTwo;
                    if (pileOne - (timesToRemove * pileTwo) < goalOne) {
                        timesToRemove = (pileOne - goalOne) / pileTwo;
                    }

                    pileOne -= timesToRemove*pileTwo;
                    res += timesToRemove;
                }
                else if (pileTwo > pileOne || pileOne == goalOne) {
                    long timesToRemove = pileTwo/pileOne;
                    if (pileTwo - (timesToRemove * pileOne) < goalTwo) {
                        timesToRemove = (pileTwo - goalTwo) / pileOne;
                    }

                    pileTwo -= timesToRemove * pileOne;
                    res += timesToRemove;
                }
            }

            if (pileOne != goalOne || pileTwo != goalTwo) pw.println(-1);
            else pw.println(res);
        }

        pw.close();
    }
}
