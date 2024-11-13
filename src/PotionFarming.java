import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PotionFarming {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int maxPotions = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[maxPotions];
        int[] potions = new int[maxPotions];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < maxPotions; i++) {
            nodes[i] = new Node(i);
        }

        for (int i = 0; i < maxPotions; i++) {
            potions[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        for (int i = 0; i < maxPotions - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(st.nextToken()) - 1;
            int n2 = Integer.parseInt(st.nextToken()) - 1;

            nodes[n1].connected.add(nodes[n2]);
            nodes[n2].connected.add(nodes[n1]);
        }

        int leaves = (int) Arrays.stream(nodes).skip(1).filter(n -> n.connected.size() == 1).count();

        for (int i = 0; i < leaves; i++) {
            int potion = potions[i];
            nodes[potion].potions++;
        }

        pw.println(doDFS(nodes[0], null).potionsCollected);
        pw.close();
    }

    static Pair doDFS(Node cur, Node parent) {
        if(cur.num != 0 && cur.connected.size() == 1) {
            if(cur.potions == 0) return new Pair(1, 0);
            else return new Pair(0, 1);
        }

        ArrayList<Pair> childrenAnswers = new ArrayList<>();

        ArrayList<Node> connected = cur.connected;
        for (int i = 0; i < connected.size(); i++) {
            Node n = connected.get(i);
            if(n == parent) continue;
            childrenAnswers.add(doDFS(n, cur));
        }

        int leavesSum = 0;
        int collected = 0;
        for (Pair p : childrenAnswers) {
            leavesSum += p.leavesAvailable;
            collected += p.potionsCollected;
        }

        if(cur.potions >= leavesSum) {
            return new Pair(0, collected + leavesSum);
        }
        else {
            return new Pair(leavesSum - cur.potions, collected + cur.potions);
        }
    }

    static class Pair {
        int leavesAvailable;
        int potionsCollected;

        public Pair(int leavesAvailable, int potionsInSubtree) {
            this.leavesAvailable = leavesAvailable;
            this.potionsCollected = potionsInSubtree;
        }
    }

    static class Node {
        int num;
        int potions;
        ArrayList<Node> connected;

        public Node(int num) {
            this.num = num;
            this.potions = 0;
            this.connected = new ArrayList<>();
        }
    }
}
