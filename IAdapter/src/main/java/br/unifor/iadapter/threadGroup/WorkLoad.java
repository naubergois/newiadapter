package br.unifor.iadapter.threadGroup;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

public class WorkLoad {

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
		return (long) (numberThread * 1000 + 10000 + System.currentTimeMillis() + 1000);
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

}
