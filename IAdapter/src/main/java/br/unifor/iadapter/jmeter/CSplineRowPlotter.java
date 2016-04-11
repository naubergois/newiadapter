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

public class CSplineRowPlotter extends AbstractRowPlotter {

	private long splineLinesCount = 200L;

	public CSplineRowPlotter(ChartSettings chartSettings,
			NumberRenderer labelRenderer) {
		super(chartSettings, labelRenderer);
		allowMarkers = true;
	}

	@Override
	protected void processPoint(Graphics2D g2d, int granulation) {
		// do nothing
	}

	protected void postPaintRow(AbstractGraphRow row, Graphics2D g2d) {
		if (chartSettings.getLineWidth() == 0)
			return;

		if (row.size() >= 3) {
			CubicSpline cs = new CubicSpline(row);
			long minX = row.getMinX();
			long maxX = row.getMaxX();

			double step = (double) (maxX - minX) / splineLinesCount;

			double currentX = minX;

			while (currentX <= maxX) {
				x = chartRect.x + (int) ((currentX - minXVal) * dxForDVal);
				int yHeight = (int) ((cs.interpolate(currentX) - minYVal) * dyForDVal);
				y = chartRect.y + chartRect.height - yHeight;

				// prevent out of range
				if (y < chartRect.y) {
					y = chartRect.y;
				}
				if (y > chartRect.y + chartRect.height) {
					y = chartRect.y + chartRect.height;
				}

				currentX += step;

				if (prevX >= 0) {
					g2d.drawLine(prevX, prevY, x, y);
				}

				prevX = x;
				prevY = y;
			}
		}
	}
}
