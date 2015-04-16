package br.unifor.iadapter.threadGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.engine.TreeCloner;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/***
 * Class for define workload model
 * 
 * @author naubergois
 *
 */
public class WorkLoadThreadGroup extends AbstractSimpleThreadGroup implements
		Serializable, TestStateListener, SampleListener, LoopIterationListener {

	private WorkLoad workloadCurrent;

	public static List<WorkLoad> createWorkloads() {
		List<WorkLoad> lista = new ArrayList<WorkLoad>();
		WorkLoad workload = new WorkLoad();
		workload.setNumThreads(10);
		lista.add(workload);
		return lista;
	}

	@Override
	public void threadFinished(JMeterThread thread) {

		super.threadFinished(thread);
		WorkLoad.setThreadStopped(WorkLoad.getThreadStopped() + 1);
		if (workloadCurrent.getNumThreads() <= WorkLoad.getThreadStopped()) {
			System.out.print("teste terminou");
			WorkLoad.setThreadStopped(0);
			WorkLoadTests.setCurrentTest(WorkLoadTests.getCurrentTest() + 1);
			if (WorkLoadTests.getCurrentTest() < WorkLoadTests.getTests()
					.size()) {
				final JMeterContext context = JMeterContextService.getContext();
				// context.g
				try {

					JMeterEngine engine = context.getEngine();

					new Thread((Runnable) engine).start();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public boolean verifyThreadsStopped() {
		// TODO Auto-generated method stub
		return super.verifyThreadsStopped();
	}

	private static final Logger log = LoggingManager.getLoggerForClass();
	public static final String DATA_PROPERTY = "workloadthreadgroupdata";
	private int threadsToSchedule;

	private final Map<JMeterThread, Thread> allThreads = new ConcurrentHashMap<JMeterThread, Thread>();
	/**
	 * Is test (still) running?
	 */
	private volatile boolean running = false;

	// JMeter 2.7 Compatibility
	private long tgStartTime = -1;
	private static final long TOLERANCE = 1000;

	@Override
	public void start(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine) {

		SinglentonEngine.groupCount = groupCount;
		SinglentonEngine.notifier = notifier;
		SinglentonEngine.threadGroupTree = threadGroupTree;
		SinglentonEngine.engine1 = engine;

		if (WorkLoadTests.getTests().size() > 0) {
			running = true;

			WorkLoad workload = WorkLoadTests.getTests().get(
					WorkLoadTests.getCurrentTest());

			this.workloadCurrent = workload;

			int numThreads = workload.getNumThreads();

			threadsToSchedule = numThreads;

			log.info("Starting thread group number " + groupCount + " threads "
					+ numThreads);

			final JMeterContext context = JMeterContextService.getContext();

			context.setRestartNextLoop(true);

			for (int i = 0; running && i < numThreads; i++) {
				JMeterThread jmThread = makeThread(groupCount, notifier,
						threadGroupTree, engine, i, context, workload);
				workload.scheduleThread(log, numThreads, jmThread, i);
				Thread newThread = new Thread(jmThread,
						jmThread.getThreadName());

				registerStartedThread(jmThread, newThread);

				newThread.start();
			}

			log.info("Started thread group number " + groupCount);
		}
	}

	@Override
	public Sampler next() {
		// TODO Auto-generated method stub
		return super.next();
	}

	private ListedHashTree cloneTree(ListedHashTree tree) {
		TreeCloner cloner = new TreeCloner(true);
		tree.traverse(cloner);
		return cloner.getClonedTree();
	}

	private JMeterThread makeThread(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine, int i,
			JMeterContext context, WorkLoad workLoad) { // N.B. Context needs to
														// be fetched in the
		// correct thread
		boolean onErrorStopTest = getOnErrorStopTest();
		boolean onErrorStopTestNow = getOnErrorStopTestNow();
		boolean onErrorStopThread = getOnErrorStopThread();
		boolean onErrorStartNextLoop = getOnErrorStartNextLoop();
		String groupName = getName();
		final JMeterThread jmeterThread = new JMeterThread(
				cloneTree(threadGroupTree), this, notifier);
		jmeterThread.setThreadNum(i);
		jmeterThread.setThreadGroup(this);
		jmeterThread.setInitialContext(context);
		final String threadName = groupName + " " + (groupCount) + "-"
				+ workLoad.getName() + "-" + (i + 1);
		jmeterThread.setThreadName(threadName);
		jmeterThread.setEngine(engine);
		jmeterThread.setOnErrorStopTest(onErrorStopTest);
		jmeterThread.setOnErrorStopTestNow(onErrorStopTestNow);
		jmeterThread.setOnErrorStopThread(onErrorStopThread);
		jmeterThread.setOnErrorStartNextLoop(onErrorStartNextLoop);
		return jmeterThread;
	}

	private void registerStartedThread(JMeterThread jMeterThread,
			Thread newThread) {
		allThreads.put(jMeterThread, newThread);
	}

	public WorkLoadThreadGroup() {
		super();

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

	@Override
	public void stop() {
		running = false;
		for (JMeterThread item : allThreads.keySet()) {
			item.stop();
		}
	}

	protected void scheduleThread(JMeterThread thread, long tgStartTime) {

	}

	@Override
	public int getNumThreads() {

		return 0;
	}

	public void testStarted() {

		JMeterProperty data = getData();
		if (!(data instanceof NullProperty)) {

		}

		threadsToSchedule = 0;
	}

	@Override
	public void triggerEndOfLoop() {
		// TODO Auto-generated method stub
		super.triggerEndOfLoop();
		System.out.println("End of loop");
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return super.isDone();
	}

	public void testEnded(String host) {
		System.out.println(host);
		testEnded();

	}

	public void testStarted(String host) {
		testStarted();
	}

	public void sampleOccurred(SampleEvent e) {
		// TODO Auto-generated method stub

	}

	public void sampleStarted(SampleEvent e) {
		System.out.print(e);
		// TODO Auto-generated method stub

	}

	public void sampleStopped(SampleEvent e) {
		// TODO Auto-generated method stub

	}

	public void iterationStart(LoopIterationEvent iterEvent) {
		// TODO Auto-generated method stub

	}

}
