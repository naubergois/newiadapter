package br.unifor.iadapter.threadGroup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import kg.apc.charting.AbstractGraphRow;
import kg.apc.charting.DateTimeRenderer;
import kg.apc.charting.GraphPanelChart;
import kg.apc.charting.rows.GraphRowSumValues;
import kg.apc.jmeter.JMeterPluginsUtils;
import kg.apc.jmeter.gui.GuiBuilderHelper;
import kg.apc.jmeter.threads.UltimateThreadGroup;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class WorkLoadThreadGroupGUI extends AbstractThreadGroupGui implements
		TableModelListener, CellEditorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String WIKIPAGE = "UltimateThreadGroup";
	private static final Logger log = LoggingManager.getLoggerForClass();
	/**
     *
     */
	protected ConcurrentHashMap<String, AbstractGraphRow> model;
	private GraphPanelChart chart;
	/**
     *
     */
	public static final String[] columnIdentifiers = new String[] {
			"Max Threads Count", "Max Delay, sec", "Max Time, sec",
			"Max Hold Load For, sec", "Shutdown Time" };

	public static final Class[] columnClasses = new Class[] { String.class,
			String.class, String.class, String.class, String.class };
	public static final Integer[] defaultValues = new Integer[] { 100, 0, 30,
			60, 10 };
	private LoopControlPanel loopPanel;
	protected PowerTableModel tableModel;
	protected JTable grid;
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
		containerPanel.add(GuiBuilderHelper.getComponentWithMargin(
				createChart(), 2, 2, 0, 2), BorderLayout.CENTER);
		tabbedPane.addTab("Main", containerPanel);
		add(tabbedPane, BorderLayout.CENTER);

		// this magic LoopPanel provides functionality for thread loops
		createControllerPanel();
	}

	private JTable createGrid() {
		grid = new JTable();
		grid.getDefaultEditor(String.class).addCellEditorListener(this);
		createTableModel();
		grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		grid.setMinimumSize(new Dimension(200, 100));

		return grid;
	}

	private JPanel createParamsPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("Threads Schedule"));
		panel.setPreferredSize(new Dimension(200, 200));

		JScrollPane scroll = new JScrollPane(createGrid());
		scroll.setPreferredSize(scroll.getMinimumSize());
		panel.add(scroll, BorderLayout.CENTER);
		buttons = new JPanel();

		JButton button1 = new JButton("Create Workloads");
		buttons.add(button1);
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				WorkLoadThreadGroupGUI.createTabChart(tabbedPane);

			}
		});
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}

	public String getLabelResource() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getStaticLabel() {
		return JMeterPluginsUtils.prefixLabel("WorkLoad Thread Group");
	}

	public TestElement createTestElement() {
		// log.info("Create test element");
		WorkLoadThreadGroup tg = new WorkLoadThreadGroup();
		modifyTestElement(tg);
		tg.setComment(JMeterPluginsUtils.getWikiLinkText(WIKIPAGE));

		return tg;
	}

	public void modifyTestElement(TestElement tg) {
		
		
		
		// log.info("Modify test element");
		if (grid == null) {
			createGrid();
		}

		if (grid.isEditing()) {
			grid.getCellEditor().stopCellEditing();
		}

		if (tg instanceof WorkLoadThreadGroup) {
			WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;
			CollectionProperty rows = JMeterPluginsUtils
					.tableModelRowsToCollectionProperty(tableModel,
							WorkLoadThreadGroup.DATA_PROPERTY);
			utg.setData(rows);
			if (loopPanel == null) {
				createControllerPanel();
			}

			LoopController controler=(LoopController) loopPanel
					.createTestElement();
			controler.setLoops(2);
			utg.setSamplerController(controler);
		}
		super.configureTestElement(tg);
	}

	@Override
	public void configure(TestElement tg) {
		// log.info("Configure");
		super.configure(tg);
		
		WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;
		// log.info("Configure "+utg.getName());
		JMeterProperty threadValues = utg.getData();
		if (!(threadValues instanceof NullProperty)) {
			CollectionProperty columns = (CollectionProperty) threadValues;

			tableModel.removeTableModelListener(this);
			JMeterPluginsUtils.collectionPropertyToTableModelRows(columns,
					tableModel);
			tableModel.addTableModelListener(this);
			updateUI();
		} else {
			log.warn("Received null property instead of collection");
		}

		TestElement te = (TestElement) tg.getProperty(
				AbstractThreadGroup.MAIN_CONTROLLER).getObjectValue();
		if (te != null) {
			if (te instanceof LoopController) {
				((LoopController) te).setLoops(2);
			}
			
			loopPanel.configure(te);
			
		}
		// buttons.checkDeleteButtonStatus();
	}

	@Override
	public void updateUI() {
		super.updateUI();

		if (tableModel == null) {
			createTableModel();
		}
		WorkLoadThreadGroup utgForPreview = new WorkLoadThreadGroup();
		utgForPreview.setData(JMeterPluginsUtils
				.tableModelRowsToCollectionPropertyEval(tableModel,
						UltimateThreadGroup.DATA_PROPERTY));
		updateChart(utgForPreview);

	}

	private void updateChart(WorkLoadThreadGroup tg) {
		tg.testStarted();
		if (model == null) {
			createChart();
		}

		model.clear();
		GraphRowSumValues row = new GraphRowSumValues();
		row.setColor(Color.RED);
		row.setDrawLine(true);
		row.setMarkerSize(AbstractGraphRow.MARKER_SIZE_NONE);
		row.setDrawThickLines(true);

		final HashTree hashTree = new HashTree();
		hashTree.add(new LoopController());
		JMeterThread thread = new JMeterThread(hashTree, null, null);

		long now = System.currentTimeMillis();

		chart.setxAxisLabelRenderer(new DateTimeRenderer(
				DateTimeRenderer.HHMMSS, now - 1)); // -1 because
													// row.add(thread.getStartTime()
													// - 1, 0)
		chart.setForcedMinX(now);

		row.add(now, 0);

		// users in
		int numThreads = tg.getNumThreads();
		log.debug("Num Threads: " + numThreads);
		for (int n = 0; n < numThreads; n++) {
			thread.setThreadNum(n);
			thread.setThreadName(Integer.toString(n));
			tg.scheduleThread(thread, now);
			row.add(thread.getStartTime() - 1, 0);
			row.add(thread.getStartTime(), 1);
		}

		tg.testStarted();
		// users out
		for (int n = 0; n < tg.getNumThreads(); n++) {
			thread.setThreadNum(n);
			thread.setThreadName(Integer.toString(n));
			tg.scheduleThread(thread, now);
			row.add(thread.getEndTime() - 1, 0);
			row.add(thread.getEndTime(), -1);
		}

		model.put("Expected parallel users count", row);
		chart.invalidateCache();
		chart.repaint();
	}

	private JPanel createControllerPanel() {
		loopPanel = new LoopControlPanel(false);
		LoopController looper = (LoopController) loopPanel.createTestElement();
		looper.setLoops(2);
		looper.setContinueForever(true);
		loopPanel.configure(looper);
		return loopPanel;
	}

	public static Component createTabChart(JTabbedPane tab1) {
		JPanel panel = new JPanel();

		GraphPanelChart chart1 = new GraphPanelChart(false, true);
		ConcurrentHashMap<String, AbstractGraphRow> model1 = new ConcurrentHashMap<String, AbstractGraphRow>();
		chart1.setRows(model1);
		chart1.getChartSettings().setDrawFinalZeroingLines(true);
		chart1.setxAxisLabel("Elapsed time");
		chart1.setYAxisLabel("Number of active threads");
		chart1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		panel.add(chart1);

		tab1.addTab("Test1", chart1);

		GraphRowSumValues row = new GraphRowSumValues();
		row.setColor(Color.RED);
		row.setDrawLine(true);
		row.setMarkerSize(AbstractGraphRow.MARKER_SIZE_NONE);
		row.setDrawThickLines(true);
		long now = System.currentTimeMillis();
		row.add(now, 0);

		final HashTree hashTree = new HashTree();
		hashTree.add(new LoopController());

		// users in
		// int numThreads = tg.getNumThreads();
		log.debug("Num Threads: " + 50);
		WorkLoad workload = new WorkLoad();
		workload.setNumThreads(10);
		workload.plotGraph(row);
		workload.setTab(tab1);

		model1.put("Expected parallel users count", row);
		chart1.invalidateCache();
		chart1.repaint();
		WorkLoadTests.getTests().add(workload);
		return chart1;

	}

	private Component createChart() {
		chart = new GraphPanelChart(false, true);
		model = new ConcurrentHashMap<String, AbstractGraphRow>();
		chart.setRows(model);
		chart.getChartSettings().setDrawFinalZeroingLines(true);
		chart.setxAxisLabel("Elapsed time");
		chart.setYAxisLabel("Number of active threads");
		chart.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		return chart;
	}

	public void tableChanged(TableModelEvent e) {
		// log.info("Model changed");
		updateUI();
	}

	private void createTableModel() {
		tableModel = new PowerTableModel(columnIdentifiers, columnClasses);
		tableModel.addTableModelListener(this);
		if (grid == null) {
			createGrid();
		}

		grid.setModel(tableModel);
	}

	public void editingStopped(ChangeEvent e) {
		// log.info("Editing stopped");
		updateUI();
	}

	@Override
	public void clearGui() {
		super.clearGui();

		if (tableModel != null) {
			tableModel.clearData();
		}
	}

	public void editingCanceled(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	public WorkLoadThreadGroupGUI() {
		super();
		init();
	}

}
