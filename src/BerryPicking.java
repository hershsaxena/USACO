import java.util.*;
import java.io.*;

public class BerryPicking {
    static int numTrees;
    static int numBaskets;
    static int[] trees;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("berries.in"));
        PrintWriter pw = new PrintWriter("berries.out");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        numTrees = Integer.parseInt(st.nextToken());
        numBaskets = Integer.parseInt(st.nextToken());
        trees = new int[numTrees];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < numTrees; i++) {
            trees[i] = Integer.parseInt(st.nextToken());
        }

//        if (numBaskets > numTrees) {
//            Arrays.sort(trees);
//            if (numTrees % 2 == 0) {
//                pw.println(Arrays.stream(Arrays.copyOfRange(trees, 0, numTrees/2)).sum());
//            } else {
//                pw.println(Arrays.stream(Arrays.copyOfRange(trees, 0, numTrees/2)).sum() + trees[numTrees/2]/2);
//            }
//
//            pw.close();
//            return;
//        }

        int res = 0;
        int upperBound = Arrays.stream(trees).max().orElse(0);
        for (int i = 1; i <= upperBound; i++) {
            res = Math.max(res, getBerriesCollected(i));
        }

        pw.println(res);
        pw.close();
    }

    static int getBerriesCollected(int bessieMax) {
        PriorityQueue<Integer> treesSorted = new PriorityQueue<>(Comparator.reverseOrder());
        for (int t : trees) treesSorted.add(t);

        int bessieBerries = 0;

        for (int basketsUsed = 0; basketsUsed < numBaskets / 2; basketsUsed++) {
            if (treesSorted.isEmpty()) return -1;

            Integer maxTree = treesSorted.remove();
            if (maxTree > bessieMax) {
                treesSorted.add(maxTree - bessieMax);
            }
            else if (maxTree < bessieMax) return -1;
        }
        for (int basketsUsed = numBaskets / 2; basketsUsed < numBaskets; basketsUsed++) {
            if (treesSorted.isEmpty()) break;
            Integer maxTree = treesSorted.remove();
            if (maxTree > bessieMax) {
                treesSorted.add(maxTree - bessieMax);
                bessieBerries += bessieMax;
            } else {
                bessieBerries += maxTree;
            }
        }

        return bessieBerries;
    }
}
