import java.util.Arrays;

public class Analytical {
	/* Initial reels. *//**/
    private static int base[][] = {
		{0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6}, 
		{0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6}, 
		{0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6}, 
		{0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6}, 
		{0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6},
    };
	/**/

	/* Random reels. *//*
    private static int base[][] = {
	 	{6, 4, 6, 3, 4, 4, 5, 5, 2, 6, 6, 2, 1, 5, 6, 0, 4, 3, 3, 6, 5, 6, 6, 3, 4, 5, 5, 1, 2, 6}, 
	 	{1, 6, 5, 2, 5, 3, 3, 5, 2, 6, 3, 6, 4, 4, 4, 4, 5, 6, 6, 0, 5, 4, 6, 6, 6, 1, 3, 5, 2, 6}, 
	 	{6, 3, 3, 6, 4, 5, 0, 6, 4, 5, 1, 4, 5, 6, 5, 2, 1, 5, 3, 3, 4, 6, 6, 0, 4, 2, 2, 6, 5, 6}, 
	 	{1, 2, 6, 6, 5, 1, 5, 3, 4, 4, 6, 5, 4, 3, 5, 5, 6, 2, 6, 3, 6, 3, 4, 6, 0, 2, 3, 4, 6, 5}, 
	 	{3, 6, 6, 4, 6, 1, 2, 5, 3, 6, 1, 0, 5, 6, 5, 6, 4, 2, 5, 4, 5, 5, 4, 6, 3, 2, 1, 3, 6, 4}, 
	};
	/**/

	/* High-volatility reels (21.71% hit rate). *//*
    private static int base[][] = {
		{4, 6, 1, 5, 5, 1, 1, 3, 4, 4, 5, 2, 4, 5, 1, 4, 1, 1, 1, 5, 5, 4, 5, 2, 2, 4, 5, 4, 5, 5},
		{6, 6, 2, 3, 6, 1, 6, 4, 3, 2, 3, 3, 3, 6, 6, 1, 6, 3, 5, 2, 2, 1, 5, 4, 1, 4, 6, 2, 1, 1},
		{3, 2, 4, 1, 3, 6, 5, 5, 4, 3, 3, 4, 2, 4, 3, 3, 6, 6, 1, 5, 3, 6, 4, 6, 5, 3, 6, 6, 6, 3},
		{5, 0, 5, 1, 5, 5, 6, 3, 5, 6, 2, 6, 4, 0, 3, 4, 1, 5, 1, 2, 5, 5, 5, 4, 4, 2, 1, 4, 5, 4},
		{6, 3, 5, 5, 6, 0, 4, 6, 4, 3, 4, 5, 1, 3, 5, 5, 2, 3, 6, 3, 4, 1, 6, 2, 5, 5, 6, 6, 4, 1},
	};
	/**/

	/* Low-volatility reels (58.59% hit rate). *//*
    private static int base[][] = {
		{3, 5, 2, 5, 6, 4, 6, 5, 6, 3, 5, 6, 2, 5, 6, 6, 5, 6, 2, 5, 4, 6, 3, 5, 5, 6, 6, 6, 6, 5},
		{1, 3, 3, 6, 2, 2, 6, 5, 1, 0, 1, 1, 2, 4, 6, 4, 3, 4, 5, 2, 5, 1, 4, 5, 3, 2, 2, 5, 3, 5},
		{3, 5, 3, 5, 4, 1, 5, 5, 4, 6, 5, 4, 5, 6, 6, 5, 3, 6, 3, 6, 5, 6, 2, 4, 5, 5, 6, 5, 3, 5},
		{1, 2, 5, 4, 2, 2, 4, 5, 3, 1, 3, 6, 3, 4, 3, 3, 5, 5, 1, 1, 6, 3, 5, 1, 1, 3, 3, 1, 5, 1},
		{1, 5, 2, 6, 2, 5, 2, 4, 4, 1, 6, 4, 2, 2, 1, 5, 6, 6, 6, 2, 6, 6, 2, 1, 1, 6, 3, 4, 1, 1},
	};
	/**/

	private static double[][] paytable = {
		{  0, 0, 0, 0,  0,  0,  0,},
		{  0, 0, 0, 0,  0,  0,  0,},
		{  0, 0, 0, 0,  0,  0,  0,},
		{  5, 4, 3, 1,0.8,0.5,0.4,},
		{ 20,15,10, 5,  4,  2,1.5,},
		{100,60,40,15, 12,  8,  6,},
	};

    public static void main(String[] args) {
		int count;
		int symbol;
        long total = 0;
        int freqencies[][] = new int[7][6];
        for (int a = 0; a < base[0].length; a++) {
            for (int b = 0; b < base[1].length; b++) {
                for(int c = 0; c < base[2].length; c++) {
                    for (int d = 0; d < base[3].length; d++) {
                        for (int e = 0; e < base[4].length; e++) {
                            int stops[] = {a, b, c, d, e};
                            total++;
							
							count = 0;
							symbol = base[0][a];

							if(base[0][a] != 0) {
								for(int col = 0; col < 5; col++) {
									if (base[col][stops[col]] == symbol || base[col][stops[col]] == 0) {
										count++;
									} else {
										break;
									}
								}
							} else if(base[0][a] == 0) {
								int count1 = 0;
								for(int col = 0; col < 5; col++) {
									if (base[col][stops[col]] == 0) {
										count1++;
									} else {
										break;
									}
								}
								
								int count2 = 0;
								int substitute = 0;
								for(int col = 0; col < 5; col++) {
									if(base[col][stops[col]] != 0) {
										substitute = base[col][stops[col]];
										break;
									}
								}

								for(int col = 0; substitute==symbol && col < 5; col++) {
									if (base[col][stops[col]] == substitute || base[col][stops[col]] == 0) {
										count2++;
									} else {
										break;
									}
								}
								
								if(paytable[count1][0] > paytable[count2][substitute]) {
									symbol = 0;
									count = count1;
								} else {
									symbol = substitute;
									count = count2;									
								}
							}

							if (count >= 3) {
								freqencies[symbol][count]++;
							}
                        }
                    }
                }
            }
        }

        System.out.println("Total Combinations: " + total);
        System.out.println("Number of Observations: " + Arrays.deepToString(freqencies)
            .replace("[[","[\n [")
            .replace("]]","]\n]")
            .replace("], [", "],\n ["));
    }
}
