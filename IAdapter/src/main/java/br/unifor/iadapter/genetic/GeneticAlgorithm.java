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

			List<WorkLoad> workLoads = null;
			if ((tg.getCollaborative()) || (tg.getGeneration() == 1)) {
				workLoads = MySQLDatabase.listBESTWorkloadAllPopulationSize(
						tg.getName(), tg.getPopulationSize());

			} else {
				workLoads = MySQLDatabase
						.listBESTWorkloadGeneticPopulationSize(tg.getName(),
								tg.getPopulationSize());
			}
			
			System.out.println("Best:"+workLoads);

			List<Chromosome> bestP = GeneWorkLoad.getChromossomes(workLoads,
					tg.getTree(), gui);
			
			System.out.println("Best Chromossome:"+bestP);

			List<IChromosome> newList = null;

			newList = GeneWorkLoad.crossOverPopulation(bestP, bestI);
			
			System.out.println("After crossover:"+newList);

			List<WorkLoad> listWorkloads = JMeterPluginsUtils
					.getListWorkLoadFromPopulationTestPlan(newList, tg, gui,tg.getGeneration()+1);

			return GeneWorkLoad.mutationPopulation(tg, listWorkloads, nodes,Integer.valueOf(tg.getMaxMemory()),Integer.valueOf(tg.getMaxCpuShare()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
