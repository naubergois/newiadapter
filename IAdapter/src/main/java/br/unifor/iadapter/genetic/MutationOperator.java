package br.unifor.iadapter.genetic;

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

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class MutationOperator {
	/** String containing the CVS revision. Read out via reflection! */
	private final static String CVS_REVISION = "$Revision: 1.45 $";

	public static WorkLoad mutantWorkload(WorkLoad workload, int n,
			List<TestElement> nodes, int maxUsers, int generation,
			int mutantionProbability) {
		Random random = new Random();

		int evolute = random.nextInt(10);
		WorkLoad workloadClone = workload.clone();
		if (evolute < mutantionProbability) {

			for (int i = 0; i <= n; i++) {
				workloadClone = WorkLoadUtil.getNeighBorHoodMutant(
						workloadClone, nodes, maxUsers);

			}
			workloadClone.setName("Mutant:G" + generation + ":"
					+ workloadClone.getName());
		}
		return workloadClone;

	}

}
