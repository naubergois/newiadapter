package br.unifor.iadapter.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.ListedHashTree;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.WorkLoadUtil;

public class GeneWorkLoad {

	private static Configuration conf;

	public static int indexOf(List<TestElement> list, String name) {
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			TestElement element = list.get(i);
			if (element.getName().equals(name)) {
				index = i;
			}
		}
		return index;
	}

	public static List<IChromosome> crossOverPopulation(Population population,
			List<Chromosome> list) throws InvalidConfigurationException {
		getConfiguration().setPopulationSize(population.size());

		List<IChromosome> newlist = new ArrayList<IChromosome>();
		CrossoverOperator cs = new CrossoverOperator(getConfiguration(), 1);
		newlist = cs.operateBestIndividuals(population.getChromosomes(), list);
		return newlist;

	}

	public static List<WorkLoad> mutationPopulation(WorkLoadThreadGroup tg,
			List<WorkLoad> list, List<TestElement> nodes) {
		int maxUsers = Integer.valueOf(tg.getNumberOfThreads());
		int generation = tg.getGeneration();
		MutationOperator cs = new MutationOperator();
		List<WorkLoad> mutant = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad2 : list) {
			mutant.add(cs.mutantWorkload(workLoad2, 3, nodes, maxUsers,
					generation, Integer.valueOf(tg.getMutantProbability())));
		}

		return mutant;

	}

	public static Gene[] geneFromWorkLoad(WorkLoad workload,
			ListedHashTree threadGroupTree, boolean gui)
			throws InvalidConfigurationException {
		List<TestElement> list = null;
		if (!(gui)) {
			list = FindService
					.searchWorkLoadControllerWithNoGui(threadGroupTree);
		} else {

			List<JMeterTreeNode> nodes = FindService
					.searchWorkLoadControllerWithGui();
			list = WorkLoadThreadGroup.testNodeToTestElement(nodes);
		}
		Gene[] wgenes = new Gene[22];

		wgenes[0] = new IntegerGene(getConfiguration());
		wgenes[0].setAllele(Arrays.asList(WorkLoad.getTypes()).indexOf(
				workload.getType()));
		wgenes[1] = new IntegerGene(getConfiguration());
		wgenes[1].setAllele(workload.getNumThreads());
		wgenes[2] = new IntegerGene(getConfiguration());
		wgenes[2].setAllele(indexOf(list, workload.getFunction1()));
		wgenes[3] = new IntegerGene(getConfiguration());
		wgenes[3].setAllele(workload.getUsers1());
		wgenes[4] = new IntegerGene(getConfiguration());
		wgenes[4].setAllele(indexOf(list, workload.getFunction2()));
		wgenes[5] = new IntegerGene(getConfiguration());
		wgenes[5].setAllele(workload.getUsers2());
		wgenes[6] = new IntegerGene(getConfiguration());
		wgenes[6].setAllele(indexOf(list, workload.getFunction3()));
		wgenes[7] = new IntegerGene(getConfiguration());
		wgenes[7].setAllele(workload.getUsers3());
		wgenes[8] = new IntegerGene(getConfiguration());
		wgenes[8].setAllele(indexOf(list, workload.getFunction4()));
		wgenes[9] = new IntegerGene(getConfiguration());
		wgenes[9].setAllele(workload.getUsers4());
		wgenes[10] = new IntegerGene(getConfiguration());
		wgenes[10].setAllele(indexOf(list, workload.getFunction5()));
		wgenes[11] = new IntegerGene(getConfiguration());
		wgenes[11].setAllele(workload.getUsers5());
		wgenes[12] = new IntegerGene(getConfiguration());
		wgenes[12].setAllele(indexOf(list, workload.getFunction6()));
		wgenes[13] = new IntegerGene(getConfiguration());
		wgenes[13].setAllele(workload.getUsers6());
		wgenes[14] = new IntegerGene(getConfiguration());
		wgenes[14].setAllele(indexOf(list, workload.getFunction7()));
		wgenes[15] = new IntegerGene(getConfiguration());
		wgenes[15].setAllele(workload.getUsers7());
		wgenes[16] = new IntegerGene(getConfiguration());
		wgenes[16].setAllele(indexOf(list, workload.getFunction8()));
		wgenes[17] = new IntegerGene(getConfiguration());
		wgenes[17].setAllele(workload.getUsers8());
		wgenes[18] = new IntegerGene(getConfiguration());
		wgenes[18].setAllele(indexOf(list, workload.getFunction9()));
		wgenes[19] = new IntegerGene(getConfiguration());
		wgenes[19].setAllele(workload.getUsers9());
		wgenes[20] = new IntegerGene(getConfiguration());
		wgenes[20].setAllele(indexOf(list, workload.getFunction10()));
		wgenes[21] = new IntegerGene(getConfiguration());
		wgenes[21].setAllele(workload.getUsers10());
		return wgenes;

	}

	public static Population selectBestIndividuals(Population population,
			int index) throws InvalidConfigurationException {
		Population population2 = new Population(getConfiguration());
		population.determineFittestChromosomes(index);
		List<Chromosome> list = population.determineFittestChromosomes(index);
		for (Chromosome chromosome : list) {
			population2.addChromosome(chromosome);
		}
		return population2;
	}

	@SuppressWarnings("unchecked")
	public static List<Chromosome> selectBestIndividualsList(
			Population population, int index)
			throws InvalidConfigurationException {

		return population.determineFittestChromosomes(index);

	}

	public static Population population(List<WorkLoad> list,
			ListedHashTree threadGroupTree, boolean gui)
			throws InvalidConfigurationException {
		Population population = new Population(getConfiguration());
		List<Chromosome> listC = getChromossomes(list, threadGroupTree, gui);

		for (Chromosome chromosome : listC) {
			population.addChromosome(chromosome);
		}
		return population;
	}

	public static List<Chromosome> getChromossomes(List<WorkLoad> list,
			ListedHashTree threadGroupTree, boolean gui)
			throws InvalidConfigurationException {
		List<Chromosome> listC = new ArrayList<Chromosome>();
		for (WorkLoad workload : list) {
			Gene[] genes = geneFromWorkLoad(workload, threadGroupTree, gui);
			Chromosome chromossome = new Chromosome(getConfiguration());
			chromossome.setGenes(genes);
			chromossome.setFitnessValueDirectly(workload.getFit());
			listC.add(chromossome);
		}

		return listC;
	}

	public static Configuration getConfiguration()
			throws InvalidConfigurationException {
		if (conf == null) {

			Configuration.reset();

			conf = new DefaultConfiguration();

			conf.setFitnessFunction(new FunctionFitness());
		}
		return conf;
	}

	public static List<WorkLoad> createWorkLoadsSAWithGui(int userNumbers,
			int generation) throws InvalidConfigurationException {

		List<WorkLoad> workLoads = new ArrayList<WorkLoad>();
		for (int i = 0; i <= 10; i++) {

			WorkLoad workload = WorkLoadUtil.createWorkLoadTemperatureWithGui(
					generation, userNumbers);
			workLoads.add(workload);
		}

		return workLoads;
	}

	public static List<WorkLoad> createWorkLoadsFromChromossomeWithGui(
			int userNumbers, int generation, int population)
			throws InvalidConfigurationException {
		IChromosome[] list = createPopulation(userNumbers, population);
		List<WorkLoad> workLoads = new ArrayList<WorkLoad>();
		for (IChromosome iChromosome : list) {
			Gene[] gene = iChromosome.getGenes();
			WorkLoad workload = WorkLoadUtil.createWorkLoadWithGui(gene,
					generation);
			workLoads.add(workload);
		}
		conf = null;
		return workLoads;
	}

	public static IChromosome[] createPopulation(int userNumbers,
			int populationSize) throws InvalidConfigurationException {

		Gene[] gene = createGeneWithGui(userNumbers);
		Chromosome chromosome = new Chromosome(getConfiguration(), gene);
		if (!(getConfiguration().isLocked())) {
			getConfiguration().setSampleChromosome(chromosome);
		}
		getConfiguration().setPopulationSize(populationSize);

		Genotype population = Genotype
				.randomInitialGenotype(getConfiguration());
		return population.getChromosomes();

	}

	public static Gene[] createGeneWithGui(int userNumbers)
			throws InvalidConfigurationException {

		List<JMeterTreeNode> list = FindService
				.searchWorkLoadControllerWithGui();

		int userNumberMax = userNumbers;
		int userMaxGene = userNumberMax / 10;
		Gene[] wgenes = new Gene[22];
		wgenes[0] = new IntegerGene(getConfiguration(), 0,
				WorkLoad.getTypes().length - 1);
		wgenes[1] = new IntegerGene(getConfiguration(), 1, userNumberMax);
		wgenes[2] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[3] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[4] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[5] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[6] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[7] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[8] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[9] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[10] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[11] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[12] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[13] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[14] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[15] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[16] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[17] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[18] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[19] = new IntegerGene(getConfiguration(), 1, userMaxGene);
		wgenes[20] = new IntegerGene(getConfiguration(), 0, list.size() - 1);
		wgenes[21] = new IntegerGene(getConfiguration(), 1, userMaxGene);

		return wgenes;
	}

}
