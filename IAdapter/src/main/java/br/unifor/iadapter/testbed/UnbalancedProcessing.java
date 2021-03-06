package br.unifor.iadapter.testbed;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class UnbalancedProcessing extends AbstractJavaSamplerClient {
	
	

		public Arguments getDefaultParameters() {
			Arguments params = new Arguments();
			params.addArgument("Scenario", "A");
			return params;
		}

		

		@Override
		public SampleResult runTest(JavaSamplerContext arg0) {
			
			
			

			String scenario = arg0.getParameter("Scenario");
			
			WorkLoadThreadGroup.getScenariosSimulation().add(scenario);

			//System.out.println(scenario);
			System.out.println(WorkLoadThreadGroup.getScenariosSimulation());
			String a = "A";
			String b = "B";
			String c = "C";
			int numberOfThreads = JMeterContextService.getNumberOfThreads();
			
			SampleResult sampleResult = new SampleResult();

			sampleResult.sampleStart();
			sampleResult.setSuccessful(true);
			if (scenario.equals("A")) {
				try {				
					Thread.sleep((long) 0.9*numberOfThreads);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if (scenario.equals("B")) {
				try {				
					Thread.sleep(5*numberOfThreads);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if (scenario.equals("C")) {
				int count=0;
				while(WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(b) ){
					try {
						System.out.println("Waiting for a and b "+count);
						Thread.sleep(1);
						count++;
						if (count>20){
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				WorkLoadThreadGroup.getScenariosSimulation().remove(scenario);

			}	
			
			if (scenario.equals("D")) {
				
				WorkLoadThreadGroup.getScenariosSimulation().remove("A");
				WorkLoadThreadGroup.getScenariosSimulation().remove("B");

			}	
			
			sampleResult.setResponseCode("200");
				
			sampleResult.setSuccessful(true);			
			
			
			
			

			sampleResult.sampleEnd();	
			
		
			return sampleResult;
			

		}

	


}
