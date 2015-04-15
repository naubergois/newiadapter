package br.unifor.iadapter.threadGroup;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import kg.apc.charting.rows.GraphRowSumValues;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

public class WorkLoad {

	private JTabbedPane tab;

	public JTabbedPane getTab() {
		return tab;
	}

	public void setTab(JTabbedPane tab) {
		this.tab = tab;
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

	public void plotGraph(GraphRowSumValues row) {

		for (int n = 0; n < this.getNumThreads(); n++) {
			
			long initialTime=System.currentTimeMillis();

			long now = getStartTimeStrategy(n);
			row.add(now-initialTime, 0);
			row.add(now-initialTime, 1);

			long nowEnd = getEndTimeStrategy(n);
			row.add(nowEnd-initialTime, 0);
			row.add(nowEnd-initialTime, -1);
		}

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
			System.out.println(thread.getStartTime());
			System.out.println(thread.getEndTime());

		}
	}

}
