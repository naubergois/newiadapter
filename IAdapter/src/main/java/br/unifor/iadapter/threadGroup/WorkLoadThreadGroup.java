package br.unifor.iadapter.threadGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import kg.apc.jmeter.gui.ButtonPanelAddCopyRemove;
import kg.apc.jmeter.threads.AbstractSimpleThreadGroup;

import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/***
 * Class for define workload model
 * 
 * @author naubergois
 *
 */
public class WorkLoadThreadGroup extends AbstractSimpleThreadGroup implements
		Serializable, TestStateListener {

	private static final Logger log = LoggingManager.getLoggerForClass();
	public static final String DATA_PROPERTY = "workloadthreadgroupdata";
	private PropertyIterator scheduleIT;
	private int threadsToSchedule;
	private CollectionProperty currentRecord;

	public WorkLoadThreadGroup() {
		super();
		this.getThreadContext().getEngine().run();
	}

	public JMeterProperty getData() {
		// log.info("getData: "+getProperty(DATA_PROPERTY));
		return getProperty(DATA_PROPERTY);
	}

	void setData(CollectionProperty rows) {
		// log.info("setData");
		setProperty(rows);
	}

	public void testEnded() {
		// TODO Auto-generated method stub

	}

	protected void scheduleThread(JMeterThread thread, long tgStartTime) {
		log.debug("Scheduling thread: " + thread.getThreadName());
		if (threadsToSchedule < 1) {
			if (!scheduleIT.hasNext()) {
				throw new RuntimeException(
						"Not enough schedule records for thread #"
								+ thread.getThreadName());
			}

			currentRecord = (CollectionProperty) scheduleIT.next();
			threadsToSchedule = currentRecord.get(0).getIntValue();
		}

		int numThreads = currentRecord.get(0).getIntValue();
		int initialDelay = currentRecord.get(1).getIntValue();
		int startRampUp = currentRecord.get(2).getIntValue();
		int flightTime = currentRecord.get(3).getIntValue();
		int endRampUp = currentRecord.get(4).getIntValue();

		long ascentPoint = tgStartTime + 1000 * initialDelay;
		final int rampUpDelayForThread = (int) Math.floor(1000 * startRampUp
				* (double) threadsToSchedule / numThreads);
		long startTime = ascentPoint + rampUpDelayForThread;
		long descentPoint = startTime + 1000 * flightTime + 1000 * startRampUp
				- rampUpDelayForThread;

		thread.setStartTime(startTime);
		thread.setEndTime(descentPoint
				+ (int) Math.floor(1000 * endRampUp
						* (double) threadsToSchedule / numThreads));

		thread.setScheduled(true);
		threadsToSchedule--;
	}

	@Override
	public int getNumThreads() {
		int result = 0;

		JMeterProperty threadValues = getData();
		if (!(threadValues instanceof NullProperty)) {
			CollectionProperty columns = (CollectionProperty) threadValues;
			List<?> rows = (List<?>) columns.getObjectValue();
			for (Object row1 : rows) {
				CollectionProperty prop = (CollectionProperty) row1;
				ArrayList<JMeterProperty> row = (ArrayList<JMeterProperty>) prop
						.getObjectValue();
				// log.info(prop.getStringValue());
				result += row.get(0).getIntValue();
			}
		}

		return result;
	}

	public void testStarted() {
		JMeterProperty data = getData();
		if (!(data instanceof NullProperty)) {
			scheduleIT = ((CollectionProperty) data).iterator();
		}
		threadsToSchedule = 0;
	}

	public void testEnded(String host) {
		testEnded();
	}

	public void testStarted(String host) {
		testStarted();
	}

}
