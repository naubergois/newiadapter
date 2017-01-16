//  NSGAII.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package br.unifor.iadapter.algorithm;

import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.genetic.GeneticAlgorithm;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;
import jmetal.util.Distance;

public class NSGAII extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {

		Distance distance = new Distance();

		List<WorkLoad> listBest = GeneticAlgorithm.newGeneration(this, generation, list, testCases, true, maxUsers,
				mutantProbability, populationSize, bestIndividuals, collaborative, testPlan, script);

		JMeterPluginsUtils.listWorkLoadToCollectionProperty(listBest, WorkLoadThreadGroup.DATA_PROPERTY);

		SolutionSet population = new SolutionSet();

		for (int i = 0; i < populationSize; i++) {

			population.add(listBest.get(i));
		} // for

		// Ranking the union
		Ranking ranking = new Ranking(population);

		int remain = populationSize;
		int index = 0;
		SolutionSet front = null;
		population.clear();

		// Obtain the next front
		front = ranking.getSubfront(index);

		return listBest;

	}
} // NSGA-II
