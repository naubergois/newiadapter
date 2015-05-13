package br.unifor.iadapter.threadGroup;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import br.unifor.iadapter.genetic.FunctionFitness;

public class GeneticAlgorithm {

	public static Population population(List<City> list)
			throws InvalidConfigurationException {
		Population population = new Population(getConfiguration());
		List<Chromosome> listC = getChromossomes(list);

		for (Chromosome chromosome : listC) {
			population.addChromosome(chromosome);
		}
		return population;
	}

	public static List<Chromosome> getChromossomes(List<City> list)
			throws InvalidConfigurationException {
		List<Chromosome> listC = new ArrayList<Chromosome>();
		for (City city : list) {
			Gene[] genes = geneFromCity(city);
			Chromosome chromossome = new Chromosome(getConfiguration());
			chromossome.setGenes(genes);

			listC.add(chromossome);
		}

		return listC;
	}

	private static Configuration conf;

	public static Configuration getConfiguration()
			throws InvalidConfigurationException {
		if (conf == null) {

			Configuration.reset();

			conf = new DefaultConfiguration();

			conf.setFitnessFunction(new FunctionFitness());
		}
		return conf;
	}

	public static Gene[] geneFromCity(City city)
			throws InvalidConfigurationException {
		Gene[] wgenes = new Gene[2];

		wgenes[0] = new IntegerGene(getConfiguration());
		wgenes[0].setAllele(city.getX());
		wgenes[1] = new IntegerGene(getConfiguration());
		wgenes[1].setAllele(city.getY());

		return wgenes;

	}

}
