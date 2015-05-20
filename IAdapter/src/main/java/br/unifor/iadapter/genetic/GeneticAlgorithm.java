package br.unifor.iadapter.genetic;

import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.testelement.TestElement;
import org.jgap.Chromosome;
import org.jgap.IChromosome;
import org.jgap.Population;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class GeneticAlgorithm {

	public static List<WorkLoad> newGeneration(WorkLoadThreadGroup tg,
			List<WorkLoad> listOldPopulation, List<TestElement> nodes,
			boolean gui) {

		try {

			if (gui) {
				List<JMeterTreeNode> treeNode = FindService
						.searchWorkLoadControllerWithGui();
				nodes = WorkLoadThreadGroup.testNodeToTestElement(treeNode);
			}

			Population population = GeneWorkLoad.population(listOldPopulation,
					tg.getTree(), gui);

			List<Chromosome> bestI = GeneWorkLoad.selectBestIndividualsList(
					population, Integer.valueOf(tg.getBestIndividuals()));

			List<WorkLoad> workLoads = MySQLDatabase
					.listWorstWorkloadGeneticPopulationSize(tg.getName(),
							tg.getPopulationSize());

			List<Chromosome> bestP = GeneWorkLoad.getChromossomes(workLoads,
					tg.getTree(), gui);

			List<IChromosome> newList = null;

			newList = GeneWorkLoad.crossOverPopulation(bestP, bestI);

			List<WorkLoad> listWorkloads = JMeterPluginsUtils
					.getListWorkLoadFromPopulationTestPlan(newList, tg, gui);

			return GeneWorkLoad.mutationPopulation(tg, listWorkloads, nodes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
