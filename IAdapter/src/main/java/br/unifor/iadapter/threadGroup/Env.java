package br.unifor.iadapter.threadGroup;



import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

public class Env extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__env";

    // Number of parameters expected - used to reject invalid calls
    private static final int MIN_PARAMETER_COUNT = 1;
    private static final int MAX_PARAMETER_COUNT = 3;

    static {
        desc.add("Name of environment variable");
        desc.add("Name of variable in which to store the result (optional)");
        desc.add("Default value");
    }

    private CompoundVariable[] values;

    /**
     * No-arg constructor.
     */
    public Env() {
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, MIN_PARAMETER_COUNT, MAX_PARAMETER_COUNT);
        values = parameters.toArray(new CompoundVariable[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReferenceKey() {
        return KEY;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getArgumentDesc() {
        return desc;
    }
	@Override
	public String execute(SampleResult previousResult, Sampler currentSampler)
			throws InvalidVariableException {
		// TODO Auto-generated method stub
		return null;
	}
}
