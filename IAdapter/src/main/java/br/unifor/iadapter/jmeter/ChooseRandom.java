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
import java.util.Random;

public class ChooseRandom extends AbstractFunction {

    private static final List<String> desc = new LinkedList<String>();
    private static final String KEY = "__chooseRandom";
    private static final Random random = new Random(System.currentTimeMillis());

    static {
        desc.add("Any number of values to choose from");
        desc.add("Last value must be variable name where choice will be stored");
    }

    private Object[] values;

    /**
     * No-arg constructor.
     */
    public ChooseRandom() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String execute(SampleResult previousResult, Sampler currentSampler)
            throws InvalidVariableException {
        JMeterVariables vars = getVariables();
        String varName = ((CompoundVariable) values[values.length - 1]).execute().trim();
        int index = random.nextInt(values.length - 1);
        String choice = ((CompoundVariable) values[index]).execute();

        if (vars != null && varName != null && varName.length() > 0) {// vars will be null on TestPlan
            vars.put(varName, choice);
        }

        return choice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkMinParameterCount(parameters, 3);
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
