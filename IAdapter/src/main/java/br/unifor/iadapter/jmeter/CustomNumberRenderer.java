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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import org.apache.jorphan.gui.NumberRenderer;

public class CustomNumberRenderer extends NumberRenderer {
    private NumberFormat customFormatter = null;

    public CustomNumberRenderer() {
        super();
    }

    public CustomNumberRenderer(String format) {
       super(format);
    }

    public CustomNumberRenderer(String format, char groupingSeparator) {
        super();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(groupingSeparator);
        customFormatter = new DecimalFormat(format, symbols);
    }

   @Override
    public void setValue(Object value) {
        String str = "";
        if(value != null) {
           if(customFormatter != null) {
              str = customFormatter.format(value);
           } else {
              str = formatter.format(value);
           }
        }
        setText(str);
    }
}
