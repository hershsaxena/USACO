import java.io.*;
import java.util.*;
// TODO
public class CoverIt {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testCases = Integer.parseInt(st.nextToken());
        for (int t = 0; t < testCases; t++) {
            st = new StringTokenizer(br.readLine());
            int numNodes = Integer.parseInt(st.nextToken());
            int numEdges = Integer.parseInt(st.nextToken());
            ArrayList<Integer>[] adjacency = new ArrayList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                adjacency[i] = new ArrayList<>();
            }

            for (int i = 0; i < numEdges; i++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken()) - 1;
                int end = Integer.parseInt(st.nextToken()) - 1;

                adjacency[start].add(end);
                adjacency[end].add(start);
            }

            HashSet<Integer> colorOne = new HashSet<>();
            HashSet<Integer> colorTwo = new HashSet<>();
            HashSet<Integer> seen = new HashSet<>();
            Queue<Integer> bfs = new LinkedList<>();

            bfs.add(0);
            seen.add(0);
            colorOne.add(0);

            while (!bfs.isEmpty()) {
                int current = bfs.remove();
                int color = colorOne.contains(current) ? 1 : 2;
                int opposite = color == 1 ? 2 : 1;
                ArrayList<Integer> adjacent = adjacency[current];

                for (int a : adjacent) {
                    if (seen.contains(a)) continue;
                    if (opposite == 1) {
                        colorOne.add(a);
                    }
                    else {
                        colorTwo.add(a);
                    }

                    seen.add(a);
                    bfs.add(a);
                }
            }

            String output = "";
            HashSet<Integer> h = (colorOne.size() >= colorTwo.size() ? colorTwo : colorOne);
            pw.println(h.size());
            for (int n : h) {
                output += String.valueOf(n + 1);
                output += " ";
            }

            pw.println(output.substring(0, output.length() - 1));
        }

        pw.close();
    }
}
