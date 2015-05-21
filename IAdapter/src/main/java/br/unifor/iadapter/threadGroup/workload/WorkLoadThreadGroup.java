package br.unifor.iadapter.threadGroup.workload;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.agent.Agent;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.genetic.GeneticAlgorithm;
import br.unifor.iadapter.sa.SimulateAnnealing;
import br.unifor.iadapter.tabu.TabuSearch;
import br.unifor.iadapter.threadGroup.AbstractSimpleThreadGroup;
import br.unifor.iadapter.threadGroup.SingletonEngine;
import br.unifor.iadapter.util.CSVReadStats;
import br.unifor.iadapter.util.FindService;
import br.unifor.iadapter.util.JMeterPluginsUtils;
import br.unifor.iadapter.util.WorkLoadUtil;

/***
 * Class for define workload model and control the execution of test
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

	private int generation = 0;
	private int temperature = 0;
	private int generationTrack = 0;

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

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

	private WorkLoad workloadCurrentSA;

	public WorkLoad getWorkloadCurrentSA() {
		return workloadCurrentSA;
	}

	public void setWorkloadCurrentSA(WorkLoad workloadCurrentSA) {
		this.workloadCurrentSA = workloadCurrentSA;
	}

	private List<WorkLoad> listWorkLoads;

	public List<WorkLoad> getListWorkLoads() {
		return listWorkLoads;
	}

	public void setListWorkLoads(List<WorkLoad> listWorkLoads) {
		this.listWorkLoads = listWorkLoads;
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

	public static void startEngine() {
		final JMeterContext context = JMeterContextService.getContext();

		try {

			JMeterEngine engine = context.getEngine();

			new Thread((Runnable) engine).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to process the test results
	 * 
	 * @param agent
	 * @param tg
	 * @param generation
	 */
	@SuppressWarnings("unchecked")
	public static void finishTest(Agent agent, WorkLoadThreadGroup tg,
			String generation) {
		agent.runningFinal();

		while (!(tg.verifyThreadsStopped()))
			;

		while (JMeterContextService.getTotalThreads() > 0)
			;

		CSVReadStats.run(Integer.valueOf(generation));

		List<WorkLoad> list = null;
		try {
			list = MySQLDatabase.listWorkLoads(tg.getName(),
					String.valueOf(tg.getGeneration()));
			JMeterPluginsUtils.updateResponseTime(CSVReadStats.getWorkloads(),
					CSVReadStats.getPercentiles(),
					CSVReadStats.getErrorsTotal(), list, tg);
			JMeterPluginsUtils.updateSamples(CSVReadStats.getRequestsMaxTime(),
					tg, String.valueOf(generation));
			JMeterPluginsUtils.updateErrorValue(CSVReadStats.getErrors(), list,
					tg);
			JMeterPluginsUtils.updateFit(list, tg.getName(),
					String.valueOf(tg.getGeneration()), tg.getMaxTime(), tg);

		} catch (ClassNotFoundException e1) {
			log.error(e1.getMessage());

		} catch (SQLException e1) {

			log.error(e1.getMessage());
		}
		agent.delete();
	}

	public static List<WorkLoad> returnListAlgorithmGeneticWorkLoadsForNewGeneration(
			WorkLoadThreadGroup tg) {
		try {
			return MySQLDatabase.listWorkLoadsForNewGeneration(tg.getName(),
					String.valueOf(tg.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		return null;
	}

	public static List<WorkLoad> returnListAlgorithmGeneticWorkLoadsForAllNewGeneration(
			WorkLoadThreadGroup tg) {
		try {
			return MySQLDatabase.listWorkLoadsForAllNewGeneration(tg.getName(),
					String.valueOf(tg.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		return null;
	}
	
	
	

	public static List<WorkLoad> returnListSAWorkLoadsForNewGeneration(
			WorkLoadThreadGroup tg) {
		try {
			return MySQLDatabase.listWorkLoadsSAForNewGeneration(tg.getName(),
					String.valueOf(tg.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		return null;
	}

	public static List<WorkLoad> returnListTABUWorkLoadsForNewGeneration(
			WorkLoadThreadGroup tg) {
		try {
			return MySQLDatabase.listWorkLoadsTABUForNewGeneration(
					tg.getName(), String.valueOf(tg.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		return null;
	}

	public static List<WorkLoad> returnListALLWorkLoadsForNewGeneration(
			WorkLoadThreadGroup tg) {
		try {
			return MySQLDatabase.listWorkLoadsALlForNewGeneration(tg.getName(),
					String.valueOf(tg.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}
		return null;
	}

	public static List<TestElement> testNodeToTestElement(
			List<JMeterTreeNode> nodes) {
		List<TestElement> list = new ArrayList<TestElement>();
		for (JMeterTreeNode jMeterTreeNode : nodes) {
			list.add(jMeterTreeNode.getTestElement());
		}
		return list;

	}

	public static void createNextGenerationElementsWithGui(
			WorkLoadThreadGroup tg) {

		List<JMeterTreeNode> nodes = FindService
				.searchWorkLoadControllerWithGui();

		List<TestElement> listElement = testNodeToTestElement(nodes);

		tg.setGeneration(Integer.valueOf(tg.getInitialGeneration()));

		List<WorkLoad> list = null;

		if (tg.getCollaborative()) {
			list = returnListAlgorithmGeneticWorkLoadsForAllNewGeneration(tg);
		} else {
			list = returnListAlgorithmGeneticWorkLoadsForNewGeneration(tg);
		}
		List<WorkLoad> listSA = null;
		if (tg.getCollaborative()) {
			listSA = returnListALLWorkLoadsForNewGeneration(tg);

		} else {

			listSA = returnListSAWorkLoadsForNewGeneration(tg);
		}

		List<WorkLoad> listTABU = null;

		if (tg.getCollaborative()) {
			listTABU = returnListALLWorkLoadsForNewGeneration(tg);
		} else {
			listTABU = returnListTABUWorkLoadsForNewGeneration(tg);
		}

		tg.setGeneration(tg.getGeneration() + 1);

		List<WorkLoad> listBest = GeneticAlgorithm.newGeneration(tg, list,
				listElement, true);

		List<WorkLoad> listNewSA = new ArrayList<WorkLoad>();

		List<WorkLoad> listTABUnewGeneration = null;

		listTABU = TabuSearch.verify(listTABU, listElement);

		if (listTABU.size() > 0) {

			TabuSearch.addTabuTable(listTABU.get(0), listElement);

			List<WorkLoad> neighboors = WorkLoadUtil.getNeighborHood(
					listTABU.get(0), listElement, tg);
			listTABUnewGeneration = TabuSearch.verify(neighboors, listElement);

		}

		if (listSA.size() > 0) {
			tg.temperature = SimulateAnnealing.sa(tg.temperature,
					listSA.get(0), Integer.valueOf(tg.getThreadNumberMax()),
					listNewSA, tg.getGeneration(), tg, listElement);
		}

		try {

			for (WorkLoad workLoad : listBest) {
				MySQLDatabase.insertWorkLoads(
						WorkLoadUtil.getObjectList(workLoad), tg.getName(),
						String.valueOf(tg.getGeneration()));
			}

			if ((listTABUnewGeneration != null)
					&& (listTABUnewGeneration.size() > 0)) {
				for (WorkLoad workLoad : listTABUnewGeneration) {
					MySQLDatabase.insertWorkLoads(
							WorkLoadUtil.getObjectList(workLoad), tg.getName(),
							String.valueOf(tg.getGeneration()));
				}
			}

			if (listSA.size() > 0) {

				for (WorkLoad workLoad : listNewSA) {
					MySQLDatabase.insertWorkLoads(
							WorkLoadUtil.getObjectList(workLoad), tg.getName(),
							String.valueOf(tg.getGeneration()));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JMeterPluginsUtils.listWorkLoadToCollectionProperty(listBest,
				WorkLoadThreadGroup.DATA_PROPERTY);

	}

	public static void createNextGenerationElementsWithNoGui(
			WorkLoadThreadGroup tg) {

		List<TestElement> listElement = FindService
				.searchWorkLoadControllerWithNoGui(tg.getTree());

		List<WorkLoad> list = null;
		
		if (tg.getCollaborative()) {
			list = returnListAlgorithmGeneticWorkLoadsForAllNewGeneration(tg);
		} else {
			list = returnListAlgorithmGeneticWorkLoadsForNewGeneration(tg);
		}
		
		List<WorkLoad> listSA = null;
		if (tg.getCollaborative()) {
			listSA = returnListALLWorkLoadsForNewGeneration(tg);

		} else {

			listSA = returnListSAWorkLoadsForNewGeneration(tg);
		}

		List<WorkLoad> listTABU = null;

		if (tg.getCollaborative()) {
			listTABU = returnListALLWorkLoadsForNewGeneration(tg);
		} else {
			listTABU = returnListTABUWorkLoadsForNewGeneration(tg);
		}

		tg.setGeneration(tg.getGeneration() + 1);

		List<WorkLoad> listBest = GeneticAlgorithm.newGeneration(tg, list,
				listElement, false);

		List<WorkLoad> listNewSA = new ArrayList<WorkLoad>();

		List<WorkLoad> listTABUnewGeneration = null;

		listTABU = TabuSearch.verify(listTABU, listElement);

		if (listTABU.size() > 0) {

			TabuSearch.addTabuTable(listTABU.get(0), listElement);

			List<WorkLoad> neighboors = WorkLoadUtil.getNeighborHood(
					listTABU.get(0), listElement, tg);
			listTABUnewGeneration = TabuSearch.verify(neighboors, listElement);

		}

		if (listSA.size() > 0) {
			tg.temperature = SimulateAnnealing.sa(tg.temperature,
					listSA.get(0), Integer.valueOf(tg.getThreadNumberMax()),
					listNewSA, tg.getGeneration(), tg, listElement);
		}

		try {
			int generations = Integer.valueOf(tg.getGenNumber());
			if (tg.getGeneration() <= generations) {

				for (WorkLoad workLoad : listBest) {
					MySQLDatabase.insertWorkLoads(
							WorkLoadUtil.getObjectList(workLoad), tg.getName(),
							String.valueOf(tg.getGeneration()));
				}

				if ((listTABUnewGeneration != null)
						&& (listTABUnewGeneration.size() > 0)) {
					for (WorkLoad workLoad : listTABUnewGeneration) {
						MySQLDatabase.insertWorkLoads(
								WorkLoadUtil.getObjectList(workLoad),
								tg.getName(),
								String.valueOf(tg.getGeneration()));
					}
				}
			}
			int minTempInt = Integer.valueOf(tg.getMinTemp());

			if (minTempInt <= tg.getTemperature()) {
				if (listSA.size() > 0) {

					for (WorkLoad workLoad : listNewSA) {
						MySQLDatabase.insertWorkLoads(
								WorkLoadUtil.getObjectList(workLoad),
								tg.getName(),
								String.valueOf(tg.getGeneration()));
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JMeterPluginsUtils.listWorkLoadToCollectionProperty(listBest,
				WorkLoadThreadGroup.DATA_PROPERTY);

	}

	@Override
	public void threadFinished(JMeterThread thread) {
		if (this.temperature == 0) {
			this.temperature = Integer.valueOf(getThreadNumberMax());
		}

		this.allThreads.remove(thread);

		if (this.allThreads.size() == 0) {

			List<WorkLoad> workLoads = null;

			Agent agent = new Agent(this);
			agent.delete();

			Agent.sinchronize();

			try {
				workLoads = MySQLDatabase.listWorkLoadsByGeneration(
						this.getName(), String.valueOf(this.getGeneration()));
			} catch (ClassNotFoundException e2) {

				log.error(e2.getMessage());
			} catch (SQLException e2) {

				log.error(e2.getMessage());
			}

			int size = workLoads.size();

			this.setCurrentTest(this.getCurrentTest() + 1);
			if (this.getCurrentTest() < size) {

				WorkLoadThreadGroup.finishTest(agent, this,
						String.valueOf(generation));

				Agent.sinchronizeFinal();

				startEngine();

			} else {

				WorkLoadThreadGroup.finishTest(agent, this,
						String.valueOf(generation));

				agent.runningFinal();

				createNextGenerationElementsWithNoGui(this);

				int generations = Integer.valueOf(getGenNumber());
				int minTempInt = Integer.valueOf(getMinTemp());

				agent.delete();

				Agent.sinchronizeFinal();
				if ((getGeneration() <= generations)
						|| (minTempInt <= temperature)) {
					this.currentTest = 0;

					File file = new File("tempResults.csv");
					file.delete();
					CSVReadStats.setErrorsTotal(new HashMap());
					CSVReadStats.setErrors(new HashMap());
					CSVReadStats.setPercentiles(new HashMap());
					CSVReadStats.setRequestsMaxTime(new HashMap());
					CSVReadStats.setWorkloads(new HashMap());

					startEngine();

				} else {
					File file = new File("tempResults.csv");
					file.delete();
					this.generation = 1;
					this.currentTest = 0;
					CSVReadStats.setErrorsTotal(new HashMap());
					CSVReadStats.setErrors(new HashMap());
					CSVReadStats.setPercentiles(new HashMap());
					CSVReadStats.setRequestsMaxTime(new HashMap());
					CSVReadStats.setWorkloads(new HashMap());

				}

			}
		}

	}

	@Override
	public boolean verifyThreadsStopped() {

		return super.verifyThreadsStopped();
	}

	private static final Logger log = LoggingManager.getLoggerForClass();
	public static final String DATA_PROPERTY = "workloadthreadgroupdata";

	private final Map<JMeterThread, Thread> allThreads = new ConcurrentHashMap<JMeterThread, Thread>();
	private static final String THREAD_NUMBER_MAX = "threadnumbermax";

	private static final String MUTANT_PROBABILTY = "mutantprobablity";

	private static final String RESPONSE_TIME_MAX_PENALTY = "responsetimemaxpenalty";

	private static final String THREAD_MAX_TIME = "threadmaxtimemax";

	private static final String PERCENTILE90_FIT_WEIGHT = "percentile90fitweight";

	private static final String PERCENTILE80_FIT_WEIGHT = "percentile80fitweight";

	private static final String COLABORATIVE = "colaborative";

	private static final String PERCENTILE70_FIT_WEIGHT = "percentile70fitweight";

	private static final String POPULATION_SIZE = "populationsize";

	private static final String RESPONSEMAX_FIT_WEIGHT = "responsemaxfitweigth";

	private static final String TOTALERROR_FIT_WEIGHT = "totalerrorfitweigth";

	private static final String USER_FIT_WEIGHT = "userfitweigth";

	private static final String THREAD_IND = "threadind";

	private static final String THREAD_GEN_NUMBER = "threadgennumber";

	private static final String MIN_TEMP = "workloadthreadgroup.mintemp";

	private static final String INITIAL_GENERATION = "workloadthreadgroup.initialgeneration";;

	@Override
	public void start(int groupCount, ListenerNotifier notifier,
			ListedHashTree threadGroupTree, StandardJMeterEngine engine) {

		while (!(this.verifyThreadsStopped()))
			;

		if (this.currentTest > 0) {
			Agent.sinchronizeIp();
		}
		Agent.sinchronizeFinal();

		if (generation == 0) {
			generation = Integer.valueOf(this.getInitialGeneration());
		}

		List<WorkLoad> list = null;
		try {
			list = MySQLDatabase.listWorkLoadsOrderName(this.getName(),
					String.valueOf(this.getGeneration()));
		} catch (ClassNotFoundException e1) {

			log.error(e1.getMessage());
		} catch (SQLException e1) {
			log.error(e1.getMessage());
		}

		this.setTree(threadGroupTree);

		List<TestElement> lista = FindService
				.searchWorkLoadControllerWithNoGui(threadGroupTree);

		SingletonEngine.groupCount = groupCount;
		SingletonEngine.notifier = notifier;
		SingletonEngine.threadGroupTree = threadGroupTree;
		SingletonEngine.engine1 = engine;

		int size = list.size();

		if (size > 0) {
			Agent agent = new Agent(this);

			agent.running();
			WorkLoad workload = list.get(this.getCurrentTest());

			int newGeneration = 0;

			int newGenerationAux = WorkLoadUtil.getGenerationFromName(workload
					.getName());

			if (newGenerationAux > newGeneration) {

				newGeneration = newGenerationAux;

				if (newGeneration > 0) {
					generationTrack = newGeneration + 1;
				}

			}

			JMeterContextService.getContext().getVariables()
					.put("currentWorkload", workload.getName());

			int numThreads = getThreadNumberCounter(workload,
					workload.getNumThreads());

			log.info("Starting workload " + workload.getName());

			log.info("Starting thread group number " + groupCount + " threads "
					+ workload.getNumThreads());

			final JMeterContext context = JMeterContextService.getContext();

			context.setRestartNextLoop(true);

			int count = 1;

			while (count <= 10) {

				int users = 0;

				String nameWorkloadController = getFunctionNameByID(workload,
						count);

				if ((nameWorkloadController != null)
						&& (!(nameWorkloadController.equals("None")))) {

					if (count == 1) {
						users = workload.getUsers1();
					}
					if (count == 2) {
						users = workload.getUsers2();
					}
					if (count == 3) {
						users = workload.getUsers3();
					}
					if (count == 4) {
						users = workload.getUsers4();
					}
					if (count == 5) {
						users = workload.getUsers5();
					}
					if (count == 6) {
						users = workload.getUsers6();
					}
					if (count == 7) {
						users = workload.getUsers7();
					}
					if (count == 8) {
						users = workload.getUsers8();
					}
					if (count == 9) {
						users = workload.getUsers9();
					}
					if (count == 10) {
						users = workload.getUsers10();
					}

					TestElement node = getNodesByName(nameWorkloadController,
							lista);

					if (users > 0) {

						log.info("Starting threads " + users + " Func:"
								+ nameWorkloadController);

						for (int j = 0; j < users; j++) {
							JMeterThread jmThread = makeThread(groupCount,
									notifier, threadGroupTree, engine, j,
									context, workload, node);
							workload.scheduleThread(log, numThreads, jmThread,
									count);
							Thread newThread = new Thread(jmThread,
									jmThread.getThreadName());

							registerStartedThread(jmThread, newThread);

							newThread.start();
						}
					}
					groupCount++;
				}

				count++;

			}

			log.info("Started thread group number " + groupCount);
		}

	}

	public int getGenerationTrack() {
		return generationTrack;
	}

	public void setGenerationTrack(int generationTrack) {
		this.generationTrack = generationTrack;
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
		if (id == 1) {
			return workLoad.getFunction1();
		}
		if (id == 2) {
			return workLoad.getFunction2();
		}
		if (id == 3) {
			return workLoad.getFunction3();
		}
		if (id == 4) {
			return workLoad.getFunction4();
		}
		if (id == 5) {
			return workLoad.getFunction5();
		}
		if (id == 6) {
			return workLoad.getFunction6();
		}
		if (id == 7) {
			return workLoad.getFunction7();
		}
		if (id == 8) {
			return workLoad.getFunction8();
		}
		if (id == 9) {
			return workLoad.getFunction9();
		}
		if (id == 10) {
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
			JMeterContext context, WorkLoad workLoad, TestElement node) {

		ListedHashTree clone = cloneTree(threadGroupTree);

		synchronized (clone) {
			synchronized (threadGroupTree) {

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

	}

	public String getInitialGeneration() {
		return getPropertyAsString(INITIAL_GENERATION);
	}

	public void setInitialGeneration(String delay) {
		setProperty(INITIAL_GENERATION, delay);
	}

	public String getResponseMaxFitWeight() {
		return getPropertyAsString(RESPONSEMAX_FIT_WEIGHT);
	}

	public void setResponseMaxFitWeight(String delay) {
		setProperty(RESPONSEMAX_FIT_WEIGHT, delay);
	}

	public void setTotalErrorFitWeight(String delay) {
		setProperty(TOTALERROR_FIT_WEIGHT, delay);
	}

	public void setUserFitWeight(String delay) {
		setProperty(USER_FIT_WEIGHT, delay);
	}

	public String getUserFitWeight() {
		return getPropertyAsString(USER_FIT_WEIGHT);
	}

	public void setResponseTimeMaxPenalty(String delay) {
		setProperty(RESPONSE_TIME_MAX_PENALTY, delay);
	}

	public void setMutantProbabilty(String delay) {
		setProperty(MUTANT_PROBABILTY, delay);
	}

	public String getMutantProbability() {
		return getPropertyAsString(MUTANT_PROBABILTY);
	}

	public String getResponseTimeMaxPenalty() {
		return getPropertyAsString(RESPONSE_TIME_MAX_PENALTY);
	}

	public String getTotalErrorFitWeight() {
		return getPropertyAsString(TOTALERROR_FIT_WEIGHT);
	}

	public String getResponse90FitWeight() {
		return getPropertyAsString(PERCENTILE90_FIT_WEIGHT);
	}

	public void setResponse90FitWeight(String delay) {
		setProperty(PERCENTILE90_FIT_WEIGHT, delay);
	}

	public String getResponse80FitWeight() {
		return getPropertyAsString(PERCENTILE80_FIT_WEIGHT);
	}

	public boolean getCollaborative() {
		return getPropertyAsBoolean(COLABORATIVE);
	}

	public void setCollaborative(boolean delay) {
		setProperty(COLABORATIVE, delay);
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public void setPopulationSize(String delay) {
		setProperty(POPULATION_SIZE, delay);
	}

	public String getPopulationSize() {
		return getPropertyAsString(POPULATION_SIZE);
	}

	public void setResponse80FitWeight(String delay) {
		setProperty(PERCENTILE80_FIT_WEIGHT, delay);
	}

	public String getResponse70FitWeight() {
		return getPropertyAsString(PERCENTILE70_FIT_WEIGHT);
	}

	public void setResponse70FitWeight(String delay) {
		setProperty(PERCENTILE70_FIT_WEIGHT, delay);
	}

	public String getMaxTime() {
		return getPropertyAsString(THREAD_MAX_TIME);
	}

	public void setMaxTime(String delay) {
		setProperty(THREAD_MAX_TIME, delay);
	}

	public String getBestIndividuals() {
		return getPropertyAsString(THREAD_IND);
	}

	public void setBestIndividuals(String delay) {
		setProperty(THREAD_IND, delay);
	}

	public String getMinTemp() {
		return getPropertyAsString(MIN_TEMP);
	}

	public void setMinTemp(String delay) {
		setProperty(MIN_TEMP, delay);
	}

	public String getGenNumber() {
		return getPropertyAsString(THREAD_GEN_NUMBER);
	}

	public void setGenNumber(String delay) {
		setProperty(THREAD_GEN_NUMBER, delay);
	}

	public String getThreadNumberMax() {
		return getPropertyAsString(THREAD_NUMBER_MAX);
	}

	public void setThreadNumberMax(String delay) {
		setProperty(THREAD_NUMBER_MAX, delay);
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
