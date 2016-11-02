/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

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

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
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

	public static List<IChromosome> crossOverPopulation(
			List<Chromosome> listPopulation, List<Chromosome> list)
			throws InvalidConfigurationException {

		List<IChromosome> newlist = new ArrayList<IChromosome>();

		if (listPopulation.size() > 0) {

			getConfiguration().setPopulationSize(listPopulation.size());

			CrossoverOperator cs = new CrossoverOperator(getConfiguration(), 1);
			newlist = cs.operateBestIndividuals(listPopulation, list);
		}
		return newlist;

	}

	public static List<WorkLoad> mutationPopulation(AbstractAlgorithm algorithm,List<WorkLoad> list, List<String> nodes,int mutantProbability,int population,int generation,int maxUsers) {
		
		
	
		List<WorkLoad> mutant = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad2 : list) {		
			
			mutant.add(MutationOperator.mutantWorkload(algorithm, workLoad2, population, nodes, maxUsers,
					generation, Integer.valueOf(mutantProbability)));
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

		if (list != null) {
			Gene[] wgenes = new Gene[24];

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
			wgenes[22] = new IntegerGene(getConfiguration());
			wgenes[22].setAllele(workload.getMemory());
			wgenes[23] = new IntegerGene(getConfiguration());
			wgenes[23].setAllele(workload.getCpuShare());
			return wgenes;
		} else {
			return null;
		}

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
			if (genes != null) {
				Chromosome chromossome = new Chromosome(getConfiguration());

				chromossome.setGenes(genes);
				chromossome.setFitnessValueDirectly(workload.getFit());
				listC.add(chromossome);
			}
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
			int generation,int maxMemory,int maxCpuShare) throws InvalidConfigurationException {

		List<WorkLoad> workLoads = new ArrayList<WorkLoad>();
		for (int i = 0; i <= 10; i++) {

			WorkLoad workload = WorkLoadUtil.createWorkLoadTemperatureWithGui(
					generation, userNumbers,maxMemory,maxCpuShare);
			workLoads.add(workload);
		}

		return workLoads;
	}

	public static List<WorkLoad> createWorkLoadsFromChromossomeWithGui(AbstractAlgorithm algorithm,
			int userNumbers, int generation, int population,int memory,int cpuShare)
			throws InvalidConfigurationException {
		IChromosome[] list = createPopulation(userNumbers, population,memory,cpuShare);
		List<WorkLoad> workLoads = new ArrayList<WorkLoad>();
		for (IChromosome iChromosome : list) {
			Gene[] gene = iChromosome.getGenes();
			WorkLoad workload = WorkLoadUtil.createWorkLoadWithGui(algorithm,gene,
					generation);
			workLoads.add(workload);
		}
		conf = null;
		return workLoads;
	}

	public static IChromosome[] createPopulation(int userNumbers,
			int populationSize,int memory,int cpuShare) throws InvalidConfigurationException {

		Gene[] gene = createGeneWithGui(userNumbers,memory,cpuShare);
		Chromosome chromosome = new Chromosome(getConfiguration(), gene);
		if (!(getConfiguration().isLocked())) {
			getConfiguration().setSampleChromosome(chromosome);
		}
		getConfiguration().setPopulationSize(populationSize);

		Genotype population = Genotype
				.randomInitialGenotype(getConfiguration());
		return population.getChromosomes();

	}

	public static Gene[] createGeneWithGui(int userNumbers,int memory,int cpuShare)
			throws InvalidConfigurationException {

		List<JMeterTreeNode> list = FindService
				.searchWorkLoadControllerWithGui();

		int userNumberMax = userNumbers;
		int userMaxGene = userNumberMax / 10;
		Gene[] wgenes = new Gene[24];
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
		wgenes[22] = new IntegerGene(getConfiguration(), 1, memory);
		wgenes[23] = new IntegerGene(getConfiguration(), 1, cpuShare);

		return wgenes;
	}

}
