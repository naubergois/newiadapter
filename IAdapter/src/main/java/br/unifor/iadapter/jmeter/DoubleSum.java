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

package br.unifor.iadapter.jmeter;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides a DoubleSum function that adds two or more Double values.
 * Mostly copied from LongSum
 */
public class DoubleSum extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__doubleSum";

    static {
        desc.add("First double to add");
        desc.add("Second long to add - further doubles can be summed by adding further arguments");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private Object[] values;

    /**
     * No-arg constructor.
     */
    public DoubleSum() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {

        JMeterVariables vars = getVariables();

        Double sum = 0D;
        String varName = ((CompoundVariable) values[values.length - 1]).execute().trim();

        for (int i = 0; i < values.length - 1; i++) {
            sum += Double.parseDouble(((CompoundVariable) values[i]).execute());
        }

        try {
            sum += Double.parseDouble(varName);
            varName = null; // there is no variable name
        } catch (NumberFormatException ignored) {
        }

        String totalString = Double.toString(sum);
        if (vars != null && varName != null && varName.length() > 0) {// vars will be null on TestPlan
            vars.put(varName, totalString);
        }

        return totalString;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 2);
        values = parameters.toArray();
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
}
