package br.unifor.iadapter.threadGroup;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

public class StressWorkload extends WorkLoad {

	@Override
	protected void scheduleThread(Logger log, long numberThreads,
			JMeterThread thread, long number) {

		long threadsToSchedule = numberThreads - number;
		log.debug("Scheduling thread: " + thread.getThreadName());
		if (threadsToSchedule > 0) {

		thread.setStartTime(System.currentTimeMillis());
	    thread.setEndTime(System.currentTimeMillis()+1000*60);

	    thread.setScheduled(false);

		}

	}

	@Override
	public long getStartTimeStrategy(long numberThread) {

		return 0;
	}

	@Override
	public long getEndTimeStrategy(long numberThread) {
		// TODO Auto-generated method stub
		return getStartTimeStrategy(numberThread) + 1000000;
	}

	@Override
	public void plotGraph(GraphRowSumValues row, WorkLoad workLoad) {
		long initialTime = System.currentTimeMillis();

		// for (int n = 0; n < workLoad.getNumThreads(); n++) {

		long now = workLoad.getStartTimeStrategy(0);
		row.add(0, 0);
		row.add(0, workLoad.getNumThreads());

		long nowEnd = workLoad.getEndTimeStrategy(0);
		row.add(1000, 0);
		// row.add(1000, workLoad.getNumThreads());
		// }
	}

}
