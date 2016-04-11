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

import java.awt.Graphics2D;

import org.apache.jorphan.gui.NumberRenderer;

public class LineRowPlotter extends AbstractRowPlotter {

	public LineRowPlotter(ChartSettings chartSettings,
			NumberRenderer labelRenderer) {
		super(chartSettings, labelRenderer);
		allowMarkers = true;
	}

	@Override
	protected void processPoint(Graphics2D g2d, int granulation) {
		if (chartSettings.getLineWidth() == 0)
			return;

		boolean valid = isChartPointValid(x, y);
		if (mustDrawFirstZeroingLine && valid) {
			mustDrawFirstZeroingLine = false;
			prevX = x;
		}
		if (prevX >= 0) {
			if (valid) {
				if (prevY >= chartRect.y && y >= chartRect.y) {
					g2d.drawLine(prevX, prevY, x, y);
				} else if (prevY >= chartRect.y && y < chartRect.y) {
					int x1 = (x - prevX) * (chartRect.y - prevY) / (y - prevY)
							+ prevX;
					g2d.drawLine(prevX, prevY, x1, chartRect.y);
				} else if (prevY < chartRect.y && y >= chartRect.y) {
					int x1 = (x - prevX) * (chartRect.y - prevY) / (y - prevY)
							+ prevX;
					g2d.drawLine(x1, chartRect.y, x, y);
				}
			}
		}
	}
}
