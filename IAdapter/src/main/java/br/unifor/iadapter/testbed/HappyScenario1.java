package br.unifor.iadapter.testbed;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

public class HappyScenario1 extends AbstractJavaSamplerClient {
	


	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		
		
		


		int numberOfThreads = JMeterContextService.getNumberOfThreads();
		SampleResult sampleResult = new SampleResult();

		sampleResult.sampleStart();
		sampleResult.setSuccessful(true);
		
		if (numberOfThreads < 10){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (numberOfThreads >= 10 && numberOfThreads < 20) {

			try {
				Thread.sleep(10*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		
		if (numberOfThreads >= 20 && numberOfThreads < 30) {

			try {
				Thread.sleep(20*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 30 && numberOfThreads < 40) {

			try {
				Thread.sleep(30*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 30 && numberOfThreads < 40) {

			try {
				Thread.sleep(30*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 40 && numberOfThreads < 50) {

			try {
				Thread.sleep(40*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 50 && numberOfThreads < 60) {

			try {
				Thread.sleep(50*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 60 && numberOfThreads < 70) {

			try {
				Thread.sleep(60*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 70 && numberOfThreads < 80) {

			try {
				Thread.sleep(70*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 80 && numberOfThreads < 90) {

			try {
				Thread.sleep(80*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}
		if (numberOfThreads >= 90) {

			try {
				Thread.sleep(90*numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();
		
			}
			sampleResult.setResponseCode("200");
			
			sampleResult.setSuccessful(true);
			
			
		}		

		sampleResult.sampleEnd();

	
		
	
		return sampleResult;
		

	}

	


}