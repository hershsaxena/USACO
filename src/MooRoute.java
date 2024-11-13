import java.io.*;
import java.util.*;

public class MooRoute {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numNodes = Integer.parseInt(st.nextToken());
        int numEdges = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[numNodes];
        String[] edges = new String[numEdges];
        for (int i = 0; i < numEdges; i++) {
            edges[i] = br.readLine();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i, Integer.parseInt(st.nextToken()));
        }

        for(String s : edges) {
            st = new StringTokenizer(s);

            int start = Integer.parseInt(st.nextToken()) - 1;
            int startTime = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken()) - 1;
            int endTime = Integer.parseInt(st.nextToken());

            nodes[start].connected.add(new Edge(start, startTime, end, endTime));
        }

        for(Node n : nodes) {
            Collections.sort(n.connected);
        }

        Queue<Node> bfs = new LinkedList<>();
        bfs.add(nodes[0]);
        nodes[0].earliestArrival = 0;

        while(!bfs.isEmpty()) {
            Node cur = bfs.remove();
            List<Edge> connected = cur.connected;

            while(!connected.isEmpty() && (connected.get(connected.size() - 1).startTime >= cur.earliestArrival + cur.layoverTime || cur.num == 0)) {
//                if(connected.size() == 1 || connected.get(connected.size() - 1).startTime > cur.earliestArrival + cur.layoverTime) {
//                    break;
//                }
                Edge flight = connected.get(connected.size() - 1);
                connected.remove(connected.size() - 1);

                nodes[flight.end].earliestArrival = Math.min(flight.endTime, nodes[flight.end].earliestArrival);
                bfs.add(nodes[flight.end]);
                continue;
            }
        }

        for(Node n : nodes) {
        pw.println(n.earliestArrival != Integer.MAX_VALUE ? n.earliestArrival : -1);
        }
        pw.close();
    }

    private static class Node {
        int num;
        int earliestArrival;
        int layoverTime;
        List<Edge> connected;

        public Node(int num, int layoverTime) {
            this.num = num;
            this.layoverTime = layoverTime;
            earliestArrival = Integer.MAX_VALUE;
            connected = new ArrayList<>();
        }
    }

    private static class Edge implements Comparable<Edge> {
        int start;
        int startTime;
        int end;
        int endTime;

        public Edge(int start, int startTime, int end, int endTime) {
            this.start = start;
            this.startTime = startTime;
            this.end = end;
            this.endTime = endTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return start == edge.start && startTime == edge.startTime && end == edge.end && endTime == edge.endTime;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, startTime, end, endTime);
        }

        @Override
        public int compareTo(Edge o) {
            return this.startTime - o.startTime;
        }
    }
}
