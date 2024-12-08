import java.io.*;
import java.util.StringTokenizer;
import java.util.*;


public class FollowingDirections {
    static int n;
    static int[] verticalVatCosts;
    static int[] horizontalVatCosts;
    static Node[][] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());


        n = Integer.parseInt(st.nextToken());
        verticalVatCosts = new int[n];
        horizontalVatCosts = new int[n];
        nodes = new Node[n + 1][n + 1];
        Arrays.fill(nodes[0], 0, nodes[0].length, new Node('o', 0, 0));


        for (int i = 1; i < n + 1; i++) {
            nodes[i][0] = new Node('o', 0, 0);
        }


        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String directions = st.nextToken();


            char[] charArray = directions.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char c = charArray[j];
                nodes[i + 1][j + 1] = new Node(c, i + 1, j + 1);
            }


            verticalVatCosts[i] = Integer.parseInt(st.nextToken());
        }


        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            horizontalVatCosts[i] = Integer.parseInt(st.nextToken());
        }


//        int label = 0;


        for (int i = 0; i < n - 1; i++) {
            Node cur = nodes[n][i + 1];
            int useless = subtreeSize(cur);


            if(cur.component == -1) {
                labelComponent(cur, horizontalVatCosts[i]);
            }
        }


        for (int i = 0; i < n - 1; i++) {
            Node cur = nodes[i + 1][n];


            int useless = subtreeSize(cur);
            if(cur.component == -1) {
                labelComponent(cur, verticalVatCosts[i]);;
            }
        }


        Node node = nodes[n][n];
        subtreeSize(node);


        if (node.direction == 'R') {
            labelComponent(node, verticalVatCosts[n - 1]);
        }
        else {
            labelComponent(node, horizontalVatCosts[n - 1]);
        }


        int lastNightCost = 0;


        for (Node[] list : nodes) {
            for (Node n : list) {
                if(n.component == -1) continue;
                lastNightCost += n.component;
            }
        }


        pw.println(lastNightCost);


        st = new StringTokenizer(br.readLine());
        int numChanges = Integer.parseInt(st.nextToken());


        for (int i = 0; i < numChanges; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int column = Integer.parseInt(st.nextToken());
            Node cur = nodes[row][column];
            int subtreeSize = cur.subtreeSize;


            if(cur.direction == 'D') {
                cur.direction = 'R';
                int oldVatCost;
                int newVatCost;

                if(cur.row != n) oldVatCost = findParentVatCost(nodes[row + 1][column], -1 * subtreeSize);
                else oldVatCost = horizontalVatCosts[column - 1];

                if(cur.column != n) newVatCost = findParentVatCost(nodes[row][column + 1], subtreeSize);
                else newVatCost = verticalVatCosts[row - 1];

                int costChange = (newVatCost * subtreeSize) - (oldVatCost * subtreeSize);
                pw.println(lastNightCost + costChange);
                lastNightCost = lastNightCost + costChange;
            }


            else {
                cur.direction = 'D';
                int oldVatCost;
                int newVatCost;

                if(cur.column != n) oldVatCost = findParentVatCost(nodes[row][column + 1], -1 * subtreeSize);
                else oldVatCost = verticalVatCosts[row - 1];

                if(cur.row != n) newVatCost = findParentVatCost(nodes[row + 1][column], subtreeSize);
                else newVatCost = horizontalVatCosts[column - 1];

                int costChange = (newVatCost * subtreeSize) - (oldVatCost * subtreeSize);
                pw.println(lastNightCost + costChange);
                lastNightCost = lastNightCost + costChange;
            }
        }


        pw.close();
    }


    static class Node {
        int component;
        char direction = 'o';
        int subtreeSize;
        int row;
        int column;


        public Node(char direction, int row, int column) {
            this.component = -1;
            this.direction = direction;
            this.subtreeSize = -1;
            this.row = row;
            this.column = column;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "component=" + component +
                    ", direction=" + direction +
                    ", subtreeSize=" + subtreeSize +
                    ", row=" + row +
                    ", column=" + column +
                    '}';
        }
    }


    static void labelComponent(Node cur, int label) {
        cur.component = label;
        int r = cur.row;
        int c = cur.column;


        Node up = nodes[r - 1][c];
        Node left = nodes[r][c - 1];


        if(up.direction == 'D') labelComponent(up, label);
        if(left.direction == 'R') labelComponent(left, label);
    }


    static int subtreeSize(Node cur) {
        int s = 1;
        if(nodes[cur.row - 1][cur.column].direction != 'D' && nodes[cur.row][cur.column - 1].direction != 'R') {
            cur.subtreeSize = s;
            return s;
        }


        if(nodes[cur.row - 1][cur.column].direction == 'D') {
            s += subtreeSize(nodes[cur.row - 1][cur.column]);
        }
        if(nodes[cur.row][cur.column - 1].direction == 'R') {
            s += subtreeSize(nodes[cur.row][cur.column - 1]);
        }


        cur.subtreeSize = s;
        return s;
    }


    static int findParentVatCost(Node cur, int subtreeSizeToSubtract) {
        cur.subtreeSize += subtreeSizeToSubtract;

        if(cur.row == n && cur.column == n) {
            if(cur.direction == 'D') return horizontalVatCosts[cur.column - 1];
            else return verticalVatCosts[cur.row] - 1;
        }
        if(cur.row == n && cur.direction == 'D') {
            return horizontalVatCosts[cur.column - 1];
        }
        if(cur.column == n && cur.direction == 'R') {
            return verticalVatCosts[cur.row - 1];
        }


        if(cur.direction == 'D') {
            return findParentVatCost(nodes[cur.row + 1][cur.column], subtreeSizeToSubtract);
        }
        else {
            return findParentVatCost(nodes[cur.row][cur.column + 1], subtreeSizeToSubtract);
        }
    }
}

