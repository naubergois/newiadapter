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

import java.lang.reflect.InvocationTargetException;

/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */

import java.util.List;
import java.util.Random;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class MutationOperator {
	/** String containing the CVS revision. Read out via reflection! */
	private final static String CVS_REVISION = "$Revision: 1.45 $";

	public static WorkLoad mutantWorkload(AbstractAlgorithm algorithm, WorkLoad workload, int population, List<String> nodes,
			int maxUsers, int generation, int mutantionProbability) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		Random random = new Random();

		int evolute = random.nextInt(10);
		WorkLoad workloadClone = workload.clone();
		if (evolute < mutantionProbability) {

			for (int i = 0; i <= population; i++) {

				workloadClone = NeighborhoodUtil.getNeighBorHoodMutant(algorithm, workloadClone, nodes, maxUsers,
						generation);

			}
			workloadClone.setName(algorithm.getMethodName()+":Mutant:" + generation + ":" + workloadClone.getName());

			

			workloadClone.setGeneration(generation);

		}
		return workloadClone;

	}

}
