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

			String a = "A";
			String b = "B";
			String c = "C";
			
			SampleResult sampleResult = new SampleResult();

			sampleResult.sampleStart();
			sampleResult.setSuccessful(true);

			if (scenario.equals("A")) {
				WorkLoadThreadGroup.getScenariosSimulation().add(a);
			}

			if (scenario.equals("B")) {
				WorkLoadThreadGroup.getScenariosSimulation().add(b);
			}
			if (scenario.equals("C")) {
				while(WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(b) );
				WorkLoadThreadGroup.getScenariosSimulation().add(c);
			}	
			
			sampleResult.setResponseCode("200");
				
			sampleResult.setSuccessful(true);			
			
			
			WorkLoadThreadGroup.getScenariosSimulation().remove(scenario);
			

			sampleResult.sampleEnd();	
			
		
			return sampleResult;
			

		}

	


}
