package br.unifor.iadapter.testbed;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class FindError extends AbstractJavaSamplerClient {

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

		if (WorkLoadThreadGroup.getScenariosSimulation().contains(a) && WorkLoadThreadGroup.getScenariosSimulation().contains(b) && numberOfThreads > 10 && numberOfThreads < 15) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Scenarios" + WorkLoadThreadGroup.getScenariosSimulation());
			sampleResult.setSuccessful(false);
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sampleResult.setSuccessful(true);
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

		sampleResult.setResponseCodeOK();
		sampleResult.setResponseMessageOK();
		
	
		return sampleResult;
		

	}

}
