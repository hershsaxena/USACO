import java.util.*;
import java.io.*;

public class VocabularyQuiz {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        // Zero index is empty word
        Word[] words = new Word[n + 1];
        for (int i = 0; i < n + 1; i++) {
            words[i] = new Word(-1, i);
        }

        for (int i = 0; i < n; i++) {
            int cur = a[i];
            words[cur].children.add(i + 1);
            words[i + 1].parent = cur;
        }

        words[0].createDepths(0, words);

        int wordBankSize = 0;
        for (int i = 0; i < n + 1; i++) {
            if (words[i].children.isEmpty()) wordBankSize++;
        }

        outer:
        for (int i = 0; i < wordBankSize; i++) {
            int ind = Integer.parseInt(br.readLine());
            Word w = words[ind];
            boolean found = false;

            while (w.parent != -1) {
                Word past = w;
                w = words[w.parent];
                if (w.children.size() > 1 && !found) {
                    pw.println(w.depth + 1);
                    found = true;
                    if (past.children.isEmpty()) w.children.remove(past.index);
                    continue outer;
                }

                if (past.children.isEmpty()) w.children.remove(past.index);
            }

            if(!found) {
                pw.println(0);
            }
        }

        pw.close();
    }

    static class Word {
        HashSet<Integer> children;
        int parent;
        int depth;
        int index;

        public Word(int parent, int index) {
            this.children = new HashSet<>();
            this.parent = parent;
            this.index = index;
        }

        void createDepths(int depth, Word[] words) {
            this.depth = depth;
            if (this.children.isEmpty()) return;

            for (int i : children) {
                words[i].createDepths(depth + 1, words);
            }
        }
    }
}
