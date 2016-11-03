package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class CustomParticleSwarm extends AbstractAlgorithm{
	
	private static HashMap<String,WorkLoad> leaderArchive=new HashMap<String,WorkLoad>();

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime) {
		
		List<WorkLoad> neighborhood=new ArrayList<WorkLoad>();
		
		for (WorkLoad workLoad : list) {
			String[] nameSplit=workLoad.getName().split(":");
			String name=nameSplit[nameSplit.length-1];
			if(workLoad.getTotalErrors()>0){				
				if (!(leaderArchive.containsKey(name))){
					leaderArchive.put(name, workLoad);
				}
			}
			if ((workLoad.getPercentile90()<(0.7*maxResponseTime))&&(workLoad.getPercentile90()<(1.5*maxResponseTime))){
				if (!(leaderArchive.containsKey(name))){
					leaderArchive.put(name, workLoad);
				}
			}
			System.out.println("Leader Archive Particle Swarm:"+leaderArchive);
			List<WorkLoad> leaders=new ArrayList<WorkLoad>(leaderArchive.values());
					
			neighborhood=NeighborhoodUtil.getNeighBorHoodsAllList(this, leaders, populationSize, testCases, generation, maxUsers, testPlan);
 			
		}
		
		return neighborhood;
	}
	
	public CustomParticleSwarm(){
		setMethodName("CPS");
	}

}
