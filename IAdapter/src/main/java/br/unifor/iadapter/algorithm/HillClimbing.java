package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.WorkLoadUtil;

public class HillClimbing extends AbstractAlgorithm {

	private static List<WorkLoad> neighboors = new ArrayList<WorkLoad>();

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> listWorkload, int populationSize, List<TestElement> nodes,
			WorkLoadThreadGroup tg) {

		List<WorkLoad> neighboorsPercorred = new ArrayList<WorkLoad>();
		this.setListWorkLoads(listWorkload);

		if (neighboors.size() == 0) {
			WorkLoad newNeighboors = WorkLoadUtil.getNeighBorHoodHC(this.getListWorkLoads().get(0), nodes,
					Integer.valueOf(tg.getThreadNumberMax()), tg.getGeneration());
			neighboors.add(newNeighboors);
			System.out.println("HC neighboor"+newNeighboors);

			for (int i = 0; i < populationSize - 1; i++) {

				newNeighboors = WorkLoadUtil.getNeighBorHoodHC(this.getListWorkLoads().get(0), nodes,
						Integer.valueOf(tg.getThreadNumberMax()), tg.getGeneration());
				neighboors.add(newNeighboors);
				System.out.println("HC neighboor"+newNeighboors);
			}
			
		} else {
			for (int i = 0; i < populationSize; i++) {
				if (i < neighboors.size()) {
					WorkLoad neighboor = neighboors.get(i);
					neighboorsPercorred.add(neighboor);
				}

			}
			for (int i = 0; i < populationSize; i++) {
				if (neighboors.size() > 0) {
					neighboors.remove(0);
				}

			}

		}

		// TODO Auto-generated method stub
		return neighboorsPercorred;
	}

	public HillClimbing() {
		setMethodName("HC");
	}

}
