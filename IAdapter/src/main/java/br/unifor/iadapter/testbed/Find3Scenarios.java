package br.unifor.iadapter.testbed;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class Find3Scenarios extends AbstractJavaSamplerClient {

		public Arguments getDefaultParameters() {
			Arguments params = new Arguments();
			params.addArgument("Scenario", "A");
			return params;
		}

		

		@Override
		public SampleResult runTest(JavaSamplerContext arg0) {
			
			
			

			String scenario = arg0.getParameter("Scenario");

			String a = "A";
			String b = "B";
			String c = "C";

			if (scenario.equals("A")) {
				WorkLoadThreadGroup.getScenariosSimulation().add(a);
			}

			if (scenario.equals("B")) {
				WorkLoadThreadGroup.getScenariosSimulation().add(b);
			}
			if (scenario.equals("C")) {
				WorkLoadThreadGroup.getScenariosSimulation().add(c);
			}

			int numberOfThreads = JMeterContextService.getNumberOfThreads();
			SampleResult sampleResult = new SampleResult();

			sampleResult.sampleStart();
			sampleResult.setSuccessful(true);

			if (WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(c) && numberOfThreads > 10 && numberOfThreads < 15) {

				try {
					Thread.sleep(100*numberOfThreads);
				} catch (InterruptedException e) {
					e.printStackTrace();
			
				}
				sampleResult.setResponseCode("200");
				
				sampleResult.setSuccessful(true);
				
				
			}
			if (WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(c) && numberOfThreads > 15 && numberOfThreads < 25) {

				try {
					Thread.sleep(200*numberOfThreads);
				} catch (InterruptedException e) {
					e.printStackTrace();
			
				}
				sampleResult.setResponseCode("500");
				
				sampleResult.setResponseMessage("Error on server . JDBC problem ");
				
				sampleResult.setSuccessful(false);
			
			}
			if (WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(c) && numberOfThreads > 25 ) {

				try {
					Thread.sleep(500*numberOfThreads);
				} catch (InterruptedException e) {
					e.printStackTrace();
			
				}
				sampleResult.setResponseCode("404");
				
				sampleResult.setResponseMessage("Not found ");
				
				sampleResult.setSuccessful(false);
				
			
			}
			if (WorkLoadThreadGroup.getScenariosSimulation().contains(c) && (!WorkLoadThreadGroup.getScenariosSimulation().contains(a))&& (!WorkLoadThreadGroup.getScenariosSimulation().contains(b)) && numberOfThreads > 10 && numberOfThreads < 15) {

				try {
					Thread.sleep(50*numberOfThreads);
				} catch (InterruptedException e) {
			
					e.printStackTrace();
				}
				
				sampleResult.setResponseCode("403");
				
				sampleResult.setResponseMessage("Security error ");
				
				sampleResult.setSuccessful(false);
				
				
				
			}
			
			
			
			else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
			
			if (scenario.equals("A")) {
				WorkLoadThreadGroup.getScenariosSimulation().remove(a);
			}

			if (scenario.equals("B")) {
				WorkLoadThreadGroup.getScenariosSimulation().remove(b);
			}
			if (scenario.equals("C")) {
				WorkLoadThreadGroup.getScenariosSimulation().remove(c);
			}


			sampleResult.sampleEnd();

		
			
		
			return sampleResult;
			

		}

	


}
