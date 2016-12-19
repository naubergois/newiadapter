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

import java.sql.SQLException;
import java.util.List;

import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jorphan.collections.ListedHashTree;
import org.jgap.Chromosome;
import org.jgap.IChromosome;
import org.jgap.Population;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;
import br.unifor.iadapter.util.WorkLoadUtil;

public class GeneticAlgorithm {

	public static List<WorkLoad> newGeneration(AbstractAlgorithm algorithm, int generation,
			List<WorkLoad> listOldPopulation, List<String> testCases, boolean gui, int maxUsers, int mutantProbability,
			int populationNumber, int bestIndividualsNumber, boolean collaborative, String testPlan,
			ListedHashTree threadGroupTree) {

		try {

			if (gui) {
				List<JMeterTreeNode> treeNode = FindService.searchWorkLoadControllerWithGui();
				testCases = WorkLoadUtil.getScenarios(treeNode);
			}

			listOldPopulation = MySQLDatabase.listPopulationGA(algorithm, testPlan, generation - 1);

			System.out.println("Population " + listOldPopulation);

			if (populationNumber > listOldPopulation.size()) {
				populationNumber = listOldPopulation.size();
			}
			if (bestIndividualsNumber > listOldPopulation.size()) {
				bestIndividualsNumber = listOldPopulation.size();
			}

			Population population = GeneWorkLoad.population(listOldPopulation, threadGroupTree, gui);

			List<Chromosome> bestI = GeneWorkLoad.selectBestIndividualsList(population, bestIndividualsNumber);

			List<WorkLoad> workLoads = null;
			if ((collaborative) || (generation == 1)) {
				workLoads = MySQLDatabase.listBESTWorkloadAllPopulationSize(testPlan, populationNumber);

			} else {
				workLoads = MySQLDatabase.listBESTWorkloadPopulationSize(algorithm, testPlan, populationNumber);
				if (workLoads.size() == 0) {
					System.out.println("WorkLoad zero ");
					workLoads = MySQLDatabase.listBESTWorkloadAllPopulationSize(testPlan, populationNumber);
					System.out.println("WorkLoad size " + workLoads.size());
				}
			}
			
			try {
				MySQLDatabase.insertOBudget("GA",(int) workLoads.get(0).getFit());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			System.out.println("Best:" + workLoads);

			List<Chromosome> bestP = GeneWorkLoad.getChromossomes(workLoads, threadGroupTree, gui);

			System.out.println("Best Chromossome:" + bestP);

			List<IChromosome> newList = null;

			newList = GeneWorkLoad.crossOverPopulation(bestP, bestI);

			System.out.println("After crossover:" + newList);

			List<WorkLoad> listWorkloads = JMeterPluginsUtils.getListWorkLoadFromPopulationTestPlan(algorithm,
					threadGroupTree, newList, gui, generation);

			return GeneWorkLoad.mutationPopulation(algorithm, listWorkloads, testCases, mutantProbability,
					populationNumber, generation, maxUsers);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
