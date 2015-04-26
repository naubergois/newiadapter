package br.unifor.iadapter.threadGroup;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

public class StressWorkload extends WorkLoad {

	@Override
	protected void scheduleThread(Logger log, long numberThreads,
			JMeterThread thread, long number) {

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
		row.add(now - initialTime, 0);
		row.add(now - initialTime, workLoad.getNumThreads());

		long nowEnd = workLoad.getEndTimeStrategy(0);
		//row.add(nowEnd - initialTime, 0);
		//row.add(nowEnd - initialTime, workLoad.getNumThreads());
		// }
	}

}
