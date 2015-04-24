package br.unifor.iadapter.threadGroup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVReadStats {

	private static HashMap workloads = new HashMap<String, String>();
	private static HashMap errors = new HashMap<String, String>();
	
	

	public static void run() {

		boolean error=false;
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
				Boolean isFailed = Boolean.valueOf(result[10]);
				if (isFailed){
					errors.put(workLoadName, String.valueOf(isFailed));
				}else{
					if (!(errors.containsKey(workLoadName))) {
						errors.put(workLoadName, String.valueOf(isFailed));
					} 
				}
				String threadName = result[11];
				String sampleLabel = result[12];
				double startTimeMillis = Double.valueOf(result[13]);
				double endTimeMillis = Double.valueOf(result[14]);
				double responseTimeMicros = Double.valueOf(result[15]);
				double latencyTimeMicros = Double.valueOf(result[16]);
				long threadCount = Long.valueOf(result[17]);

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
