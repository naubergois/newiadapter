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

package br.unifor.iadapter.threadGroup.workload;

import org.apache.jmeter.threads.JMeterThread;
import org.apache.log.Logger;

import br.unifor.iadapter.jmeter.GraphRowSumValues;

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
