import java.io.*;
import java.util.*;

public class SwapitySwapitySwap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("swap.in"));
        PrintWriter pw = new PrintWriter("swap.out");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numCows = Integer.parseInt(st.nextToken());
        int cycleLength = Integer.parseInt(st.nextToken());
        int numCycles = Integer.parseInt(st.nextToken());
        int[][] swaps = new int[cycleLength][2];

        for (int i = 0; i < cycleLength; i++) {
            st = new StringTokenizer(br.readLine());
            swaps[i][0] = Integer.parseInt(st.nextToken()) - 1;
            swaps[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        int[] cowsAfterCycle = new int[numCows];
        for (int i = 0; i < numCows; i++) {
            cowsAfterCycle[i] = i;
        }
        for (int[] swap : swaps) {
            int lower = swap[0];
            int upper = swap[1];
            int temp;

            while (upper >= lower) {
                temp = cowsAfterCycle[lower];
                cowsAfterCycle[lower] = cowsAfterCycle[upper];
                cowsAfterCycle[upper] = temp;
                upper--;
                lower++;
            }
        }

        Cycle[] cycles = new Cycle[numCows];

        for (int i = 0; i < numCows; i++) {
            int currentCycleIndex = 1;
            List<Integer> currentCycle = new ArrayList<>();

            if(cycles[i] == null) {
                currentCycle.add(i);
                currentCycle.add(cowsAfterCycle[i]);

                while(!currentCycle.get(currentCycleIndex).equals(currentCycle.get(0))) {
                    currentCycle.add(cowsAfterCycle[currentCycle.get(currentCycleIndex)]);
                    //Where currentCycle[currentcycleindex] goes after 1 k
                    currentCycleIndex++;
                }

                currentCycle.remove(currentCycle.size() - 1);

                if(currentCycle.size() != 1) {
                    List<Integer> withoutFirst = currentCycle.subList(1, currentCycle.size());
                    Collections.reverse(withoutFirst);
                }

                for (int j = 0; j < currentCycle.size(); j++) {
                    int c = currentCycle.get(j);
                    cycles[c] = new Cycle(j, currentCycle);
                }
            }
        }
        Cow[] cows = new Cow[numCows];

        for (int i = 0; i < cycles.length; i++) {
            Cycle c = cycles[i];
            int index = (numCycles + c.startPosition) % c.cycle.size();
            int res = c.cycle.get(index);

            cows[i] = new Cow(i, res);
        }
        Arrays.sort(cows);

        for (Cow c : cows) {
            pw.println(c.cowNum + 1);
        }

        pw.close();
    }
    static class Cycle {
        int startPosition;
        List<Integer> cycle;

        public Cycle(int startPosition, List<Integer> cycle) {
            this.startPosition = startPosition;
            this.cycle = cycle;
        }

        @Override
        public String toString() {
            return "Cycle{" +
                    "startPosition=" + startPosition +
                    ", cycle=" + cycle +
                    '}';
        }
    }

    static class Cow implements Comparable<Cow> {
        int cowNum;
        int endsUp;

        public Cow(int cowNum, int endsUp) {
            this.cowNum = cowNum;
            this.endsUp = endsUp;
        }

        @Override
        public int compareTo(Cow o) {
            return this.endsUp -o.endsUp;
        }
    }
}
