package br.unifor.iadapter.testbed;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class TheRamp extends AbstractJavaSamplerClient {
	
		static int number;
	
	

		public Arguments getDefaultParameters() {
			Arguments params = new Arguments();
			params.addArgument("increment", "1");
			return params;
		}

		

		@Override
		public SampleResult runTest(JavaSamplerContext arg0) {
			
			int increment =Integer.valueOf( arg0.getParameter("increment"));
			
			if (!WorkLoadThreadGroup.getGlobalVariables().containsKey("ramp_number")){
			
				number=0;
			
			}
			
			
			

			SampleResult sampleResult = new SampleResult();
			sampleResult.sampleStart();
			sampleResult.setSuccessful(true);
			int numberOfThreads = JMeterContextService.getNumberOfThreads();
			
			
			try {
				Thread.sleep(number*100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			number=number+increment;


			sampleResult.sampleEnd();

			WorkLoadThreadGroup.getGlobalVariables().put("ramp_number", String.valueOf(number));
			
		
			return sampleResult;
			

		}

	


}
