package eu.veldsoft.aura;

import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Mutator;
import io.jenetics.Optimize;
import io.jenetics.UniformCrossover;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
	private static int evaluation(Genotype<IntegerGene> genotype) {
		Aura game = new Aura();
		for(int i = 0, index=0; i < game.model.baseReels.length; i++) {
			for(int j = 0; j < game.model.baseReels[i].length; j++, index++) {
				game.model.baseReels[i][j] = genotype.chromosome().
				                             as(IntegerChromosome.class).get(index).intValue();
			}
		}

		game.simulate();

		if(game.model.valid == false) {
			return Integer.MAX_VALUE;
		}

		return (int)(1000000 * game.score(g -> 
			1D * Math.abs(0.96D -(double)g.statistics.wonMoney / (double)g.statistics.lostMoney)
			+
			1D * (double)g.statistics.baseHitFrequency / (double)g.statistics.totalNumberOfGames )
		);
	}

	public static void main(String[] args) {
		Aura game = new Aura();

		// /* Random reels. */
		// game.model.baseReels = new int[][] {
		// 	{6, 4, 6, 3, 4, 4, 5, 5, 2, 6, 6, 2, 1, 5, 6, 0, 4, 3, 3, 6, 5, 6, 6, 3, 4, 5, 5, 1, 2, 6}, 
		// 	{1, 6, 5, 2, 5, 3, 3, 5, 2, 6, 3, 6, 4, 4, 4, 4, 5, 6, 6, 0, 5, 4, 6, 6, 6, 1, 3, 5, 2, 6}, 
		// 	{6, 3, 3, 6, 4, 5, 0, 6, 4, 5, 1, 4, 5, 6, 5, 2, 1, 5, 3, 3, 4, 6, 6, 0, 4, 2, 2, 6, 5, 6}, 
		// 	{1, 2, 6, 6, 5, 1, 5, 3, 4, 4, 6, 5, 4, 3, 5, 5, 6, 2, 6, 3, 6, 3, 4, 6, 0, 2, 3, 4, 6, 5}, 
		// 	{3, 6, 6, 4, 6, 1, 2, 5, 3, 6, 1, 0, 5, 6, 5, 6, 4, 2, 5, 4, 5, 5, 4, 6, 3, 2, 1, 3, 6, 4}, 
		// };
		// game.simulate(args);
		// System.exit( 0 );
		//
		// /* High-volatility reels (21.71% hit rate). */
		// game.model.baseReels = new int[][] {
		// 	{4, 6, 1, 5, 5, 1, 1, 3, 4, 4, 5, 2, 4, 5, 1, 4, 1, 1, 1, 5, 5, 4, 5, 2, 2, 4, 5, 4, 5, 5},
		// 	{6, 6, 2, 3, 6, 1, 6, 4, 3, 2, 3, 3, 3, 6, 6, 1, 6, 3, 5, 2, 2, 1, 5, 4, 1, 4, 6, 2, 1, 1},
		// 	{3, 2, 4, 1, 3, 6, 5, 5, 4, 3, 3, 4, 2, 4, 3, 3, 6, 6, 1, 5, 3, 6, 4, 6, 5, 3, 6, 6, 6, 3},
		// 	{5, 0, 5, 1, 5, 5, 6, 3, 5, 6, 2, 6, 4, 0, 3, 4, 1, 5, 1, 2, 5, 5, 5, 4, 4, 2, 1, 4, 5, 4},
		// 	{6, 3, 5, 5, 6, 0, 4, 6, 4, 3, 4, 5, 1, 3, 5, 5, 2, 3, 6, 3, 4, 1, 6, 2, 5, 5, 6, 6, 4, 1},
		// };
		// game.simulate(args);
		// System.exit( 0 );
		//
		// /* Low-volatility reels (58.59% hit rate). */
		// game.model.baseReels = new int[][] {
		// 	{3, 5, 2, 5, 6, 4, 6, 5, 6, 3, 5, 6, 2, 5, 6, 6, 5, 6, 2, 5, 4, 6, 3, 5, 5, 6, 6, 6, 6, 5},
		// 	{1, 3, 3, 6, 2, 2, 6, 5, 1, 0, 1, 1, 2, 4, 6, 4, 3, 4, 5, 2, 5, 1, 4, 5, 3, 2, 2, 5, 3, 5},
		// 	{3, 5, 3, 5, 4, 1, 5, 5, 4, 6, 5, 4, 5, 6, 6, 5, 3, 6, 3, 6, 5, 6, 2, 4, 5, 5, 6, 5, 3, 5},
		// 	{1, 2, 5, 4, 2, 2, 4, 5, 3, 1, 3, 6, 3, 4, 3, 3, 5, 5, 1, 1, 6, 3, 5, 1, 1, 3, 3, 1, 5, 1},
		// 	{1, 5, 2, 6, 2, 5, 2, 4, 4, 1, 6, 4, 2, 2, 1, 5, 6, 6, 6, 2, 6, 6, 2, 1, 1, 6, 3, 4, 1, 1},
		// };
		// game.simulate(args);
		// System.exit( 0 );

		game = new Aura();
		List<Integer> values = Arrays.stream(game.model.baseReels)
		                       .flatMapToInt(Arrays::stream).boxed().toList();
		List<Integer> unique = new ArrayList<>(values.stream()
		                                       .distinct().sorted().toList());

		List<Genotype<IntegerGene>> population = new ArrayList<>();

		IntegerGene[] genes = values.stream()
		                      .map(index -> IntegerGene.of(index, 0, unique.size() - 1))
		                      .toArray(IntegerGene[]::new);

		for(int i = 0; i < 50; i++) {
			population.add(Genotype.of(IntegerChromosome.of(genes)));
		}
		for(int i = 0; i < 50; i++) {
			population.add(Genotype.of(IntegerChromosome.of(genes)));
			Collections.shuffle(Arrays.asList(genes));
		}

		Genotype<IntegerGene> factory = Genotype.of(
		                                    IntegerChromosome.of(0, unique.size(), values.size()));

		Engine<IntegerGene, Integer> engine = Engine.builder(Main::evaluation, factory)
		                                      .populationSize(population.size())
		                                      .optimize(Optimize.MINIMUM)
		                                      //   .survivorsFraction(0.05)
		                                      //   .survivorsSelector(new EliteSelector<>())
		                                      .alterers(
		                                              new UniformCrossover<>(0.5),
		                                              new Mutator<>(0.05)
		                                      )
		                                      .build();

		Genotype<IntegerGene> result = engine.stream(population).
		limit(100).peek(intermediate -> {
			System.out.println(LocalTime.now() + "\t" +
			                   intermediate.generation() + "\t" +
			                   intermediate.bestFitness());
		}).
		collect(EvolutionResult.toBestGenotype());

		for(int i = 0, index=0; i < game.model.baseReels.length; i++) {
			for(int j = 0; j < game.model.baseReels[i].length; j++, index++) {
				game.model.baseReels[i][j] = result.chromosome().
				                             as(IntegerChromosome.class).get(index).intValue();
			}
		}
		System.out.println(Arrays.deepToString(game.model.baseReels).
		                   replace("]", "}").
		                   replace("[", "{").
		                   replace("}, {", "},\n\t{").
		                   replace("{{", "{\n\t{").
		                   replace("}}", "}\n}")
		                  );
	}
}
