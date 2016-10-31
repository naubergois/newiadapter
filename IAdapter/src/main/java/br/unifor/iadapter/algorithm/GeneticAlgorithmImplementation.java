package br.unifor.iadapter.algorithm;

import java.util.List;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.genetic.GeneticAlgorithm;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class GeneticAlgorithmImplementation extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list,int populationSize,List<TestElement> listElement,WorkLoadThreadGroup tg) {

		// TODO Auto-generated method stub
		List<WorkLoad> listBest=GeneticAlgorithm.newGeneration(tg, this.getListWorkLoads(), listElement, true);
		JMeterPluginsUtils.listWorkLoadToCollectionProperty(listBest, WorkLoadThreadGroup.DATA_PROPERTY);
		return listBest;
	}
	
	
	public GeneticAlgorithmImplementation() {
	 this.setMethodName("GA");
	}

}
