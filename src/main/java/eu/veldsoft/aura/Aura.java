package eu.veldsoft.aura;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.ToDoubleFunction;

public class Aura {
	public class Model {
		public boolean valid = true;

		public int[][] baseReels = {
			{0,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,},
			{0,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,},
			{0,0,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,},
			{0,1,1,2,2,2,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,},
			{0,1,1,1,2,2,2,3,3,3,3,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,},
		};

		public int[][] lines = {
			{1,1,1,1,1,},
			{0,0,0,0,0,},
			{2,2,2,2,2,},
			{0,1,2,1,0,},
			{2,1,0,1,2,},
			{0,0,1,2,2,},
			{2,2,1,0,0,},
			{1,0,0,0,1,},
			{1,2,2,2,1,},
			{0,1,1,1,0,},
			{2,1,1,1,2,},
			{1,0,1,2,1,},
			{1,2,1,0,1,},
			{0,1,0,1,0,},
			{2,1,2,1,2,},
			{1,1,0,1,1,},
			{1,1,2,1,1,},
			{0,0,2,0,0,},
			{2,2,0,2,0,},
			{0,2,0,2,0,},
		};

		public int[][] paytable = {
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
			{100,80,60,20,16,10,8,},
			{400,300,200,100,80,40,30,},
			{2000,1200,800,300,240,160,120,},			
		};
	}

	public class Statistics {
		public long wonMoney = 0L;
		public long lostMoney = 0L;
		public long baseMoney = 0L;
		public long baseHitFrequency = 0L;
		public long totalNumberOfGames = 0L;
		public long numberOfSimulations = 10_000_000L;
		Map<Integer, Long> winHistogram = new HashMap<>();
	}

	//TODO new SecureRandom()
	private static final Random PRNG = ThreadLocalRandom.current();

	public Model model = new Model();
	public Statistics statistics = new Statistics();

	private void spin(int[][] reels, int[][] view) {
		for (int i = 0; i < view.length && i < reels.length; i++) {
			int r = PRNG.nextInt(reels[i].length);
			int u = r - 1;
			int d = r + 1;

			if (u < 0) {
				u = reels[i].length - 1;
			}

			if (d >= reels[i].length) {
				d = 0;
			}

			view[i][0] = reels[i][u];
			view[i][1] = reels[i][r];
			view[i][2] = reels[i][d];
		}
	}

	private int wildWin(int[] line){
		if(line[0] != 0) {
			return 0;
		}

		int symbol = line[0];

		int number = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == symbol) {
				number++;
			} else {
				break;
			}
		}

		return model.paytable[number][symbol];
	}

	private int lineWin(int[] line) {
		int symbol = line[0];

		for (int i = 0; i < 5 && (symbol == 0); i++) {
			symbol = line[i];
		}

		for (int i = 0; i < line.length; i++) {
			if (line[i] == 0) {
				line[i] = symbol;
			}
		}

		int number = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == symbol) {
				number++;
			} else {
				break;
			}
		}
		
		return model.paytable[number][symbol];
	}

	private int linesWin(int[][] view) {
		int win1 = 0;
		int win2 = 0;

		int[] line = { -1, -1, -1, -1, -1 };
		for (int l = 0; l < model.lines.length; l++) {
			for (int i = 0; i < line.length; i++) {
				int index = model.lines[l][i];
				line[i] = view[i][index];
			}

			win1 += wildWin(line);
			win2 += lineWin(line);
		}

		return Math.max(win1, win2);
	}


	private void singleBaseGame(int[][] view) {
		spin(model.baseReels, view);

		int win = linesWin(view);
		if (win > 0) {
			statistics.baseHitFrequency++;
			statistics.baseMoney += win;
			statistics.wonMoney += win;
		}

		statistics.winHistogram.merge(win, 1L, Long::sum);
	}

	private void validate() {
		for(int i = 0; i < model.baseReels.length; i++) {
			Map<Integer, Integer> frequencies = new HashMap<>();
			for(int j = 0; j < model.baseReels[i].length; j++) {
				int symbol = model.baseReels[i][j];
				frequencies.merge(symbol, 1, Integer::sum);
			}

			for(int symbol = 0; symbol < model.paytable[i+1].length; symbol++) {
				if(frequencies.getOrDefault(symbol, 0) < 1) {
					model.valid = false;
					return;
				}
			}
		}
	}

	public void simulate() {
		//validate();
		if(model.valid == false) {
			return;
		}

		int[][] view = {
			{ -1, -1, -1 },
			{ -1, -1, -1 },
			{ -1, -1, -1 },
			{ -1, -1, -1 },
			{ -1, -1, -1 },
		};

		for (long g = 0L; g < statistics.numberOfSimulations; g++) {
			statistics.totalNumberOfGames++;
			statistics.lostMoney += model.lines.length;

			singleBaseGame(view);
		}
	}

	public void simulate(Model model, Statistics statistics) {
		this.model = model;
		this.statistics = statistics;

		simulate();
	}

	public void simulate(String[] args) {
		System.out.println("Output will be on the screen!");
		System.out.println();
		System.out.println("Ctrl+C to abort simulation.");
		System.out.println();
		System.out.println("java Main -l1000");
		System.out.println("Do 1 000 iterations.");
		System.out.println();
		System.out.println("java Main -l1000k");
		System.out.println("Do 1 000 000 iterations.");
		System.out.println();
		System.out.println("java Main -l10m");
		System.out.println("Do 10 000 000 iterations.");
		System.out.println();
		System.out.println("java Main");
		System.out.println("Do 10 000 000 iterations as default value.");
		System.out.println();

		if (args.length > 0 && args[0].contains("-l")) {
			String lParameter = args[0].substring(2);

			if (lParameter.contains("k")) {
				lParameter = lParameter.substring(0, lParameter.length() - 1);
				lParameter += "000";
			}

			if (lParameter.contains("m")) {
				lParameter = lParameter.substring(0, lParameter.length() - 1);
				lParameter += "000000";
			}

			try {
				statistics.numberOfSimulations = Long.parseLong(lParameter);
			} catch (Exception exception) {
			}
		}

		simulate(model, statistics);

		// TODO Hunt for 96.00% RTP.
		System.out.println("Won money: " + statistics.wonMoney);
		System.out.println("Lost money: " + statistics.lostMoney);
		System.out.println("Total RTP%: " + ((double) statistics.wonMoney * 100.0 / (double) statistics.lostMoney));
		System.out.println("Base Game RTP%: " + ((double) statistics.baseMoney * 100.0 / (double) statistics.lostMoney));
		System.out.println("Base Hit Frequency: " + statistics.baseHitFrequency);
		System.out.println("Total Number of Games: " + statistics.totalNumberOfGames);
		System.out.println("Win Histogram: ");
		statistics.winHistogram.entrySet().stream().sorted(Map.Entry.comparingByKey())
           .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue()));
	}

	public double score(ToDoubleFunction<Aura> formula) {
		return formula.applyAsDouble(this);
	}
}
