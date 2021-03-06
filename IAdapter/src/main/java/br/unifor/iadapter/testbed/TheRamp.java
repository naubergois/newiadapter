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
					
					TheRamp.number=0;
					WorkLoadThreadGroup.getGlobalVariables().put("ramp_number", String.valueOf(number));
				
				}
				
			
			
			
			
			
			

			SampleResult sampleResult = new SampleResult();
			sampleResult.sampleStart();
			sampleResult.setSuccessful(true);
			int numberOfThreads = JMeterContextService.getNumberOfThreads();
			
			TheRamp.number=TheRamp.number+increment;	
			
			
			
			try {
				Thread.sleep(numberOfThreads*10);
				Thread.sleep(number*10);
				System.out.println("Number is "+number);
				System.out.println("increment is "+number);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			


			sampleResult.sampleEnd();

			
			
		
			return sampleResult;
			

		}

	


}
