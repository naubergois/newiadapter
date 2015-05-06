package br.unifor.iadapter.jmeter;

import java.io.Serializable;

public abstract class AbstractGraphPanelChartElement implements Serializable {

    public abstract double getValue();

    public abstract void add(double val);

    public boolean isPointRepresentative(int limit) {
        return true;
    }
}
