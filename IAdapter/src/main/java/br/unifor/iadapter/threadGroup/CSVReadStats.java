package br.unifor.iadapter.threadGroup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class CSVReadStats {

	public static HashMap getErrors() {
		return errors;
	}

	public static void setErrors(HashMap errors) {
		CSVReadStats.errors = errors;
	}

	private static HashMap workloads = new HashMap<String, String>();
	private static HashMap errors = new HashMap<String, String>();
	private static HashMap errorsTotal = new HashMap<String, String>();
	private static HashMap requestTotal = new HashMap<String, String>();

	public static void run() {

		boolean error = false;
		String csvFile = "tempResults.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] result = line.split(cvsSplitBy);

				String workLoadName = result[0];
				boolean isSuccsessful = Boolean.valueOf(result[1]);
				long starttime = Long.valueOf(result[2]);
				long endtime = Long.valueOf(result[3]);
				long sendBytes = Long.valueOf(result[4]);
				long reicevedBytes = Long.valueOf(result[5]);
				long responseTime = Long.valueOf(result[6]);
				long latencyTime = Long.valueOf(result[7]);
				long responseCode = 0;
				try {
					responseCode = Long.valueOf(result[8]);
				} catch (Exception e) {
					responseCode = -1;
				}
				String responseMessage = result[9];
				String isFailed = result[10];

				String threadName = result[11];
				String sampleLabel = result[12];
				double startTimeMillis = Double.valueOf(result[13]);
				double endTimeMillis = Double.valueOf(result[14]);
				double responseTimeMicros = Double.valueOf(result[15]);
				double latencyTimeMicros = Double.valueOf(result[16]);
				long threadCount = Long.valueOf(result[17]);

				if (!(requestTotal.containsKey(workLoadName))) {
					requestTotal.put(workLoadName, "1");
				} else {
					String totalRequest = (String) requestTotal
							.get(workLoadName);
					long totalRequestLong = Long.valueOf(totalRequest);
					totalRequestLong++;
					requestTotal.put(workLoadName,
							String.valueOf(totalRequestLong));

				}

				if (!(errors.containsKey(workLoadName))) {

					if (isFailed.equals("0"))

						errors.put(workLoadName, "false");
					if (isFailed.equals("1"))
						errors.put(workLoadName, "true");

				} else {

					if (isFailed.equals("1")) {

						errorsTotal.put(workLoadName, "1");

					} else {
						String totalError = (String)errorsTotal
								.get(workLoadName);
						long totalErrorLong = Long.valueOf(totalError);
						totalErrorLong++;
						errorsTotal.put(workLoadName,
								String.valueOf(totalErrorLong));

					}

					String isFailedString = (String) errors.get(workLoadName);
					if (!(isFailedString.equals("true"))) {

						if (isFailed.equals("0")) {
							errorsTotal.put(workLoadName, "1");

							errors.put(workLoadName, "false");
						}
						if (isFailed.equals("1")) {

							errors.put(workLoadName, "true");
						}

					}
				}

				if (!(workloads.containsKey(workLoadName))) {
					workloads.put(workLoadName, String.valueOf(responseTime));
				} else {
					String responseTimeWorst = (String) workloads
							.get(workLoadName);
					long responseTimeWorstLong = Long
							.valueOf(responseTimeWorst);
					if (responseTime > responseTimeWorstLong) {
						workloads.put(workLoadName,
								String.valueOf(responseTime));
					}
				}

			}
			
			Set keys=workloads.keySet();
			for (Object object : keys) {
				long errorTotal=Long.valueOf(errorsTotal.get(object).toString());
				long total=Long.valueOf(requestTotal.get(object).toString());
				double percent=Double.valueOf(errorTotal)/Double.valueOf(total);
				if (percent<0.15){
					errors.put(object, "false");
				}
				
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static HashMap getWorkloads() {
		return workloads;
	}

	public static void setWorkloads(HashMap workloads) {
		CSVReadStats.workloads = workloads;
	}

}
