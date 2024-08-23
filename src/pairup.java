import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class pairup {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("pairup.in"));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pairup.out")));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int numGroups = Integer.parseInt(st.nextToken());
		Group[] cows = new Group[numGroups];
		for (int i = 0; i < numGroups; i++) {
			st = new StringTokenizer(br.readLine());
			cows[i] = new Group(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		br.close();
		Arrays.sort(cows, Comparator.comparingInt((Group a) -> a.time));

		int totalTime = getTotalTime(numGroups, cows);

		pw.println(totalTime);
		pw.close();
	}

	private static int getTotalTime(int numGroups, Group[] cows) {
		int lowerIndex = 0;
		int upperIndex = numGroups - 1;
		int totalTime = 0;

		while (upperIndex > lowerIndex) {
			int numLower = cows[lowerIndex].num;
			int numUpper = cows[upperIndex].num;
			int time = cows[lowerIndex].time + cows[upperIndex].time;
			totalTime = Math.max(time, totalTime);

			if (numUpper > numLower) {
				cows[upperIndex].num -= cows[lowerIndex].num;
				lowerIndex++;
			} else if (numLower > numUpper) {
				cows[lowerIndex].num -= cows[upperIndex].num;
				upperIndex--;
			} else {
				lowerIndex++;
				upperIndex--;
			}
		}

		if(upperIndex == lowerIndex) {
			totalTime = Math.max(2* cows[lowerIndex].time, totalTime);
		}
		return totalTime;
	}

	static class Group {
		public int num; 
		public int time;

		public Group(int num, int time) {
			this.num = num;
			this.time = time;
		}
	}
}
