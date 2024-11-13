import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Pasture {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st = new StringTokenizer(br.readLine());

		int numCows = Integer.parseInt(st.nextToken());
		Cow[] cows = new Cow[numCows];
		for (int i = 0; i < cows.length; i++) {
			st = new StringTokenizer(br.readLine());
			cows[i] = new Cow(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}

		Arrays.sort(cows, Comparator.comparingInt((Cow a) -> a.x));
		for (int i = 0; i < numCows; i++) {
			cows[i].x = i;
		}

		Arrays.sort(cows, Comparator.comparingInt((Cow a) -> a.y));
		for (int i = 0; i < numCows; i++) {
			cows[i].y = i;
		}

		CompressedCow[] compressedCows = new CompressedCow[numCows];
		for (int i = 0; i < numCows; i++) {
			compressedCows[i] = new CompressedCow(cows[i], numCows)	;
		}

		int[][] sums = createSumArray(createInitialArray(compressedCows));
		Arrays.sort(compressedCows);
		long total = 0;

		int[][] expandedSums = new int[sums.length+1][sums.length+1];
		for(int r = 0; r < sums.length; r++) {
			System.arraycopy(sums[r], 0, expandedSums[r+1], 1, sums.length);
		}


		for(int first = 0; first < numCows; first++) {
			for(int second = first+1; second < numCows; second++) {
				CompressedCow left = compressedCows[first];
				CompressedCow right = compressedCows[second];
				CompressedCow higher, lower;

				if(left.r < right.r) {
					higher = left;
					lower = right;
				}
				else {
					higher = right;
					lower = left;
				}


				int tops;
				tops = query(expandedSums, 0, left.c, higher.r, right.c);
				int bottoms;
				bottoms = query(expandedSums, lower.r, left.c, numCows-1, right.c);
				total += ((long) tops * bottoms);
			}
		}

		total += numCows + 1;
		pw.println(total);
		pw.close();
	}

	static int[][] createInitialArray(CompressedCow[] compressedCows) {
		int[][] initial = new int[compressedCows.length][compressedCows.length];
		for(CompressedCow cow : compressedCows) {
			initial[cow.r][cow.c] = 1;
		}

		return initial;
	}

	static int[][] createSumArray(int[][] initial) {
		int[][] sums = new int[initial.length + 1][initial.length + 1];

		for (int r = 1; r < sums.length; r++) {
			for (int c = 1; c < sums.length; c++) {
				sums[r][c] = sums[r-1][c] + sums[r][c-1] + initial[r-1][c-1] - sums[r-1][c-1];
			}
		}

		int[][] result = new int[initial.length][initial.length];

		for (int r = 1; r < sums.length; r++) {
			System.arraycopy(sums[r], 1, result[r - 1], 0, sums.length - 1);
		}

		return sums;
	}

	static int query(int[][] sums, int r1, int c1, int r2, int c2) {
		r1++;
		c1++;
		r2++;
		c2++;

		return sums[r2][c2] - sums[r2][c1-1] - sums[r1-1][c2] + sums[r1-1][c1-1];
	}

	static class Cow {
		public int x;
		public int y;

		public Cow(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static class CompressedCow {
		public int r;
		public int c;

		public CompressedCow(Cow cow, int numCows) {
			int numRows = numCows - 1;
			this.r = numRows - cow.y;
			this.c = cow.x;
		}
	}
}
