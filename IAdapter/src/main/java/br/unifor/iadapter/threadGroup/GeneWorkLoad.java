package br.unifor.iadapter.threadGroup;

import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class GeneWorkLoad {

	private static Configuration conf;

	public static Configuration getConfiguration()
			throws InvalidConfigurationException {
		if (conf == null) {

			conf = new DefaultConfiguration();

			conf.setFitnessFunction(new FunctionFitness());
		}
		return conf;
	}

	public static List<WorkLoad> createWorkLoadsFromChromossomeWithGui(
			int userNumbers) throws InvalidConfigurationException {
		IChromosome[] list = createPopulation(userNumbers);
		List<WorkLoad> workLoads = new ArrayList<WorkLoad>();
		for (IChromosome iChromosome : list) {
			Gene[] gene = iChromosome.getGenes();
			WorkLoad workload = FactoryWorkLoad.createWorkLoadWithGui(gene);
			workLoads.add(workload);
		}
		return workLoads;
	}

	public static IChromosome[] createPopulation(int userNumbers)
			throws InvalidConfigurationException {

		Gene[] gene = createGeneWithGui(userNumbers);
		Chromosome chromosome = new Chromosome(getConfiguration(), gene);
		if (!(getConfiguration().isLocked())) {
			getConfiguration().setSampleChromosome(chromosome);
		}
		getConfiguration().setPopulationSize(10);

		Genotype population = Genotype
				.randomInitialGenotype(getConfiguration());
		return population.getChromosomes();

	}

	public static Gene[] createGeneWithGui(int userNumbers)
			throws InvalidConfigurationException {

		List<JMeterTreeNode> list = FindService
				.searchWorkLoadControllerWithGui();

		int userNumberMax = userNumbers;
		Gene[] wgenes = new Gene[12];
		wgenes[0] = new IntegerGene(getConfiguration(), 0,
				WorkLoad.getTypes().length - 1);
		wgenes[1] = new IntegerGene(getConfiguration(), 1, userNumberMax);
		wgenes[2] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[3] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[4] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[5] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[6] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[7] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[8] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[9] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[10] = new IntegerGene(getConfiguration(), 0, list.size());
		wgenes[11] = new IntegerGene(getConfiguration(), 0, list.size());

		return wgenes;
	}

}
