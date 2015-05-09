package br.unifor.iadapter.threadGroup.workload;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

import br.unifor.iadapter.jmeter.GraphRowSumValues;

public class WorkLoad {

	public void calcUsers() {

		Integer[] arrays = new Integer[10];

		arrays[0] = 0;
		arrays[1] = 0;
		arrays[2] = 0;
		arrays[3] = 0;
		arrays[4] = 0;
		arrays[5] = 0;
		arrays[6] = 0;
		arrays[7] = 0;
		arrays[8] = 0;
		arrays[9] = 0;

		int count = 1;
		int count2 = 1;

		if (this.getNumThreads() > 0) {

			while (count <= this.getNumThreads()) {

				if (count > this.getNumThreads())
					break;

				String nameWorkloadController = WorkLoadThreadGroup
						.getFunctionNameByID(this, count2 % 10);

				if ((nameWorkloadController != null)
						&& (!(nameWorkloadController.equals("None")))) {

					arrays[count2 % 10] += 1;
					count++;

				} else {
					int rest = count2 % 10;
					if (rest > 9) {
						rest = 9;
					}
					if (rest < 0) {
						rest = 0;
					}
					arrays[rest] = 0;
				}

				count2++;

			}

			this.setUsers1(arrays[0]);
			this.setUsers2(arrays[1]);
			this.setUsers3(arrays[2]);
			this.setUsers4(arrays[3]);
			this.setUsers5(arrays[4]);
			this.setUsers6(arrays[5]);
			this.setUsers7(arrays[6]);
			this.setUsers8(arrays[7]);
			this.setUsers9(arrays[8]);
			this.setUsers10(arrays[9]);
		}

	}

	private int users1;

	private int users2;

	private int users3;

	private int users4;

	private int users5;

	private int users6;

	private int users7;

	private int users8;

	private int users9;

	private int users10;

	public int getUsers1() {
		return users1;
	}

	public void setUsers1(int users1) {
		this.users1 = users1;
	}

	public int getUsers2() {
		return users2;
	}

	public void setUsers2(int users2) {
		this.users2 = users2;
	}

	public int getUsers3() {
		return users3;
	}

	public void setUsers3(int users3) {
		this.users3 = users3;
	}

	public int getUsers4() {
		return users4;
	}

	public void setUsers4(int users4) {
		this.users4 = users4;
	}

	public int getUsers5() {
		return users5;
	}

	public void setUsers5(int users5) {
		this.users5 = users5;
	}

	public int getUsers6() {
		return users6;
	}

	public void setUsers6(int users6) {
		this.users6 = users6;
	}

	public int getUsers7() {
		return users7;
	}

	public void setUsers7(int users7) {
		this.users7 = users7;
	}

	public int getUsers8() {
		return users8;
	}

	public void setUsers8(int users8) {
		this.users8 = users8;
	}

	public int getUsers9() {
		return users9;
	}

	public void setUsers9(int users9) {
		this.users9 = users9;
	}

	public int getUsers10() {
		return users10;
	}

	public void setUsers10(int users10) {
		this.users10 = users10;
	}

	private String searchMethod;

	public String getSearchMethod() {
		return searchMethod;
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}

	private long totalErrors;

	public long getTotalErrors() {
		return totalErrors;
	}

	public void setTotalErrors(long totalErrors) {
		this.totalErrors = totalErrors;
	}

	private long percentile90;

	private long percentile80;

	private long percentile70;

	public long getPercentile90() {
		return percentile90;
	}

	public void setPercentile90(long percentile90) {
		this.percentile90 = percentile90;
	}

	public long getPercentile80() {
		return percentile80;
	}

	public void setPercentile80(long percentile80) {
		this.percentile80 = percentile80;
	}

	public long getPercentile70() {
		return percentile70;
	}

	public void setPercentile70(long percentile70) {
		this.percentile70 = percentile70;
	}

	private static String[] types = { "UpDown", "Stress" };

	private int generation;

	private boolean active;

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private String function1;

	public static String[] getTypes() {
		return types;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + this.getName() + "," + this.getType() + ","
				+ this.getNumThreads() + "," + this.getFunction1() + "]";
	}

	public static void setTypes(String[] types) {
		WorkLoad.types = types;
	}

	private String function2;

	private String function3;

	private String function4;

	private String function5;

	private String function6;

	private String function7;

	private String function8;

	private String function9;

	private String function10;

	public String getFunction1() {
		return function1;
	}

	public void setFunction1(String function1) {
		this.function1 = function1;
	}

	public String getFunction2() {
		return function2;
	}

	public void setFunction2(String function2) {
		this.function2 = function2;
	}

	public String getFunction3() {
		return function3;
	}

	public void setFunction3(String function3) {
		this.function3 = function3;
	}

	public String getFunction4() {
		return function4;
	}

	public void setFunction4(String function4) {
		this.function4 = function4;
	}

	public String getFunction5() {
		return function5;
	}

	public void setFunction5(String function5) {
		this.function5 = function5;
	}

	public String getFunction6() {
		return function6;
	}

	public void setFunction6(String function6) {
		this.function6 = function6;
	}

	public String getFunction7() {
		return function7;
	}

	public void setFunction7(String function7) {
		this.function7 = function7;
	}

	public String getFunction8() {
		return function8;
	}

	public void setFunction8(String function8) {
		this.function8 = function8;
	}

	public String getFunction9() {
		return function9;
	}

	public void setFunction9(String function9) {
		this.function9 = function9;
	}

	public String getFunction10() {
		return function10;
	}

	public void setFunction10(String function10) {
		this.function10 = function10;
	}

	private double fit;

	public double getFit() {
		return fit;
	}

	public void setFit(double fit) {
		this.fit = fit;
	}

	private boolean error;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private long worstResponseTime;

	public long getWorstResponseTime() {
		return worstResponseTime;
	}

	public void setWorstResponseTime(long worstResponseTime) {
		this.worstResponseTime = worstResponseTime;
	}

	int initialDelay;

	public int getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(int initialDelay) {
		this.initialDelay = initialDelay;
	}

	public int getStartRampUp() {
		return startRampUp;
	}

	public void setStartRampUp(int startRampUp) {
		this.startRampUp = startRampUp;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public int getEndRampUp() {
		return endRampUp;
	}

	public void setEndRampUp(int endRampUp) {
		this.endRampUp = endRampUp;
	}

	int startRampUp = 60;
	int flightTime;
	int endRampUp;

	private int numThreads;

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	private static int threadStopped;

	public static int getThreadStopped() {
		return threadStopped;
	}

	public static void setThreadStopped(int threadStopped) {
		WorkLoad.threadStopped = threadStopped;
	}

	public long getStartTimeStrategy(long numberThread) {
		return (long) (numberThread * 1000 + System.currentTimeMillis() + 1000);
	}

	public long getEndTimeStrategy(long numberThread) {
		long totalTime = this.getNumThreads() * 2 * 1000;
		long difftime = totalTime - numThreads * 1000;
		return (long) (getStartTimeStrategy(numberThread) + difftime);
	}

	protected void scheduleThread(Logger log, long numberThreads,
			JMeterThread thread, long number) {

		long threadsToSchedule = numberThreads - number;
		log.debug("Scheduling thread: " + thread.getThreadName());
		if (threadsToSchedule > 0) {

			thread.setStartTime(getStartTimeStrategy(number));
			thread.setEndTime(getEndTimeStrategy(number));

			thread.setScheduled(true);

		}
	}

	public void plotGraph(GraphRowSumValues row, WorkLoad workLoad) {

		long initialTime = System.currentTimeMillis();

		for (int n = 0; n < workLoad.getNumThreads(); n++) {

			long now = workLoad.getStartTimeStrategy(n);
			row.add(now - initialTime, 0);
			row.add(now - initialTime, 1);

			long nowEnd = workLoad.getEndTimeStrategy(n);
			row.add(nowEnd - initialTime, 0);
			row.add(nowEnd - initialTime, -1);
		}

	}

}
