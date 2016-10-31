package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.sa.SimulateAnnealing;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class SAAlgorithm extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list,int populationSize,List<TestElement> listElement,WorkLoadThreadGroup tg) {
		List<WorkLoad> newList = new ArrayList<WorkLoad>();
		if (this.getListWorkLoads().size() > 0) {
			tg.temperature = SimulateAnnealing.sa(tg.temperature, this.getListWorkLoads(),
					Integer.valueOf(tg.getThreadNumberMax()), newList, tg.getGeneration(), tg, listElement);
		}
		return newList;
	}

	public SAAlgorithm() {
		this.setMethodName("SA");
	}

}
