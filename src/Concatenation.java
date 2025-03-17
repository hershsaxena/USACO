import java.util.*;
import java.io.*;

public class Concatenation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine());
        myString[] words = new myString[n];

        for (int i = 0; i < n; i++) {
            words[i] = new myString(br.readLine());
        }

        Arrays.sort(words);
        String res = "";

        for (myString w : words) {
            res += w.s;
        }

        pw.println(res);
        pw.close();
    }

    static class myString implements Comparable<myString> {
        String s;

        @Override
        public int compareTo(myString o) {
            return (this.s + o.s).compareTo(o.s+this.s);
        }

        public myString(String s) {
            this.s = s;
        }
    }
}
