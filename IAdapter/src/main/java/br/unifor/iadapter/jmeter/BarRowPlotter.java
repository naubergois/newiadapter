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

import java.awt.Composite;
import java.awt.Graphics2D;

import org.apache.jorphan.gui.NumberRenderer;

public class BarRowPlotter extends AbstractRowPlotter {

   public BarRowPlotter(ChartSettings chartSettings, NumberRenderer labelRenderer) {
      super(chartSettings, labelRenderer);
      allowMarkers = false;
   }

   @Override
   protected void processPoint(Graphics2D g2d, int granulation) {
      //fix bar flickering
      if (y < chartRect.y) {
         y = chartRect.y;
      }
      if (isChartPointValid(x + 1, y)) { //as we draw bars, xMax values must be rejected
         int x2 = chartRect.x + (int) ((calcPointX + granulation - minXVal) * dxForDVal) - x - 1;
         Composite oldComposite = g2d.getComposite();
         g2d.setComposite(chartSettings.getBarComposite());

         int yHeight = chartRect.y + chartRect.height - y;

         g2d.fillRect(x, y - 1, x2, yHeight + 1);
         g2d.setComposite(oldComposite);
      }
   }
}
