package br.unifor.iadapter.threadGroup;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.engine.TreeCloner;
import org.apache.jmeter.engine.TreeClonerNoTimer;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.jgap.Population;

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

	ListedHashTree tree;

	public ListedHashTree getTree() {
		return tree;
	}

	public void setTree(ListedHashTree tree) {
		this.tree = tree;
	}

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

	private WorkLoad workLoadaux;

	@Override
	public void threadFinished(JMeterThread thread) {

		this.allThreads.remove(thread);

		if (this.allThreads.size() == 0) {

			System.out.println("Sample"
					+ JMeterContextService.getContext().getCurrentSampler());
			System.out.println("Sample"
					+ JMeterContextService.getContext().isSamplingStarted());
			System.out.println("Sample"
					+ JMeterContextService.getContext().isRestartNextLoop());
			System.out.println("Sample"
					+ JMeterContextService.getContext().getThreadNum());
			System.out.println("Num" + thread.getThreadNum());

			JMeterProperty data = getData();

			if (!(data instanceof NullProperty)) {
				scheduleIT = ((CollectionProperty) data).iterator();

			}

			CollectionProperty collection = ((CollectionProperty) data);

			int inc = 0;
			if (JMeter.isNonGUI()) {
				inc = 1;
			}

			int size = collection.size() + inc;

			WorkLoad.setThreadStopped(WorkLoad.getThreadStopped() + 1);

			int threadStopped = WorkLoad.getThreadStopped();

			System.out.println("TS:" + threadStopped);
			System.out.println(">=");
			System.out.println("TS:" + workloadCurrent + ":"
					+ workloadCurrent.getNumThreads());
			// if (threadStopped>=this.workloadCurrent.getNumThreads()) {
			System.out.println("Entrou novo la�o");

			WorkLoad.setThreadStopped(0);

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

				while (JMeterContextService.getTotalThreads() > 0)
					;

				this.currentTest = 0;

				CSVReadStats.run();

				List<WorkLoad> list = JMeterPluginsUtils
						.collectionPropertyToWorkLoad(this);

				JMeterPluginsUtils.updateFitnessValue(
						CSVReadStats.getWorkloads(), list);

				try {

					Population population = GeneWorkLoad.population(list,
							this.tree);

					Population populationBest = GeneWorkLoad
							.selectBestIndividuals(population, 10);

					List<WorkLoad> listBest = JMeterPluginsUtils
							.getListWorkLoadFromPopulation(populationBest,
									this.tree);

					try {
						for (WorkLoad workLoad : listBest) {
							DerbyDatabase.insertWorkLoads(JMeterPluginsUtils
									.getObjectList(workLoad));
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					JMeterPluginsUtils.listWorkLoadToCollectionProperty(
							listBest, WorkLoadThreadGroup.DATA_PROPERTY);

					System.out.println(CSVReadStats.getWorkloads());

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

	private PropertyIterator scheduleIT;
	private static final long TOLERANCE = 1000;

	@Override
	public void start(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine) {

		JMeterProperty data = getData();

		this.setTree(threadGroupTree);

		if (!(data instanceof NullProperty)) {
			scheduleIT = ((CollectionProperty) data).iterator();

		}

		List<TestElement> lista = FindService
				.searchWorkLoadControllerWithNoGui(threadGroupTree);

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

				int numThreads = getThreadNumberCounter(workload,
						workload.getNumThreads());

				threadsToSchedule = numThreads;

				log.info("Starting thread group number " + groupCount
						+ " threads " + numThreads);

				final JMeterContext context = JMeterContextService.getContext();

				context.setRestartNextLoop(true);

				int count = 1;
				int count2 = 1;

				while (count <= workload.getNumThreads()) {

					if (count > workload.getNumThreads())
						break;

					String nameWorkloadController = getFunctionNameByID(
							workload, count2 % 10);

					if ((nameWorkloadController != null)
							&& (!(nameWorkloadController.equals("None")))) {

						count++;

						TestElement node = getNodesByName(
								nameWorkloadController, lista);

						JMeterThread jmThread = makeThread(groupCount,
								notifier, threadGroupTree, engine, count,
								context, workload, node);
						workload.scheduleThread(log, numThreads, jmThread,
								count);
						Thread newThread = new Thread(jmThread,
								jmThread.getThreadName());

						registerStartedThread(jmThread, newThread);

						newThread.start();
					}

					count2++;

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

	public static int getThreadNumberCounter(WorkLoad workLoad, int length) {

		int counter = 0;
		for (int i = 0; i < 10; i++) {
			String name = getFunctionNameByID(workLoad, i);
			if (!(name.equals("None"))) {
				counter++;
			}
		}
		if (counter != 0) {
			int fracLength = length / counter;
			if (fracLength <= 0) {
				return 1;
			}
			return fracLength;
		} else {
			return 0;
		}
	}

	public static String getFunctionNameByID(WorkLoad workLoad, int id) {
		if (id == 0) {
			return workLoad.getFunction1();
		}
		if (id == 1) {
			return workLoad.getFunction2();
		}
		if (id == 2) {
			return workLoad.getFunction3();
		}
		if (id == 3) {
			return workLoad.getFunction4();
		}
		if (id == 4) {
			return workLoad.getFunction5();
		}
		if (id == 5) {
			return workLoad.getFunction6();
		}
		if (id == 6) {
			return workLoad.getFunction7();
		}
		if (id == 7) {
			return workLoad.getFunction8();
		}
		if (id == 8) {
			return workLoad.getFunction9();
		}
		if (id == 9) {
			return workLoad.getFunction10();
		}
		return "None";

	}

	public TestElement getNodesByName(String name, List<TestElement> listNodes) {

		TestElement selectNode = null;

		if (!(name.equals("None"))) {

			for (TestElement jMeterTreeNode : listNodes) {

				if (jMeterTreeNode.getName().equals(name)) {
					selectNode = jMeterTreeNode;
				}

			}
		}

		return selectNode;

	}

	private JMeterThread makeThread(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, JMeterEngine engine, int i,
			JMeterContext context, WorkLoad workLoad, TestElement node) { // N.B.
																			// Context
																			// needs
																			// to

		ListedHashTree clone = cloneTree(threadGroupTree);

		synchronized (clone) {

			Set keys = clone.keySet();

			for (Object object : keys) {
				HashTree tree = clone.get(object);
				Object[] object1 = tree.getArray();
				for (Object object2 : object1) {

					if (object2 instanceof WorkLoadController) {

						if (object2.equals(node)) {

							WorkLoadController controler = (WorkLoadController) object2;
							controler.setCondition("TRUE");
						} else {
							WorkLoadController controler = (WorkLoadController) object2;
							if (controler != null) {
								controler.setCondition("FALSE");
							}
						}

					}

				}

			}

			boolean onErrorStopTest = getOnErrorStopTest();
			boolean onErrorStopTestNow = getOnErrorStopTestNow();
			boolean onErrorStopThread = getOnErrorStopThread();
			boolean onErrorStartNextLoop = getOnErrorStartNextLoop();
			String groupName = getName();

			JMeterThread jmeterThread = null;

			jmeterThread = new JMeterThread(clone, this, notifier);
			jmeterThread.setThreadNum(i);
			jmeterThread.setThreadGroup(this);
			jmeterThread.setInitialContext(context);
			final String threadName = groupName + " " + (groupCount) + "-"
					+ workLoad.getName() + "-" + (i + 1);
			jmeterThread.setThreadName(threadName);

			jmeterThread.setEngine((StandardJMeterEngine) engine);
			jmeterThread.setOnErrorStopTest(onErrorStopTest);
			jmeterThread.setOnErrorStopTestNow(onErrorStopTestNow);
			jmeterThread.setOnErrorStopThread(onErrorStopThread);
			jmeterThread.setOnErrorStartNextLoop(onErrorStartNextLoop);

			return jmeterThread;
		}
	}

	private void registerStartedThread(JMeterThread jMeterThread,
			Thread newThread) {
		allThreads.put(jMeterThread, newThread);
	}

	public WorkLoadThreadGroup() {
		super();
		this.currentTest = 0;
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

	private TreeCloner cloneTree(HashTree testTree, boolean removeTimers) {
		TreeCloner cloner = null;
		if (removeTimers) {
			cloner = new TreeClonerNoTimer(false);
		} else {
			cloner = new TreeCloner(false);
		}
		testTree.traverse(cloner);
		return cloner;
	}

	private HashTree treeComplete;

	public HashTree getTreeComplete() {
		return treeComplete;
	}

	public void setTreeComplete(HashTree treeComplete) {
		this.treeComplete = treeComplete;
	}

	public void testEnded() {

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

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return super.isDone();
	}

	public void testEnded(String host) {

		testEnded();

	}

	public void testStarted(String host) {
		testStarted();
	}

	public void sampleOccurred(SampleEvent e) {
		// TODO Auto-generated method stub

	}

	public void sampleStarted(SampleEvent e) {

		// TODO Auto-generated method stub

	}

	public void sampleStopped(SampleEvent e) {
		// TODO Auto-generated method stub

	}

	public void iterationStart(LoopIterationEvent iterEvent) {
		// TODO Auto-generated method stub

	}

}
