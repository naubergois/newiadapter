package br.unifor.iadapter.threadGroup.workload;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jorphan.collections.Data;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class WorkLoadTable extends PowerTableModel {

	private WorkLoadThreadGroup workLoadThreadGroup;

	public WorkLoadThreadGroup getWorkLoadThreadGroup() {
		return workLoadThreadGroup;
	}

	public void setWorkLoadThreadGroup(WorkLoadThreadGroup workLoadThreadGroup) {
		this.workLoadThreadGroup = workLoadThreadGroup;
	}

	private static final Logger log = LoggingManager.getLoggerForClass();

	private static final long serialVersionUID = 233L;

	private Data model = new Data();

	private Class<?>[] columnClasses;

	public WorkLoadTable(String[] headers, Class<?>[] classes) {
		if (headers.length != classes.length) {
			throw new IllegalArgumentException(
					"Header and column array sizes differ");
		}
		model.setHeaders(headers);
		columnClasses = classes;
	}

	public WorkLoadTable(WorkLoadThreadGroup workLoadThreadGroup) {

		this.workLoadThreadGroup = workLoadThreadGroup;
	}

	public void setRowValues(int row, Object[] values) {
		if (values.length != model.getHeaderCount()) {
			throw new IllegalArgumentException("Incorrect number of data items");
		}
		model.setCurrentPos(row);
		for (int i = 0; i < values.length; i++) {
			model.addColumnValue(model.getHeaders()[i], values[i]);
		}
	}

	public Data getData() {
		return model;
	}

	public void addNewColumn(String colName, Class<?> colClass) {
		model.addHeader(colName);
		Class<?>[] newClasses = new Class[columnClasses.length + 1];
		System.arraycopy(columnClasses, 0, newClasses, 0, columnClasses.length);
		newClasses[newClasses.length - 1] = colClass;
		columnClasses = newClasses;
		Object defaultValue = createDefaultValue(columnClasses.length - 1);
		model.setColumnData(colName, defaultValue);
		this.fireTableStructureChanged();
	}

	@Override
	public void removeRow(int row) {
		log.debug("remove row: " + row);
		if (model.size() > row) {
			log.debug("Calling remove row on Data");
			model.removeRow(row);
		}
	}

	public void removeColumn(int col) {
		model.removeColumn(col);
		this.fireTableStructureChanged();
	}

	public void setColumnData(int col, List<?> data) {
		model.setColumnData(col, data);
	}

	public List<?> getColumnData(String colName) {
		return model.getColumnAsObjectArray(colName);
	}

	public void clearData() {
		String[] headers = model.getHeaders();
		model = new Data();
		model.setHeaders(headers);
		this.fireTableDataChanged();
	}

	@Override
	public void addRow(Object data[]) {
		if (data.length != model.getHeaderCount()) {
			throw new IllegalArgumentException("Incorrect number of data items");
		}
		model.setCurrentPos(model.size());
		for (int i = 0; i < data.length; i++) {
			model.addColumnValue(model.getHeaders()[i], data[i]);
		}

	}

	public void addNewRow() {

	}

	private Object[] createDefaultRow() {
		return new Object[5];
	}

	public Object[] getRowData(int row) {
		WorkLoad workload = this.workLoadThreadGroup.getListWorkLoads()
				.get(row);
		Object[] rowObject = new Object[5];
		rowObject[0] = workload.getName();
		rowObject[1] = workload.getNumThreads();
		rowObject[2] = workload.getWorstResponseTime();
		rowObject[3] = workload.getStartRampUp();
		rowObject[4] = workload.getEndRampUp();
		return rowObject;
	}

	private Object createDefaultValue(int i) {
		Class<?> colClass = getColumnClass(i);
		try {
			return colClass.newInstance();
		} catch (Exception e) {
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { String.class });
				return constr.newInstance(new Object[] { "" });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Integer.TYPE });
				return constr.newInstance(new Object[] { Integer.valueOf(0) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Long.TYPE });
				return constr.newInstance(new Object[] { Long.valueOf(0L) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Boolean.TYPE });
				return constr.newInstance(new Object[] { Boolean.FALSE });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Float.TYPE });
				return constr.newInstance(new Object[] { Float.valueOf(0F) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Double.TYPE });
				return constr.newInstance(new Object[] { Double.valueOf(0D) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Character.TYPE });
				return constr
						.newInstance(new Object[] { Character.valueOf(' ') });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Byte.TYPE });
				return constr.newInstance(new Object[] { Byte
						.valueOf(Byte.MIN_VALUE) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
			try {
				Constructor<?> constr = colClass
						.getConstructor(new Class[] { Short.TYPE });
				return constr.newInstance(new Object[] { Short
						.valueOf(Short.MIN_VALUE) });
			} catch (NoSuchMethodException err) {
			} catch (InstantiationException err) {
			} catch (IllegalAccessException err) {
			} catch (InvocationTargetException err) {
			}
		}
		return "";
	}

	/**
	 * Required by table model interface.
	 *
	 * @return the RowCount value
	 */
	@Override
	public int getRowCount() {
		if (this.getWorkLoadThreadGroup() != null) {
			return this.getWorkLoadThreadGroup().getListWorkLoads().size();
		} else {
			return 0;
		}
	}

	/**
	 * Required by table model interface.
	 *
	 * @return the ColumnCount value
	 */
	@Override
	public int getColumnCount() {
		return 5;
	}

	/**
	 * Required by table model interface.
	 *
	 * @return the ColumnName value
	 */
	@Override
	public String getColumnName(int column) {
		String[] columns = { "Name", "Type", "Response Time", "Fit", "Error" };
		return columns[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// all table cells are editable
		return false;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return String.class;
	}

	/**
	 * Required by table model interface. return the ValueAt value
	 */
	@Override
	public Object getValueAt(int row, int column) {
		WorkLoad workload = this.getWorkLoadThreadGroup().getListWorkLoads()
				.get(row);
		if (column == 0)
			return workload.getName();
		if (column == 1)
			return workload.getNumThreads();
		if (column == 2)
			return workload.getWorstResponseTime();
		if (column == 3)
			return workload.getStartRampUp();
		if (column == 4)
			return workload.getEndRampUp();
		return 0;
	}

	/**
	 * Sets the ValueAt attribute of the Arguments object.
	 *
	 * @param value
	 *            the new ValueAt value
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
	}
}
