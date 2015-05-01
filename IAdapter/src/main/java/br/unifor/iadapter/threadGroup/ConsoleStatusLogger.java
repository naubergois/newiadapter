// TODO: work out clear distributed mode behavior
// TODO: limit precision for error rate
package br.unifor.iadapter.threadGroup;

import java.io.PrintStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class ConsoleStatusLogger extends AbstractListenerElement implements
		SampleListener, Serializable, NoThreadClone, TestStateListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long worstResponseTime;

	private static final Logger log = LoggingManager.getLoggerForClass();
	private PrintStream out;
	private long cur = 0;
	private int count;
	private int threads;
	private int sumRTime;
	private int sumLatency;
	private int errors;
	private long begin;

	private static final String SAVE_CONFIG = "saveConfig"; // $NON-NLS-1$

	private static class JMeterLoggerOutputStream extends PrintStream {

		public JMeterLoggerOutputStream(Logger log) {
			super(System.out);
		}

		@Override
		public void println(String msg) {
			log.info(msg);
		}
	}

	private String currentWorkload = "";

	private static HashMap<String, String> reponseTimes = new HashMap<String, String>();

	public synchronized void sampleOccurred(SampleEvent se) {

		AbstractThreadGroup group = JMeterContextService.getContext()
				.getThreadGroup();

		if (group instanceof WorkLoadThreadGroup) {

			String workLoad = JMeterContextService.getContext().getVariables()
					.get("currentWorkload");

			if (!(this.currentWorkload.equals(workLoad))) {
				this.currentWorkload = workLoad;

				if (!(reponseTimes.containsKey(this.currentWorkload))) {
					reponseTimes.put(this.currentWorkload, "0");
				}

			}

			String responseTime = reponseTimes.get(this.currentWorkload);

			int worstResponseTime;

			if (responseTime == null) {
				worstResponseTime = 0;
			} else {
				worstResponseTime = Integer.valueOf(responseTime);
			}

			if (se.getResult().getTime() > worstResponseTime) {

				reponseTimes.put(this.currentWorkload,
						String.valueOf(se.getResult().getTime()));
			}

		}

		// TODO: make the interval configurable
		long sec = System.currentTimeMillis() / 1000;
		if (sec != cur && count > 0) {
			if (cur == 0) {
				begin = sec;
			}

			log.debug(cur + " " + begin);
			flush(sec - begin);
			cur = sec;
		}
		SampleResult res = se.getResult();

		count++;
		sumRTime += res.getTime();
		sumLatency += res.getLatency();
		errors += res.isSuccessful() ? 0 : 1;
		threads = res.getAllThreads();
	}

	public static HashMap<String, String> getReponseTimes() {
		return reponseTimes;
	}

	public static void setReponseTimes(HashMap<String, String> reponseTimes) {
		ConsoleStatusLogger.reponseTimes = reponseTimes;
	}

	public ConsoleStatusLogger() {
		super();

	}

	private void flush(long sec) {
		String msg = '#' + Long.toString(sec) + '\t';
		msg += "Threads: " + threads + '/'
				+ JMeterContextService.getTotalThreads() + '\t';
		msg += "Samples: " + count + '\t';
		msg += "Latency: " + sumLatency / (count > 0 ? count : 1) + '\t';
		msg += "Resp.Time: " + sumRTime / (count > 0 ? count : 1) + '\t';
		msg += "Errors: " + errors;
		out.println(msg);

		count = 0;
		sumRTime = 0;
		sumLatency = 0;
		errors = 0;
	}

	public void sampleStarted(SampleEvent se) {
	}

	public void sampleStopped(SampleEvent se) {
	}

	public void testStarted() {
		if (JMeter.isNonGUI()) {
			out = System.out;
		} else {
			out = new JMeterLoggerOutputStream(log);
		}
		cur = 0;
	}

	public void testStarted(String string) {
		testStarted();
	}

	public void testEnded() {

		Set keys = this.getReponseTimes().keySet();
		for (Object object : keys) {
			String responseTime = this.getReponseTimes().get(object);

		}
		System.out.println("Teste console terminou");
	}

	public void testEnded(String string) {
	}
}
