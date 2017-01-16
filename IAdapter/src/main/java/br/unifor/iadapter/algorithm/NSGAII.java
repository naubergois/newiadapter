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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;
import org.jgap.Chromosome;
import org.jgap.IChromosome;
import org.jgap.Population;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.genetic.GeneWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class NSGAII extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {

		System.out.println("NSGAII start");

		Distance distance = new Distance();

		List<WorkLoad> parentsList = new ArrayList<WorkLoad>();

		try {
			parentsList = MySQLDatabase.listWorkLoadsForNewGenerationByMethodAllGenerations(testPlan, this,
					populationSize);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SolutionSet population = new SolutionSet();
		population.setCapacity(populationSize);

		if (populationSize > list.size()) {
			populationSize = list.size();
		}

		for (int i = 0; i <= populationSize - 1; i++) {

			population.add(list.get(i));
		}

		population.solutionsList_.addAll(parentsList);

		// for

		// Ranking the union
		Ranking ranking = new Ranking(population);

		int remain = populationSize;
		int index = 0;
		SolutionSet front = null;
		population.clear();

		// Obtain the next front
		front = ranking.getSubfront(index);

		System.out.println("NSGAII test remain");

		while ((remain > 0) && (remain >= front.size())) {
			// Assign crowding distance to individuals
			distance.crowdingDistanceAssignment(front, WorkLoadThreadGroup.getWeights().size());
			// Add the individuals of this front
			for (int k = 0; k < front.size(); k++) {
				population.add(front.get(k));
			} // for

			// Decrement remain
			remain = remain - front.size();

			// Obtain the next front
			index++;
			if (remain > 0) {
				front = ranking.getSubfront(index);
			} // if
		} // while

		if (remain > 0) { // front contains individuals to insert
			distance.crowdingDistanceAssignment(front, WorkLoadThreadGroup.getWeights().size());
			front.sort(new CrowdingComparator());
			for (int k = 0; k < remain; k++) {
				population.add(front.get(k));
			} // for

			remain = 0;
		} // if

		Ranking ranking1 = new Ranking(population);

		SolutionSet set = ranking1.getSubfront(0);

		try {

			//Population populationC = GeneWorkLoad.population(set.solutionsList_, script, false);

			List<Chromosome> bestI = GeneWorkLoad.getChromossomes(set.solutionsList_, script, false);

			List<Chromosome> bestP = GeneWorkLoad.getChromossomes(list, script, false);

			List<IChromosome> newList = null;

			newList = GeneWorkLoad.crossOverPopulation(bestP, bestI);

			List<WorkLoad> listWorkloads = JMeterPluginsUtils.getListWorkLoadFromPopulationTestPlan(this, script,
					newList, false, generation);

			List<WorkLoad> mutations = GeneWorkLoad.mutationPopulation(this, listWorkloads, testCases,
					mutantProbability, populationSize, generation, maxUsers);

			System.out.println("Set " + mutations);

			return mutations;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
} // NSGA-II
