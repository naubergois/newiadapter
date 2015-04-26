package br.unifor.iadapter.threadGroup;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

public class WorkLoad {

	private static String[] types = { "UpDown","Stress" };

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

	public  void plotGraph(GraphRowSumValues row, WorkLoad workLoad) {

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
