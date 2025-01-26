import java.io.*;
import java.util.*;

public class Deforestation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int testCases = Integer.parseInt(st.nextToken());

        for (int t = 0; t < testCases; t++) {
            st = new StringTokenizer(br.readLine());
            int numTrees = Integer.parseInt(st.nextToken());
            int numRestrictions = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            long[] trees = new long[numTrees];
            for (int i = 0; i < numTrees; i++) {
                trees[i] = Long.parseLong(st.nextToken());
            }
            Arrays.sort(trees);

            Restriction[] restrictions = new Restriction[numRestrictions];
            for (int i = 0; i < numRestrictions; i++) {
                st = new StringTokenizer(br.readLine());
                restrictions[i] = new Restriction(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()), Integer.parseInt(st.nextToken()));
            }

            Arrays.sort(restrictions, Comparator.comparingLong(a -> a.start));
            PriorityQueue<PQRestriction> activeRestrictions = new PriorityQueue<>();

            for (Restriction r : restrictions) {
                int startTree = Arrays.binarySearch(trees, r.start);
                int endTree = Arrays.binarySearch(trees, r.end);

                if (startTree < 0) {
                    int insertionPoint = -1 * (startTree + 1);
                    startTree = insertionPoint + 1;
                }
                if (endTree < 0) {
                    int insertionPoint = -1 * (endTree + 1);
                    endTree = insertionPoint - 1;
                }

                r.delta = -(r.treesRequired - (endTree - startTree + 1));
            }

            int firstUnusedRestriction = 0;
            int treesCut = 0;

            for (int i = 0; i < numTrees; i++) {
                firstUnusedRestriction = addNewRestrictions(restrictions, activeRestrictions, firstUnusedRestriction, trees[i], treesCut);
                clearPQ(activeRestrictions, trees[i]);

                if (activeRestrictions.isEmpty()) {
                    treesCut++;
                    continue;
                }
                else {
                    PQRestriction mostRestrictive = activeRestrictions.peek();

                    if(treesCut < mostRestrictive.treesCutBeforeEnd) {
                        treesCut++;
                    }
                }
            }

            pw.println(treesCut);
        }

        pw.close();
    }

    static class Restriction {
        long start;
        long end;
        int treesRequired;
        int delta;

        public Restriction(long start, long end, int treesRequired) {
            this.start = start;
            this.end = end;
            this.treesRequired = treesRequired;
        }
    }

    static void clearPQ(PriorityQueue<PQRestriction> activeRestrictions, long treePosition) {
        while(!activeRestrictions.isEmpty()) {
            if (activeRestrictions.peek().end < treePosition) {
                activeRestrictions.remove();
            }
            else return;
        }
    }

    static int addNewRestrictions(Restriction[] restrictions, PriorityQueue<PQRestriction> activeRestrictions, int firstUnusedRestriction, long treePosition, int treesCut) {
        while (true) {
            if (firstUnusedRestriction >= restrictions.length) break;

            Restriction r = restrictions[firstUnusedRestriction];

             if (r.start > treePosition) break;

            activeRestrictions.add(new PQRestriction(treesCut + r.delta, r.end));
            firstUnusedRestriction++;
        }

        return firstUnusedRestriction;
    }

    static class PQRestriction implements Comparable<PQRestriction> {
        int treesCutBeforeEnd;
        long end;

        public PQRestriction(int treesCutBeforeEnd, long end) {
            this.treesCutBeforeEnd = treesCutBeforeEnd;
            this.end = end;
        }

        @Override
        public int compareTo(PQRestriction o) {
            return this.treesCutBeforeEnd - o.treesCutBeforeEnd;
        }
    }
}