package br.unifor.iadapter.jmeter;


/** {@inheritDoc} */
public class GraphPanelChartSimpleElement extends
		AbstractGraphPanelChartElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double value = 0;

	/**
	 *
	 * @param yVal
	 */
	public GraphPanelChartSimpleElement(double yVal) {
		add(yVal);
	}

	GraphPanelChartSimpleElement() {
	}

	/**
	 *
	 * @param yVal
	 */
	public void add(double yVal) {
		value = yVal;
	}

	/** {@inheritDoc} */
	public double getValue() {
		return value;
	}
}
