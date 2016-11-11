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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import br.unifor.iadapter.action.AddRowWorkloadAction;
import br.unifor.iadapter.action.DeleteAgentAction;
import br.unifor.iadapter.action.DeleteRowAction;
import br.unifor.iadapter.action.DesactiveRowWorkloadAction;
import br.unifor.iadapter.action.ExportPositiveWorkloadAction;
import br.unifor.iadapter.action.ExportWorkloadAction;
import br.unifor.iadapter.action.NewGenerationWorkloadAction;
import br.unifor.iadapter.action.RefreshRowWorkloadAction;
import br.unifor.iadapter.action.SaveRowAction;
import br.unifor.iadapter.jmeter.AbstractGraphRow;
import br.unifor.iadapter.jmeter.ClearRowWorkloadAction;
import br.unifor.iadapter.jmeter.GraphPanelChart;
import br.unifor.iadapter.jmeter.GraphRowSumValues;
import br.unifor.iadapter.jmeter.GuiBuilderHelper;
import br.unifor.iadapter.searchclass.SearchClass;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class WorkLoadThreadGroupGUI extends AbstractThreadGroupGui implements TableModelListener, CellEditorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String WIKIPAGE = "WorkLoadThreadGroup";
	private static final Logger log = LoggingManager.getLoggerForClass();

	private JTextField threadMax;
	private JTextField maxtime;
	private JTextField genNumber;
	private JTextField bestInd;
	private JTextField minTemp;
	private JTextField percentile90FitWeight;
	private JTextField percentile80FitWeight;
	private JTextField percentile70FitWeight;
	private JTextField responseMaxFitWeight;
	private JTextField totalErrorFitWeight;
	private JTextField userFitWeight;
	private JTextField populationSize;
	private JCheckBox colaborative;
	private JTextField initialGeneration;
	private JTextField responseTimeMaxPenalty;
	private JTextField mutantProbability;
	private JTextField maxMemory;
	private JTextField maxCpuShare;
	private JTextField dockerImage;
	private JTextField ipDocker;
	private JTextField sourcePortDocker;
	private JTextField destPortDocker;
	private JTextField dockerCommandLine;
	private JTextArea algorithmList;
	/**
	 *
	 */
	protected ConcurrentHashMap<String, AbstractGraphRow> model;
	private GraphPanelChart chart;
	/**
	 *
	 */

	public static final String[] columnIdentifiers = new String[] { "Name", "Kind", "Users", "Response Time", "Error",
			"Fit", "Function1", "Function2", "Function3", "Function4", "Function5", "Function6", "Function7",
			"Function8", "Function9", "Function10", "Generation", "Active", "Percentile90", "Percentile80",
			"Percentile70", "TotalError", "SearchMethod", "USER1", "USER2", "USER3", "USER4", "USER5", "USER6", "USER7",
			"USER8", "USER9", "USER10", "MEMORY", "CPUSHARE" };

	public static final String[] columnIdentifiersAgent = new String[] { "Name", "Running", "IP", "Date" };

	public static final String[] columnIdentifiersLog = new String[] { "Log" };
	/**
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static final Class[] columnClasses = new Class[] { String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class };

	@SuppressWarnings("rawtypes")
	public static final Class[] columnClassesAgent = new Class[] { String.class, String.class, String.class,
			String.class };

	@SuppressWarnings("rawtypes")
	public static final Class[] columnClassesLog = new Class[] { String.class };

	public static final String[] defaultValues = new String[] { "1", "1", "1", "1", "1", "1", "None", "None", "None",
			"None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None",
			"None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None" };

	public static final String[] defaultValuesAgent = new String[] { "1", "1", "1", "1" };

	private LoopControlPanel loopPanel;
	protected PowerTableModel wtableModel;
	protected PowerTableModel wtableLog;
	protected PowerTableModel wtableModelAgents;
	protected JTable grid;
	protected JTable gridAgents;
	protected JTable gridLog;
	protected JPanel buttons;

	JTabbedPane tabbedPane = new JTabbedPane();

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	protected final void init() {

		JMeterPluginsUtils.addHelpLinkToPanel(this, WIKIPAGE);
		JPanel containerPanel = new VerticalPanel();

		containerPanel.add(createParamsPanel(), BorderLayout.NORTH);
		tabbedPane.addTab("Main", containerPanel);
		tabbedPane.addTab("WorkLoad", createWorkloadPanel());
		tabbedPane.addTab("Algorithms", createAlgorithmsPanel());
		tabbedPane.addTab("Graph", createGraphPanel());
		// createTabDocker(tabbedPane);
		createTabAgent(tabbedPane);
		createTabParameters(tabbedPane);
		createLogPanel(tabbedPane);
		createFitParameters(tabbedPane);
		createPopulationParameters(tabbedPane);
		add(tabbedPane, BorderLayout.CENTER);

		createControllerPanel();
	}

	public JTextField getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(JTextField populationSize) {
		this.populationSize = populationSize;
	}

	public PowerTableModel getWtableLog() {
		return wtableLog;
	}

	public void setWtableLog(PowerTableModel wtableLog) {
		this.wtableLog = wtableLog;
	}

	public PowerTableModel getWtableModelAgents() {
		return wtableModelAgents;
	}

	public void setWtableModelAgents(PowerTableModel wtableModelAgents) {
		this.wtableModelAgents = wtableModelAgents;
	}

	private JTable createGrid() {
		grid = new JTable();
		grid.getDefaultEditor(String.class).addCellEditorListener(this);
		createTableModel();
		grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		grid.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		grid.setPreferredScrollableViewportSize(new Dimension(800, 800));
		grid.setSize(800, 800);
		grid.getColumnModel().getColumn(0).setWidth(100);

		grid.addMouseListener(new WorkLoadTableClicked(grid, this, wtableModel, chart));

		return grid;
	}

	private JTable createGridAgent() {
		gridAgents = new JTable();
		gridAgents.getDefaultEditor(String.class).addCellEditorListener(this);
		createTableModelAgent();
		gridAgents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gridAgents.setMinimumSize(new Dimension(200, 100));

		return gridAgents;
	}

	private JTable createGridLog() {
		gridLog = new JTable();
		gridLog.getDefaultEditor(String.class).addCellEditorListener(this);
		createTableModelLog();
		gridLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gridLog.setMinimumSize(new Dimension(200, 100));

		return gridLog;
	}

	private JPanel createParamsPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.setPreferredSize(new Dimension(200, 200));

		ImageIcon iadapter = new javax.swing.ImageIcon(getClass().getResource("iadapter.png"));

		JLabel icon = new JLabel(iadapter);

		
		
		JTextArea area=new JTextArea(10,10);

		List<String> classes = SearchClass.getClasses();

		String classesString = "";
		classesString+="Algorithms found: "+"\n";
		for (String string : classes) {
			classesString += string + "\n";
		}
		classesString += "";

		area.setText(classesString);
		
		JScrollPane scroller = new JScrollPane(area, 
			      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


		panel.add(BorderLayout.NORTH, icon);
		panel.add(BorderLayout.CENTER, scroller);
		return panel;
	}
	
	private JPanel createAlgorithmsPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.setPreferredSize(new Dimension(200, 200));

				
		
		algorithmList=new JTextArea(10,10);
		
		

		List<String> classes = SearchClass.getClasses();

		String classesString = "";
		
		for (String string : classes) {
			classesString += string + ",";
		}
		classesString += "";

		algorithmList.setText(classesString);
		JLabel label=new JLabel(classesString);
		
		JScrollPane scroller = new JScrollPane(algorithmList, 
			      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			      JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


		//panel.add(BorderLayout.SOUTH, label);
		panel.add(BorderLayout.CENTER, scroller);
		return panel;
	}

	private JPanel createWorkloadPanel() {

		JPanel containerPanel = new VerticalPanel();

		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JScrollPane scroll = new JScrollPane(createGrid());
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setPreferredSize(scroll.getMaximumSize());
		panel.add(scroll, BorderLayout.CENTER);
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(0, 1));

		JPanel database = new JPanel();

		ImageIcon iadapter = new javax.swing.ImageIcon(getClass().getResource("iadapter1.png"));

		JLabel icon = new JLabel(iadapter);

		buttons.add(icon);

		JButton button1 = new JButton();
		button1.setToolTipText("This button create the workloads");
		ImageIcon create = new javax.swing.ImageIcon(getClass().getResource("create.png"));
		button1.setIcon(create);
		buttons.add(button1);

		JButton button2 = new JButton();
		button1.setToolTipText("This button get data from the database");
		ImageIcon refresh = new javax.swing.ImageIcon(getClass().getResource("refresh1.png"));
		button2.setIcon(refresh);
		buttons.add(button2);

		JButton button3 = new JButton();
		button1.setToolTipText("This button delete all the workloads");
		ImageIcon trash = new javax.swing.ImageIcon(getClass().getResource("trash1.png"));
		button3.setIcon(trash);
		buttons.add(button3);

		JButton button4 = new JButton();
		button1.setToolTipText("This button delete the selected workload");
		ImageIcon delete = new javax.swing.ImageIcon(getClass().getResource("delete1.png"));
		button4.setIcon(delete);
		buttons.add(button4);

		JButton button5 = new JButton();
		ImageIcon save = new javax.swing.ImageIcon(getClass().getResource("save1.png"));
		button5.setIcon(save);
		buttons.add(button5);

		JButton button6 = new JButton();
		ImageIcon turnoff = new javax.swing.ImageIcon(getClass().getResource("turnoff.png"));
		button6.setIcon(turnoff);
		buttons.add(button6);

		JButton button7 = new JButton();
		ImageIcon export = new javax.swing.ImageIcon(getClass().getResource("export.jpeg"));
		button7.setIcon(export);

		buttons.add(button7);

		JButton button8 = new JButton();
		ImageIcon dna2 = new javax.swing.ImageIcon(getClass().getResource("dna2.png"));
		button8.setIcon(dna2);

		JButton button9 = new JButton("Export +");

		buttons.add(button8);
		buttons.add(button9);

		button1.addActionListener(new AddRowWorkloadAction(this, grid, wtableModel, null, null));
		button2.addActionListener(new RefreshRowWorkloadAction(this));

		button3.addActionListener(new ClearRowWorkloadAction(this, grid, wtableModel, null, null));

		button4.addActionListener(new DeleteRowAction(this, grid, wtableModel, null));

		button5.addActionListener(new SaveRowAction(this, wtableModel, null, null));

		button6.addActionListener(new DesactiveRowWorkloadAction(this, wtableModel));

		button7.addActionListener(new ExportWorkloadAction());

		button8.addActionListener(new NewGenerationWorkloadAction());

		button9.addActionListener(new ExportPositiveWorkloadAction());
		southPanel.add(buttons, BorderLayout.SOUTH);
		southPanel.add(database, BorderLayout.SOUTH);

		panel.add(southPanel, BorderLayout.SOUTH);

		containerPanel.add(panel, BorderLayout.CENTER);

		return containerPanel;
	}

	private JPanel createGraphPanel() {

		JPanel containerPanel = new VerticalPanel();

		containerPanel.add(GuiBuilderHelper.getComponentWithMargin(createChart(), 2, 2, 0, 2), BorderLayout.CENTER);

		return containerPanel;
	}

	public JTextField getThreadMax() {
		return threadMax;
	}

	public JTextField getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(JTextField minTemp) {
		this.minTemp = minTemp;
	}

	public void setThreadMax(JTextField threadMax) {
		this.threadMax = threadMax;
	}

	public String getLabelResource() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getStaticLabel() {
		return "nauberphkwork@WorkLoad Thread Group";
	}

	public TestElement createTestElement() {

		WorkLoadThreadGroup tg = new WorkLoadThreadGroup();

		modifyTestElement(tg);

		tg.setComment(JMeterPluginsUtils.getWikiLinkText(WIKIPAGE));

		return tg;
	}

	public PowerTableModel getWtableModel() {
		return wtableModel;
	}

	public void setWtableModel(PowerTableModel wtableModel) {
		this.wtableModel = wtableModel;
	}

	public void modifyTestElement(TestElement tg) {
		if (tg instanceof WorkLoadThreadGroup) {

			WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;

			utg.setThreadNumberMax(threadMax.getText());
			utg.setMaxTime(maxtime.getText());
			utg.setGenNumber(genNumber.getText());
			utg.setBestIndividuals(bestInd.getText());
			utg.setMinTemp(minTemp.getText());
			utg.setResponse90FitWeight(percentile90FitWeight.getText());
			utg.setResponse80FitWeight(percentile80FitWeight.getText());
			utg.setResponse70FitWeight(percentile70FitWeight.getText());
			utg.setResponseMaxFitWeight(responseMaxFitWeight.getText());
			utg.setTotalErrorFitWeight(totalErrorFitWeight.getText());
			utg.setUserFitWeight(userFitWeight.getText());
			utg.setPopulationSize(populationSize.getText());
			utg.setCollaborative(colaborative.isSelected());
			utg.setInitialGeneration(initialGeneration.getText());
			utg.setResponseTimeMaxPenalty(responseTimeMaxPenalty.getText());
			utg.setMutantProbabilty(mutantProbability.getText());
			utg.setAlgorithmList(algorithmList.getText());
			// utg.setMaxMemory(maxMemory.getText());
			// utg.setMaxCpuShare(maxCpuShare.getText());
			// utg.setDockerImage(dockerImage.getText());
			// utg.setIpDocker(ipDocker.getText());
			// utg.setSourcePortDocker(sourcePortDocker.getText());
			// utg.setDestPortDocker(destPortDocker.getText());
			// utg.setDockerCommandLine(dockerCommandLine.getText());

			if (grid == null) {
				createGrid();
			}

			if (grid.isEditing()) {
				grid.getCellEditor().stopCellEditing();
			}

			CollectionProperty rows = JMeterPluginsUtils.tableModelRowsToCollectionProperty(wtableModel,
					WorkLoadThreadGroup.DATA_PROPERTY);

			utg.setData(rows);

			LoopController controler = (LoopController) loopPanel.createTestElement();
			controler.setLoops(1);
			utg.setSamplerController(controler);

		}
		super.configureTestElement(tg);
	}

	@Override
	public void configure(TestElement tg) {

		super.configure(tg);

		WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;

		threadMax.setText(utg.getThreadNumberMax());
		maxtime.setText(utg.getMaxTime());
		genNumber.setText(utg.getGenNumber());
		bestInd.setText(utg.getBestIndividuals());
		minTemp.setText(utg.getMinTemp());
		percentile90FitWeight.setText(utg.getResponse90FitWeight());
		percentile80FitWeight.setText(utg.getResponse80FitWeight());
		percentile70FitWeight.setText(utg.getResponse70FitWeight());
		responseMaxFitWeight.setText(utg.getResponseMaxFitWeight());
		totalErrorFitWeight.setText(utg.getTotalErrorFitWeight());
		userFitWeight.setText(utg.getUserFitWeight());
		colaborative.setSelected(utg.getCollaborative());
		populationSize.setText(utg.getPopulationSize());
		initialGeneration.setText(utg.getInitialGeneration());
		responseTimeMaxPenalty.setText(utg.getResponseTimeMaxPenalty());
		mutantProbability.setText(utg.getMutantProbability());
		algorithmList.setColumns(100);
		if(utg.getAlgorithmList().length()==0){
			List<String> classes = SearchClass.getClasses();

			String classesString = "";
			
			for (String string : classes) {
				classesString += string + ",";
			}
			classesString += "";
			classesString=classesString.substring(0,classesString.length()-2);
			System.out.println("Algorithms found: "+classesString);
			utg.setAlgorithmList(classesString);
			
			
		}
		algorithmList.setText(utg.getAlgorithmList());
		// maxCpuShare.setText(utg.getMaxCpuShare());
		// maxMemory.setText(utg.getMaxMemory());
		// dockerImage.setText(utg.getDockerImage());
		// ipDocker.setText(utg.getIpDocker());
		// sourcePortDocker.setText(utg.getSourcePortDocker());
		// destPortDocker.setText(utg.getDestPortDocker());
		// dockerCommandLine.setText(utg.getDockerCommandLine());

		JMeterProperty threadValues = utg.getData();
		if (!(threadValues instanceof NullProperty)) {
			CollectionProperty columns = (CollectionProperty) threadValues;

			wtableModel.removeTableModelListener(this);
			JMeterPluginsUtils.collectionPropertyToTableModelRows(columns, wtableModel);

			try {
				JMeterPluginsUtils.derbyAgentToTableModelRows(wtableModelAgents);
				JMeterPluginsUtils.collectionPropertyToDerby(columns, utg, String.valueOf(utg.getGeneration()));
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			wtableModel.addTableModelListener(this);
			updateUI();
		} else {
			log.warn("Received null property instead of collection");
		}

		TestElement te = (TestElement) tg.getProperty(AbstractThreadGroup.MAIN_CONTROLLER).getObjectValue();
		if (te != null) {
			loopPanel.configure(te);
		}

	}

	public JCheckBox getColaborative() {
		return colaborative;
	}

	public void setColaborative(JCheckBox colaborative) {
		this.colaborative = colaborative;
	}

	@Override
	public void updateUI() {
		super.updateUI();

		if (wtableModel == null) {
			createTableModel();
		}

	}

	private JPanel createControllerPanel() {
		loopPanel = new LoopControlPanel(false);
		LoopController looper = (LoopController) loopPanel.createTestElement();
		looper.setLoops(1);
		looper.setContinueForever(true);
		loopPanel.configure(looper);
		return loopPanel;
	}

	public Component createTabAgent(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(createGridAgent());
		scroll.setPreferredSize(scroll.getMinimumSize());
		panel.add(scroll, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		JButton button1 = new JButton("Delete");
		button1.addActionListener(new DeleteAgentAction(gridAgents, wtableModelAgents, this));

		JButton button2 = new JButton("Refresh");
		button2.addActionListener(new RefreshRowWorkloadAction(this));

		buttons.add(button1);
		buttons.add(button2);
		panel.add(buttons, BorderLayout.SOUTH);

		tab1.addTab("Agents", panel);

		return tab1;

	}

	public Component createLogPanel(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(createGridLog());
		scroll.setPreferredSize(scroll.getMinimumSize());
		panel.add(scroll, BorderLayout.CENTER);
		tab1.addTab("Log", panel);

		return tab1;

	}

	public Component createTabParameters(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel param = new JPanel();
		param.setLayout(new GridLayout(0, 2));
		threadMax = new JTextField("10", 5);
		maxtime = new JTextField("30000", 5);
		genNumber = new JTextField("3", 5);
		bestInd = new JTextField("1", 5);
		minTemp = new JTextField("0", 15);
		mutantProbability = new JTextField("1", 15);
		param.add(new JLabel("Thread Max Number"));
		param.add(threadMax);
		param.add(new JLabel("Max response time"));
		param.add(maxtime);
		param.add(new JLabel("Generations Number"));
		param.add(genNumber);
		param.add(new JLabel("Number of Best Individuals for CrossOver"));
		param.add(bestInd);
		param.add(new JLabel("Minimal Simulated Annealing Temperature"));
		param.add(minTemp);
		param.add(new JLabel("Mutation Probability Number (1-9)"));
		param.add(mutantProbability);
		panel.add(param, BorderLayout.CENTER);

		tab1.addTab("Parameters", panel);

		return tab1;

	}

	public Component createTabDocker(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel param = new JPanel();
		param.setLayout(new GridLayout(0, 2));
		maxMemory = new JTextField("10", 5);
		maxCpuShare = new JTextField("10", 5);
		dockerImage = new JTextField("image", 5);
		ipDocker = new JTextField("127.0.0.1", 5);
		sourcePortDocker = new JTextField("8080", 5);
		destPortDocker = new JTextField("8080", 5);
		dockerCommandLine = new JTextField("", 5);

		param.add(new JLabel("Max Memory"));
		param.add(maxMemory);
		param.add(new JLabel("Max Cpu Share"));
		param.add(maxCpuShare);
		param.add(new JLabel("Docker Image"));
		param.add(dockerImage);
		param.add(new JLabel("Ip Docker"));
		param.add(ipDocker);
		param.add(new JLabel("Source Port Docker"));
		param.add(sourcePortDocker);
		param.add(new JLabel("Destination Port Docker"));
		param.add(destPortDocker);
		param.add(new JLabel("Docker Command Line"));
		param.add(dockerCommandLine);
		panel.add(param, BorderLayout.CENTER);

		tab1.addTab("Docker", panel);

		return tab1;

	}

	public JTextField getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(JTextField maxMemory) {
		this.maxMemory = maxMemory;
	}

	public JTextField getMaxCpuShare() {
		return maxCpuShare;
	}

	public void setMaxCpuShare(JTextField maxCpuShare) {
		this.maxCpuShare = maxCpuShare;
	}

	public JTextField getDockerImage() {
		return dockerImage;
	}

	public void setDockerImage(JTextField dockerImage) {
		this.dockerImage = dockerImage;
	}

	public Component createPopulationParameters(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel param = new JPanel();
		param.setLayout(new GridLayout(0, 2));
		populationSize = new JTextField("2", 5);
		colaborative = new JCheckBox("Collaborative?", true);
		initialGeneration = new JTextField("1", 5);

		param.add(new JLabel("Population Size"));
		param.add(populationSize);
		param.add(new JLabel("Colaborative"));
		param.add(colaborative);
		param.add(new JLabel("Initial Generation"));
		param.add(initialGeneration);
		panel.add(param, BorderLayout.CENTER);

		tab1.addTab("Population", panel);

		return tab1;

	}

	public Component createFitParameters(JTabbedPane tab1) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel param = new JPanel();
		param.setLayout(new GridLayout(0, 2));
		percentile90FitWeight = new JTextField("90", 5);
		percentile80FitWeight = new JTextField("20", 5);
		percentile70FitWeight = new JTextField("10", 5);
		responseMaxFitWeight = new JTextField("10", 5);
		totalErrorFitWeight = new JTextField("-10", 5);
		userFitWeight = new JTextField("10", 5);
		responseTimeMaxPenalty = new JTextField("-10", 5);

		param.add(new JLabel("Percentile 90 Fit Weight"));
		param.add(percentile90FitWeight);
		param.add(new JLabel("Percentile 80 Fit Weight"));
		param.add(percentile80FitWeight);
		param.add(new JLabel("Percentile 70 Fit Weight"));
		param.add(percentile70FitWeight);

		param.add(new JLabel("Response Max Fit Weight"));
		param.add(responseMaxFitWeight);
		param.add(new JLabel("Total Error Fit Weight"));
		param.add(totalErrorFitWeight);
		param.add(new JLabel("User Fit Weight"));
		param.add(userFitWeight);
		param.add(new JLabel("Response Time Max Across Penalty"));
		param.add(responseTimeMaxPenalty);

		panel.add(param, BorderLayout.CENTER);

		tab1.addTab("FIT", panel);

		return tab1;

	}

	private Component createChart() {
		if (this.chart == null) {
			chart = new GraphPanelChart(false, true);
			model = new ConcurrentHashMap<String, AbstractGraphRow>();
			chart.setRows(model);
			chart.getChartSettings().setDrawFinalZeroingLines(true);
			chart.setxAxisLabel("Elapsed time");
			chart.setYAxisLabel("Number of active threads");
			chart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return chart;
	}

	public void tableChanged(TableModelEvent e) {
		updateUI();
	}

	private void createTableModel() {
		wtableModel = new PowerTableModel(columnIdentifiers, columnClasses);
		wtableModel.addTableModelListener(this);

		if (this.chart == null)
			createChart();

		if (grid == null) {
			createGrid();
		}
		grid.setModel(wtableModel);

	}

	private void createTableModelAgent() {
		wtableModelAgents = new PowerTableModel(columnIdentifiersAgent, columnClassesAgent);
		wtableModelAgents.addTableModelListener(this);

		if (gridAgents == null) {
			createGridAgent();
		}
		gridAgents.setModel(wtableModelAgents);

	}

	private void createTableModelLog() {
		wtableLog = new PowerTableModel(columnIdentifiersLog, columnClassesLog);
		wtableLog.addTableModelListener(this);

		if (gridLog == null) {
			createGridLog();
		}
		gridLog.setModel(wtableLog);

	}

	public void editingStopped(ChangeEvent e) {
		updateUI();
	}

	@Override
	public void clearGui() {
		super.clearGui();
		if (wtableModel != null) {
			wtableModel.clearData();
		}
		if (wtableModelAgents != null) {
			wtableModelAgents.clearData();
		}
	}

	public void editingCanceled(ChangeEvent arg0) {

	}

	public WorkLoadThreadGroupGUI() {
		super();
		init();
	}

	public static void plotGraph(WorkLoad workLoad, GraphPanelChart chart1) {

		ConcurrentHashMap<String, AbstractGraphRow> model1 = new ConcurrentHashMap<String, AbstractGraphRow>();
		chart1.setRows(model1);

		GraphRowSumValues row = new GraphRowSumValues();
		row.setColor(Color.RED);
		row.setDrawLine(true);
		row.setMarkerSize(AbstractGraphRow.MARKER_SIZE_NONE);
		row.setDrawThickLines(true);

		workLoad.plotGraph(row, workLoad);

		model1.put("Expected parallel users count", row);
		chart1.invalidateCache();
		chart1.repaint();

	}

}
