package br.unifor.iadapter.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeModel;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.threadGroup.workload.WorkLoadController;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class FindService {

	private static HashTree tree;
	private static JMeterTreeModel treeModel;
	private static JMeterTreeNode root;

	public static JMeterTreeNode getRootWithGui() throws Exception {

		JMeterTreeModel treeModel = GuiPackage.getInstance().getTreeModel();

		return (JMeterTreeNode) treeModel.getRoot();

	}

	public static JMeterTreeNode getRootWithNoGui(ListedHashTree threadGroupTree)
			throws Exception {

		JMeterTreeModel model = new JMeterTreeModel(new Object());
		JMeterTreeNode node = new JMeterTreeNode();
		model.addSubTree(threadGroupTree, node);

		return (JMeterTreeNode) model.getRoot();

	}

	public static List<JMeterTreeNode> searchWorkLoadControllerWithGui() {
		try {
			JMeterTreeNode root = getRootWithGui();
			return searchTransaction(root, new ArrayList<JMeterTreeNode>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static List<JMeterTreeNode> searchWorkLoadThreadGroupWithGui() {
		try {
			JMeterTreeNode root = getRootWithGui();
			return searchThreadGroup(root, new ArrayList<JMeterTreeNode>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static List<TestElement> searchWorkLoadControllerWithNoGui(
			ListedHashTree threadGroupTree) {

		if (threadGroupTree != null) {
			try {

				ArrayList<Object> objects = new ArrayList<Object>();
				Set keys = threadGroupTree.keySet();
				for (Object object : keys) {
					HashTree tree = threadGroupTree.getTree(object);
					Object[] objects2 = tree.getArray();
					for (Object object3 : objects2) {
						objects.add(object3);
					}

				}

				return searchTransactionNoGui(objects.toArray(),
						new ArrayList<TestElement>());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

	public static List<TestElement> searchWorkLoadThreadGroupWithNoGui(
			ListedHashTree threadGroupTree) {
		try {
			ArrayList<TestElement> objectsReturn = new ArrayList<TestElement>();

			ArrayList<Object> objects = new ArrayList<Object>();
			Set keys = threadGroupTree.keySet();
			for (Object object : keys) {
				if (object instanceof WorkLoadThreadGroup) {
					if (!(objectsReturn.contains(object))) {
						objectsReturn.add((TestElement) object);
					}
				}
				HashTree tree = threadGroupTree.getTree(object);
				Object[] objects2 = tree.getArray();
				for (Object object3 : objects2) {
					objects.add(object3);
				}

			}

			return searchWorkLoadThreadGroupNoGui(objects.toArray(),
					objectsReturn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private static List<JMeterTreeNode> searchTransaction(JMeterTreeNode root,
			List<JMeterTreeNode> list) throws Exception {

		Enumeration<JMeterTreeNode> enumr = root.children();

		if (list == null) {
			list = new ArrayList<JMeterTreeNode>();
		}

		while (enumr.hasMoreElements()) {
			JMeterTreeNode child = enumr.nextElement();
			if (child.getTestElement() instanceof WorkLoadController) {
				if (!(list.contains(child))) {
					list.add(child);
				}
			}
			List<JMeterTreeNode> listChild = searchTransaction(child, list);
			for (JMeterTreeNode node : listChild) {
				if (!(list.contains(node))) {
					list.add(node);
				}
			}

		}
		return list;

	}

	private static List<JMeterTreeNode> searchThreadGroup(JMeterTreeNode root,
			List<JMeterTreeNode> list) throws Exception {

		Enumeration<JMeterTreeNode> enumr = root.children();

		if (list == null) {
			list = new ArrayList<JMeterTreeNode>();
		}

		while (enumr.hasMoreElements()) {
			JMeterTreeNode child = enumr.nextElement();
			if (child.getTestElement() instanceof WorkLoadThreadGroup) {
				if (!(list.contains(child))) {
					list.add(child);
				}
			}
			List<JMeterTreeNode> listChild = searchThreadGroup(child, list);
			for (JMeterTreeNode node : listChild) {
				if (!(list.contains(node))) {
					list.add(node);
				}
			}

		}
		return list;

	}

	private static List<TestElement> searchTransactionNoGui(Object[] object,
			List<TestElement> list) throws Exception {

		if (list == null) {
			list = new ArrayList<TestElement>();
		}

		for (Object element : object) {
			TestElement child = (TestElement) element;

			if (child instanceof WorkLoadController) {
				if (!(list.contains(child))) {
					list.add(child);
				}
			}

		}
		return list;

	}

	private static List<TestElement> searchWorkLoadThreadGroupNoGui(
			Object[] object, List<TestElement> list) throws Exception {

		if (list == null) {
			list = new ArrayList<TestElement>();
		}

		for (Object element : object) {
			TestElement child = (TestElement) element;

			if (child instanceof WorkLoadThreadGroup) {
				if (!(list.contains(child))) {
					list.add(child);
				}
			}

		}
		return list;

	}

}
