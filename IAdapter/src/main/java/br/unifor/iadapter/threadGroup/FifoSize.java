package br.unifor.iadapter.threadGroup;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

public class FifoSize extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__fifoSize";

    static {
        desc.add("FIFO queue name to get elements count");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private Object[] values;

    public FifoSize() {
    }

    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        String fifoName = ((CompoundVariable) values[0]).execute();

        int size = FifoMap.getInstance().length(fifoName);
        String value = Integer.toString(size);

        JMeterVariables vars = getVariables();
        if (vars != null && values.length > 1) {
            String varName = ((CompoundVariable) values[1]).execute().trim();
            vars.put(varName, value);
        }

        return value;
    }

    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 1);
        values = parameters.toArray();
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    public List<String> getArgumentDesc() {
        return desc;
    }
}
