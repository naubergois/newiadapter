package br.unifor.iadapter.testbed;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;

public class HappyScenario2 extends AbstractJavaSamplerClient {

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {

		int numberOfThreads = JMeterContextService.getNumberOfThreads();
		SampleResult sampleResult = new SampleResult();

		sampleResult.sampleStart();
		sampleResult.setSuccessful(true);

		if (numberOfThreads < 10) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (numberOfThreads >= 10 && numberOfThreads < 20) {

			try {
				Thread.sleep((long) 0.1 * numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
			sampleResult.setResponseCode("200");

			sampleResult.setSuccessful(true);

		}

		if (numberOfThreads >= 20) {

			try {
				Thread.sleep((long) 0.3 * numberOfThreads);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		

		}

		sampleResult.setResponseCode("200");

		sampleResult.setSuccessful(true);

		sampleResult.sampleEnd();

		return sampleResult;

	}

}
