package br.unifor.iadapter.algorithm;

import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.genetic.GeneticAlgorithm;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class GeneticAlgorithmImplementation extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script) {

		List<WorkLoad> listBest = GeneticAlgorithm.newGeneration(this, generation, list, testCases, true, maxUsers,
				mutantProbability, populationSize, bestIndividuals, collaborative, testPlan, script);

		JMeterPluginsUtils.listWorkLoadToCollectionProperty(listBest, WorkLoadThreadGroup.DATA_PROPERTY);
		return listBest;
	}

	public GeneticAlgorithmImplementation() {
		this.setMethodName("GA");
	}

}
