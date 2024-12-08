import java.io.*;
import java.util.*;

public class MooBuzz {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("moobuzz.in"));
        PrintWriter pw = new PrintWriter("moobuzz.out");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter pw = new PrintWriter(System.out);

        long n = Long.parseLong(br.readLine());

        if (n % 8 == 0) {
            pw.println(n + (n / 8)*7 - 1);
        } else if (n % 8 == 1 || n % 8 == 2) {
            pw.println(n + (n/8)*7);
        } else if (n % 8 == 3) {
            pw.println(n + (n/8)*7 + 1);
        } else if (n % 8 == 4) {
            pw.println(n + (n/8)*7 + 3);
        } else if (n % 8 == 5) {
            pw.println(n + (n/8)*7 + 3);
        } else if (n % 8 == 6) {
            pw.println(n + (n/8)*7 + 5);
        } else if (n % 8 == 7) {
            pw.println(n + (n/8)*7 + 6);
        }

        pw.close();
    }
}