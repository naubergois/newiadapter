package br.unifor.iadapter.testbed;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class TowerBabel extends AbstractJavaSamplerClient {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	public static String TEST_XML_STRING = "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";

	public static void main(String[] args) {
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
			String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			System.out.println(jsonPrettyPrintString);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		
		int numberOfThreads = JMeterContextService.getNumberOfThreads();
		SampleResult sampleResult = new SampleResult();

		sampleResult.sampleStart();
		sampleResult.setSuccessful(true);

		for (int i = 0; i <= 1000; i++) {
			try {
				JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
				String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
				//System.out.println(jsonPrettyPrintString);
			} catch (JSONException je) {
				System.out.println(je.toString());
			}
		}
		sampleResult.setResponseCode("200");
		
		sampleResult.setSuccessful(true);			
		
		
		
		

		sampleResult.sampleEnd();	
		
		return sampleResult;
	}
}
