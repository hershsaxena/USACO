import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Lineup {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testCases = Integer.parseInt(st.nextToken());
        outer:
        for (int t = 0; t < testCases; t++) {
            int numCows = Integer.parseInt(br.readLine());
            int[] a = new int[numCows];
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < numCows; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }

            int maxAfter = 0;
            ArrayList<Integer> taken = new ArrayList<>();
            HashSet<Integer> takenIndices = new HashSet<>();

            for (int i = numCows - 1; i >= 0; i--) {
                if (a[i] >= maxAfter) {
                    taken.add(a[i]);
                    maxAfter = a[i];
                    takenIndices.add(i);
                }
            }

            Collections.reverse(taken);
            LastTaken[] lastTaken = new LastTaken[numCows];
            int recent = -1;
            int secondRecent = -1;

            for (int i = numCows - 1; i >= 0; i--) {
                if (takenIndices.contains(i)) {
                    secondRecent = recent;
                    recent = a[i];
                }

                lastTaken[i] = new LastTaken(recent, secondRecent);
            }

            HashSet<Integer> ignoredIndices = new HashSet<>(takenIndices);
            for (int i = 0; i < numCows; i++) {
                if (lastTaken[i].secondRecent > a[i]) ignoredIndices.add(i);
            }

            int max = -1;
            int maxIndex = -1; // TODO: Might not exist ex. 10 9 8 7 6 5 4

            for (int i = 0; i < numCows; i++) {
                if (ignoredIndices.contains(i)) continue;

                if (a[i] > max) {
                    max = a[i];
                    maxIndex = i;
                }
            }

            if (maxIndex == -1) {
                StringBuilder res = new StringBuilder();
                for (int i = 0; i < taken.size(); i++) {
                    res.append(taken.get(i));
                    if (i != taken.size() - 1) res.append(" ");
                }

                pw.println(res.toString());
                continue outer;
            }

            boolean hasBefore = false;

            for (int i = 0; i < numCows; i++) {
                if (takenIndices.contains(i)) hasBefore = true;
                if (maxIndex == i) break;
            }

            ArrayList<Integer> arr = new ArrayList<>();
            for (int i = 0; i < numCows; i++) {
                arr.add(a[i]);
            }

            if (!hasBefore) {
                int mostRecentIndex = -1;
                for (int i = maxIndex + 1; i < numCows; i++) {
                    if (takenIndices.contains(i)) {
                        mostRecentIndex = i;
                        break;
                    }
                }

                int mostRecent = arr.get(mostRecentIndex);
                arr.remove(mostRecentIndex);
                arr.add(0, mostRecent);
            }

            else {
                int takenBeforeIndex = -1;
                int mostRecentIndex = -1;
                int takenBefore = -1;
                int mostRecent = -1;

                for (int i = maxIndex - 1; i >= 0; i--) {
                    if (takenIndices.contains(i)) {
                        takenBeforeIndex = i;
                        break;
                    }
                }

                int x = 0;
                takenBefore = arr.get(takenBeforeIndex);

                for (int i = maxIndex + 1; i < numCows; i++) {
                    if (takenIndices.contains(i)) {
                        mostRecentIndex = i;
                        break;
                    }
                }

                mostRecent = arr.get(mostRecentIndex);

                arr.remove(mostRecentIndex);
                arr.add(takenBeforeIndex + 1, mostRecent);
            }

            ArrayList<Integer> res = new ArrayList<>();
            int maxSeen = -1;

            for (int i = numCows - 1; i >= 0; i--) {
                if (arr.get(i) >= maxSeen) {
                    res.add(arr.get(i));
                    maxSeen = arr.get(i);
                }
            }

            Collections.reverse(res);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < res.size(); i++) {
                result.append(res.get(i));
                if (i != res.size() - 1) result.append(" ");
            }

            pw.println(result.toString());
        }

        pw.close();
    }

    static class LastTaken {
        int mostRecent;
        int secondRecent;

        public LastTaken(int mostRecent, int secondRecent) {
            this.mostRecent = mostRecent;
            this.secondRecent = secondRecent;
        }

        @Override
        public String toString() {
            return "LastTaken{" +
                    "mostRecent=" + mostRecent +
                    ", secondRecent=" + secondRecent +
                    '}';
        }
    }
}
