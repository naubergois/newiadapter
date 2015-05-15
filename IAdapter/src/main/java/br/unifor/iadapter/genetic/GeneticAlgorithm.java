package br.unifor.iadapter.genetic;

import java.util.List;

import org.apache.jmeter.testelement.TestElement;
import org.jgap.Chromosome;
import org.jgap.IChromosome;
import org.jgap.Population;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class GeneticAlgorithm {

	public static List<WorkLoad> newGeneration(WorkLoadThreadGroup tg,
			List<WorkLoad> listOldPopulation, List<TestElement> nodes,
			int maxUsers, int generationTrack) {

		try {

			Population population = GeneWorkLoad.population(listOldPopulation,
					tg.getTree());

			List<Chromosome> bestI = GeneWorkLoad.selectBestIndividualsList(
					population, Integer.valueOf(tg.getBestIndividuals()));

			List<IChromosome> newList = null;

			newList = GeneWorkLoad.crossOverPopulation(population, bestI);

			List<WorkLoad> listWorkloads = JMeterPluginsUtils
					.getListWorkLoadFromPopulationTestPlan(newList,
							tg.getTree(), tg.getGeneration(), tg.getName(),
							Integer.valueOf(tg.getThreadNumberMax()),
							new Integer(tg.getGenerationTrack()));

			return GeneWorkLoad.mutationPopulation(listWorkloads, nodes,
					maxUsers, generationTrack);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
