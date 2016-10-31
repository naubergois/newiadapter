package br.unifor.iadapter.algorithm;

import java.util.List;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.tabu.TabuSearch;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;
import br.unifor.iadapter.util.WorkLoadUtil;

public class TabuAlgorithm extends AbstractAlgorithm {
	
	

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list,int populationSize,List<TestElement> listElement,WorkLoadThreadGroup tg) {
		
		this.setListWorkLoads(TabuSearch.verify(this.getListWorkLoads()));

		if (this.getListWorkLoads().size() > 0) {

			TabuSearch.addTabuTable(this.getListWorkLoads().get(0));
			
			System.out.println(TabuSearch.getTabuTable());
			System.out.println(TabuSearch.getTabuExpires());

			List<WorkLoad> neighboors = WorkLoadUtil.getNeighborHood(this.getListWorkLoads().get(0), listElement, tg);
			List<WorkLoad> returnList=TabuSearch.verify(neighboors);
		
			System.out.println("neighboors"+returnList.size());
			return returnList;

		}

		// TODO Auto-generated method stub
		return null;
	}
	
	public TabuAlgorithm(){
		this.setMethodName("TS");
	}

}
