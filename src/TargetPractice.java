import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class TargetPractice {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testCases = Integer.parseInt(st.nextToken());
        for (int t = 0; t < testCases; t++) {
            // IO
            st = new StringTokenizer(br.readLine());
            int numTargets = Integer.parseInt(st.nextToken());
            long lowerX = Long.parseLong(st.nextToken());

            Target[] targets = new Target[numTargets];
            for (int i = 0; i < numTargets; i++) {
                st = new StringTokenizer(br.readLine());
                targets[i] = new Target(lowerX, Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
            }

            Cow[] cows = new Cow[numTargets * 4];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < cows.length; i++) {
                cows[i] = new Cow(-1, Long.parseLong(st.nextToken()));
            }

            // Algorithm

        }
        pw.close();
    }

    static class Target {
        long lowerX;
        long lowerY;
        long upperX;
        long upperY;
        Coordinate lowerLeft;
        Coordinate lowerRight;
        Coordinate upperLeft;
        Coordinate upperRight;

        public Target(long lowerX, long lowerY, long upperY, long upperX) {
            this.lowerX = lowerX;
            this.lowerY = lowerY;
            this.upperY = upperY;
            this.upperX = upperX;
            this.lowerLeft = new Coordinate(lowerX, lowerY);
            this.lowerRight = new Coordinate(upperX, lowerY);
            this.upperLeft = new Coordinate(lowerX, upperY);
            this.upperRight = new Coordinate(upperX, upperY);
        }
    }

    static class Coordinate {
        long x;
        long y;

        public Coordinate(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Cow {
        long position;
        long slope;

        public Cow(long position, long slope) {
            this.position = position;
            this.slope = slope;
        }
    }
}
