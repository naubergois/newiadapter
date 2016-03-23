/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import br.unifor.iadapter.percentiles.PercentileCounter;

public class CSVReadStats {

	public static HashMap getRequestsMaxTime() {
		return requestsMaxTime;
	}

	public static void setRequestsMaxTime(HashMap requestsMaxTime) {
		CSVReadStats.requestsMaxTime = requestsMaxTime;
	}

	public static HashMap getErrors() {
		return errors;
	}

	public static void setErrors(HashMap errors) {
		CSVReadStats.errors = errors;
	}

	private static HashMap workloads = new HashMap<String, String>();
	private static HashMap requestsMaxTime = new HashMap<String, String>();
	private static HashMap errors = new HashMap<String, String>();
	private static HashMap errorsTotal = new HashMap<String, String>();

	public static HashMap getPercentiles() {
		return percentiles;
	}

	public static void setPercentiles(HashMap percentiles) {
		CSVReadStats.percentiles = percentiles;
	}

	private static HashMap requestTotal = new HashMap<String, String>();
	private static HashMap percentiles = new HashMap<String, PercentileCounter>();

	public static void run(int generation) {

		errorsTotal = new HashMap<String, String>();

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

				String key = workLoadName + "##@" + sampleLabel;

				if (!(errorsTotal.containsKey(workLoadName))) {

					errorsTotal.put(workLoadName, "0");
					if (isFailed.equals("1")) {
						String totalError = (String) errorsTotal
								.get(workLoadName);
						long totalErrorLong = Long.valueOf(totalError);
						totalErrorLong++;
						errorsTotal.put(workLoadName,
								String.valueOf(totalErrorLong));
					}

				} else {
					if (isFailed.equals("1")) {
						String totalError = (String) errorsTotal
								.get(workLoadName);
						long totalErrorLong = Long.valueOf(totalError);
						totalErrorLong++;
						errorsTotal.put(workLoadName,
								String.valueOf(totalErrorLong));
					}

				}

				if (!(requestsMaxTime.containsKey(key))) {
					requestsMaxTime.put(key, String.valueOf(responseTime));

				} else {
					String responseTimeWorst = (String) requestsMaxTime
							.get(key);
					long responseTimeWorstLong = Long
							.valueOf(responseTimeWorst);
					if (responseTime > responseTimeWorstLong) {
						requestsMaxTime.put(key, String.valueOf(responseTime));
					}
				}

				if (!(percentiles.containsKey(workLoadName))) {
					PercentileCounter counter = new PercentileCounter(0, 100,
							200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000,
							3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
							11000, 12000, 13000, 14000, 15000, 16000, 17000,
							18000, 19000, 20000, 21000, 22000, 23000, 24000,
							25000, 26000, 27000, 28000, 29000, 30000, 31000,
							32000, 33000, 34000, 35000, 36000, 37000, 38000,
							39000, 40000, 41000, 42000, 43000, 44000, 45000,
							46000, 47000, 48000, 49000, 50000, 51000, 52000,
							53000, 54000, 55000, 56000, 57000, 58000, 59000,
							60000, 65000, 70000, 75000, 80000, 85000, 90000,
							95000, 100000, 105000, 11000, 115000, 120000,
							125000, 130000, 135000, 140000, 145000, 150000,
							155000, 160000, 165000, 170000, 175000, 180000,
							185000, 190000);
					counter.increment((int) responseTime);
					percentiles.put(workLoadName, counter);

				} else {
					PercentileCounter counter = (PercentileCounter) percentiles
							.get(workLoadName);
					counter.increment((int) responseTime);

					percentiles.put(workLoadName, counter);

				}

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

						errors.put(workLoadName, "true");
					}

					String isFailedString = (String) errors.get(workLoadName);
					if (!(isFailedString.equals("true"))) {

						if (isFailed.equals("0")) {

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

			Set keys = workloads.keySet();
			for (Object object : keys) {
				if (errorsTotal.get(object) != null) {
					long errorTotal = Long.valueOf(errorsTotal.get(object)
							.toString());
					long total = Long.valueOf(requestTotal.get(object)
							.toString());
					double percent = Double.valueOf(errorTotal)
							/ Double.valueOf(total);
					if (percent < 0.15) {
						errors.put(object, "false");
					}
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

		File file = new File("tempResults.csv");
		file.delete();

	}

	public static HashMap getErrorsTotal() {
		return errorsTotal;
	}

	public static void setErrorsTotal(HashMap errorsTotal) {
		CSVReadStats.errorsTotal = errorsTotal;
	}

	public static HashMap getWorkloads() {
		return workloads;
	}

	public static void setWorkloads(HashMap workloads) {
		CSVReadStats.workloads = workloads;
	}

}
