package br.unifor.iadapter.threadGroup;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.engine.TreeCloner;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.JMeterVariables;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int currentTest;

	public int getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(int currentTest) {
		this.currentTest = currentTest;
	}

	private WorkLoad workloadCurrent;

	private List<WorkLoad> listWorkLoads;

	public List<WorkLoad> getListWorkLoads() {
		return listWorkLoads;
	}

	public void setListWorkLoads(List<WorkLoad> listWorkLoads) {
		this.listWorkLoads = listWorkLoads;
	}

	public static List<JMeterTreeNode> findWorkLoadTransactions(
			JMeterTreeNode rootNode, List<JMeterTreeNode> list) {

		Enumeration<JMeterTreeNode> enumr = rootNode.children();

		if (list == null) {
			list = new ArrayList<JMeterTreeNode>();
		}

		while (enumr.hasMoreElements()) {
			JMeterTreeNode child = enumr.nextElement();
			if (child.getTestElement() instanceof WorkLoadTransactionController) {
				list.add(child);
			}
			list.addAll(findWorkLoadTransactions(child, list));

		}
		return list;
	}

	public static List<JMeterTreeNode> findWorkLoadThreadGroup(
			JMeterTreeNode rootNode, List<JMeterTreeNode> list) {

		Enumeration<JMeterTreeNode> enumr = rootNode.children();

		if (list == null) {
			list = new ArrayList<JMeterTreeNode>();
		}

		while (enumr.hasMoreElements()) {
			JMeterTreeNode child = enumr.nextElement();
			if (child.getTestElement() instanceof WorkLoadThreadGroup) {
				list.add(child);
			}
			list.addAll(findWorkLoadThreadGroup(child, list));

		}
		return list;
	}

	public static List<WorkLoad> createWorkloads() {
		List<WorkLoad> lista = new ArrayList<WorkLoad>();
		WorkLoad workload = new WorkLoad();
		workload.setNumThreads(10);
		lista.add(workload);
		return lista;
	}

	@Override
	public void threadFinished(JMeterThread thread) {

		JMeterProperty data = getData();

		if (!(data instanceof NullProperty)) {
			scheduleIT = ((CollectionProperty) data).iterator();

		}

		CollectionProperty collection = ((CollectionProperty) data);
		
		int inc=0;
		if(JMeter.isNonGUI()){
			inc=1;
		}
		
		int size = collection.size()+inc;

		System.out.println(JMeterContextService.getContext().getEngine()
				.isActive());

		super.threadFinished(thread);
		WorkLoad.setThreadStopped(WorkLoad.getThreadStopped() + 1);
		
		
		int threadStopped = WorkLoad.getThreadStopped();
		
		
		

		if (workloadCurrent.getNumThreads() <= threadStopped) {
			System.out.print("teste terminou");
			WorkLoad.setThreadStopped(0);

			System.out.println(ConsoleStatusLogger.getReponseTimes());

			this.setCurrentTest(this.getCurrentTest() + 1);
			
			

			
			if (this.getCurrentTest() < size) {
				final JMeterContext context = JMeterContextService.getContext();

				try {

					JMeterEngine engine = context.getEngine();

					new Thread((Runnable) engine).start();

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				while (!(verifyThreadsStopped()))
					;
				this.currentTest = 0;

				for (int i = 0; i < collection.size(); i++) {
					CollectionProperty property = (CollectionProperty) collection
							.get(i);

					
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

	private PropertyIterator scheduleIT;
	private static final long TOLERANCE = 1000;

	@Override
	public void start(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine) {

		JMeterProperty data = getData();

		if (!(data instanceof NullProperty)) {
			scheduleIT = ((CollectionProperty) data).iterator();

		}

		SinglentonEngine.groupCount = groupCount;
		SinglentonEngine.notifier = notifier;
		SinglentonEngine.threadGroupTree = threadGroupTree;
		SinglentonEngine.engine1 = engine;
		CollectionProperty collection = ((CollectionProperty) data);
		int size = collection.size();

		if (scheduleIT.hasNext()) {

			if (size > 0) {
				running = true;

				ArrayList object = (ArrayList) collection.get(currentTest)
						.getObjectValue();

				WorkLoad workload = JMeterPluginsUtils.getWorkLoad(object);

				try {
					DerbyDatabase.createWorkLoadIfNotExist(object);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JMeterContextService.getContext().getVariables()
						.put("currentWorkload", workload.getName());

				this.workloadCurrent = workload;

				int numThreads = workload.getNumThreads();

				threadsToSchedule = numThreads;

				log.info("Starting thread group number " + groupCount
						+ " threads " + numThreads);

				final JMeterContext context = JMeterContextService.getContext();

				context.setRestartNextLoop(true);

				for (int i = 0; i < numThreads; i++) {
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
		System.out.println("Active:" + engine.isActive());
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
		if (this.listWorkLoads == null) {
			listWorkLoads = new ArrayList<WorkLoad>();
		}
		try {
			DerbyDatabase.createDatabase();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		System.out.println("Teste terminou");

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
